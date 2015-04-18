/*
 * Copyright (c) 2011, grossmann, Nikolaus Moll
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.impl.layout.miglayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jowidgets.api.layout.miglayout.IMigLayout;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IControl;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Rectangle;

final class MigLayout implements IMigLayout {

    private final IContainer container;
    private transient IContainerWrapperCommon cacheParentW = null;

    // Hold the serializable text representation of the constraints.
    private Object constraints;
    private Object columnConstraints;
    private Object rowConstraints;

    private transient LCCommon lc = null;
    private transient ACCommon colSpecs = null;
    private transient ACCommon rowSpecs = null;

    private final transient Map<IComponentWrapperCommon, CCCommon> ccMap = new HashMap<IComponentWrapperCommon, CCCommon>(8);
    private transient GridCommon grid = null;

    // The component to string constraints mappings.
    private final Map<IControl, Object> scrConstrMap = new IdentityHashMap<IControl, Object>(8);

    private transient ArrayList<AbstractLayoutCallbackCommon> callbackList = null;
    private transient int lastModCount = MigLayoutToolkitImpl.getMigPlatformDefaults().getModCount();
    private transient int lastHash = -1;

    private final StringBuilder reason = new StringBuilder();

    private final long cacheTime = 0;
    //private long cacheTimeSetMs = 0;
    private final long cacheTimeSetNano = 0;

    private final LayoutUtilCommon layoutUtil;

    public MigLayout(
        final IContainer container,
        final Object constraints,
        final Object columnConstraints,
        final Object rowConstraints) {
        this.container = container;
        this.cacheParentW = new JoMigContainerWrapper(container);
        layoutUtil = MigLayoutToolkitImpl.getMigLayoutUtil();
        setLayoutConstraints(constraints);
        setColumnConstraints(columnConstraints);
        setRowConstraints(rowConstraints);
    }

    @Override
    public void setLayoutConstraints(Object constraints) {
        if (constraints == null || constraints instanceof String) {
            constraints = ConstraintParserCommon.prepare((String) constraints);
            lc = ConstraintParserCommon.parseLayoutConstraint((String) constraints);
        }
        else if (constraints instanceof LCWrapper) {
            lc = ((LCWrapper) constraints).getLC();
        }
        else if (constraints instanceof LCCommon) {
            lc = (LCCommon) constraints;
        }
        else {
            throw new IllegalArgumentException("Illegal constraint type: " + constraints.getClass().toString());
        }
        this.constraints = constraints;

        grid = null;
    }

    @Override
    public Object getLayoutConstraints() {
        return constraints;
    }

    @Override
    public void setColumnConstraints(Object constraints) {
        if (constraints == null || constraints instanceof String) {
            constraints = ConstraintParserCommon.prepare((String) constraints);
            colSpecs = ConstraintParserCommon.parseColumnConstraints((String) constraints);
        }
        else if (constraints instanceof ACWrapper) {
            colSpecs = ((ACWrapper) constraints).getAC();
        }
        else if (constraints instanceof ACCommon) {
            colSpecs = (ACCommon) constraints;
        }
        else {
            throw new IllegalArgumentException("Illegal constraint type: " + constraints.getClass().toString());
        }
        columnConstraints = constraints;

        grid = null;
    }

    @Override
    public Object getColumnConstraints() {
        return columnConstraints;
    }

    @Override
    public void setRowConstraints(Object constraints) {
        if (constraints == null || constraints instanceof String) {
            constraints = ConstraintParserCommon.prepare((String) constraints);
            rowSpecs = ConstraintParserCommon.parseRowConstraints((String) constraints);
        }
        else if (constraints instanceof ACWrapper) {
            rowSpecs = ((ACWrapper) constraints).getAC();
        }
        else if (constraints instanceof ACCommon) {
            rowSpecs = (ACCommon) constraints;
        }
        else {
            throw new IllegalArgumentException("Illegal constraint type: " + constraints.getClass().toString());
        }
        rowConstraints = constraints;

        grid = null;
    }

    @Override
    public Object getRowConstraints() {
        return rowConstraints;
    }

    @Override
    public void setConstraintMap(final Map<IControl, Object> map) {
        scrConstrMap.clear();
        ccMap.clear();
        for (final Map.Entry<IControl, Object> e : map.entrySet()) {
            setComponentConstraintsImpl(e.getKey(), e.getValue(), true);
        }
    }

    @Override
    public Map<IControl, Object> getConstraintMap() {
        return new IdentityHashMap<IControl, Object>(scrConstrMap);
    }

    /**
     * Sets the component constraint for the component that already must be handleded by this layout manager.
     * <p>
     * See the class JavaDocs for information on how this string is formatted.
     * 
     * @param constr The component constraints as a String or {@link CCCommon.miginfocom.layout.CC}. <code>null</code> is ok.
     * @param comp The component to set the constraints for.
     * @param noCheck Doesn't check if control already is managed.
     * @throws RuntimeException if the constaint was not valid.
     * @throws IllegalArgumentException If the component is not handling the component.
     */
    private void setComponentConstraintsImpl(final IControl comp, final Object constr, final boolean noCheck) {
        if (noCheck == false && scrConstrMap.containsKey(comp) == false) {
            throw new IllegalArgumentException("Component must already be added to parent!");
        }

        final IComponentWrapperCommon cw = new JoMigComponentWrapper(comp);

        if (constr == null || constr instanceof String) {
            final String cStr = ConstraintParserCommon.prepare((String) constr);

            scrConstrMap.put(comp, constr);
            ccMap.put(cw, ConstraintParserCommon.parseComponentConstraint(cStr));
        }
        else if (constr instanceof CCWrapper) {
            scrConstrMap.put(comp, constr);
            ccMap.put(cw, ((CCWrapper) constr).getCC());
        }
        else if (constr instanceof CCCommon) {
            scrConstrMap.put(comp, constr);
            ccMap.put(cw, (CCCommon) constr);
        }
        else {
            throw new IllegalArgumentException("Constraint must be String or ComponentConstraint: "
                + constr.getClass().toString());
        }

        grid = null;
    }

    @Override
    public boolean isManagingComponent(final IControl control) {
        return scrConstrMap.containsKey(control);
    }

    public void addLayoutCallback(final AbstractLayoutCallbackCommon callback) {
        if (callback == null) {
            throw new NullPointerException();
        }

        if (callbackList == null) {
            callbackList = new ArrayList<AbstractLayoutCallbackCommon>(1);
        }

        callbackList.add(callback);
    }

    public void removeLayoutCallback(final AbstractLayoutCallbackCommon callback) {
        if (callbackList != null) {
            callbackList.remove(callback);
        }
    }

    private void checkChildren() {
        final List<IControl> comps = new LinkedList<IControl>(container.getChildren());

        final List<IComponentWrapperCommon> removed = new LinkedList<IComponentWrapperCommon>();
        for (final IComponentWrapperCommon cw : ccMap.keySet()) {
            if (comps.contains(cw.getComponent())) {
                comps.remove(cw.getComponent());
                continue;
            }
            removed.add(cw);
        }

        for (final IComponentWrapperCommon cw : removed) {
            scrConstrMap.remove(cw.getComponent());
            ccMap.remove(cw);
        }

        if (comps.size() != scrConstrMap.size()) {
            for (final IControl c : comps) {
                if (scrConstrMap.containsKey(c)) {
                    continue;
                }

                setComponentConstraintsImpl(c, c.getLayoutConstraints(), true);
            }
        }
    }

    @SuppressWarnings("unused")
    private boolean calculateCache() {
        //		if (Math.abs(cacheTimeSetMs - System.currentTimeMillis()) > 1000) {
        //			cacheTime = 0;
        //			return true;
        //		}

        //CHECKSTYLE:OFF
        if (System.nanoTime() - cacheTimeSetNano < 2 * cacheTime) {
            //			if (this.thread == null) {
            //				this.thread = new LayoutThread(this, 2 * cacheTime);
            //			}
            //			else {
            //				thread.reset();
            //			}
            //			return false;
        }
        //CHECKSTYLE:ON
        return true;
    }

    /**
     * Check if something has changed and if so recrete it to the cached objects.
     */
    private void checkCache() {
        //		final long start = System.nanoTime();
        //		final boolean calculateCache = calculateCache();
        //		if (calculateCache) {
        checkChildren();

        // Check if the grid is valid
        final int mc = MigLayoutToolkitImpl.getMigPlatformDefaults().getModCount();
        if (lastModCount != mc) {
            grid = null;
            lastModCount = mc;
            reason.append("lastmodcount,");
        }

        int hash = container.getSize().hashCode();
        for (final Iterator<IComponentWrapperCommon> it = ccMap.keySet().iterator(); it.hasNext();) {
            hash += it.next().getLayoutHashCode();
        }

        if (hash != lastHash) {
            reason.append("hash " + hash + " vs " + lastHash + ",");
            reason.append(container.getSize() + ",");
            grid = null;
            lastHash = hash;
        }
        //		}

        if (grid == null) {
            //CHECKSTYLE:OFF
            //System.out.println("new Grid for " + this + " [" + reason + "]");
            //CHECKSTYLE:ON
            grid = new GridCommon(cacheParentW, lc, rowSpecs, colSpecs, ccMap, callbackList);
            reason.setLength(0);
        }

        //		if (calculateCache) {
        //			final long end = System.nanoTime();
        //			final long currentTime = end - start;
        //
        //			if (currentTime > 1000000) {
        //				return;
        //			}
        //			if (cacheTime < currentTime) {
        //				cacheTime = currentTime;
        //				//cacheTimeSetMs = System.currentTimeMillis();
        //				cacheTimeSetNano = end;
        //				//CHECKSTYLE:OFF
        //				System.out.println("nano time: " + (cacheTime) + " [" + this + "]");
        //				//CHECKSTYLE:ON
        //			}
        //		}
    }

    @Override
    public void layout() {
        checkCache();

        final Rectangle r = container.getClientArea();
        final int[] b = new int[] {r.getX(), r.getY(), r.getWidth(), r.getHeight()};

        final boolean layoutAgain = grid.layout(b, lc.getAlignX(), lc.getAlignY(), false, true);

        if (layoutAgain) {
            grid = null;
            checkCache();
            grid.layout(b, lc.getAlignX(), lc.getAlignY(), false, false);
        }
    }

    private Dimension getSize(final int type) {
        checkCache();
        final int w = layoutUtil.getSizeSafe(grid != null ? grid.getWidth() : null, type);
        final int h = layoutUtil.getSizeSafe(grid != null ? grid.getHeight() : null, type);
        return new Dimension(w, h);
    }

    @Override
    public Dimension getMinSize() {
        return container.computeDecoratedSize(getSize(LayoutUtilCommon.MIN));
    }

    @Override
    public Dimension getPreferredSize() {
        return container.computeDecoratedSize(getSize(LayoutUtilCommon.PREF));
    }

    @Override
    public Dimension getMaxSize() {
        return container.computeDecoratedSize(getSize(LayoutUtilCommon.MAX));
    }

    @Override
    public void invalidate() {
        //reason.append("invalidate,");
        //grid = null;
    }

}
