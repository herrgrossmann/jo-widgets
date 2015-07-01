/*
 * Copyright (c) 2010, Michael Grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.spi.impl.swt.common.widgets;

import org.eclipse.swt.widgets.Control;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controller.IComponentListener;
import org.jowidgets.common.widgets.controller.IFocusListener;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.controller.IMouseMotionListener;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.spi.dnd.IDragSourceSpi;
import org.jowidgets.spi.dnd.IDropTargetSpi;
import org.jowidgets.spi.impl.controller.InputObservable;
import org.jowidgets.spi.impl.swt.common.dnd.ImmutableDropSelection;
import org.jowidgets.spi.impl.swt.common.image.SwtImageRegistry;
import org.jowidgets.spi.widgets.IInputControlSpi;
import org.jowidgets.spi.widgets.IPopupMenuSpi;

public abstract class AbstractInputControl extends InputObservable implements IInputControlSpi {

    private final Control control;
    private final SwtControl swtControlDelegate;

    public AbstractInputControl(final Control control, final SwtImageRegistry imageRegistry) {
        super();
        this.control = control;
        this.swtControlDelegate = new SwtControl(control, new ImmutableDropSelection(this), imageRegistry);
    }

    @Override
    public Control getUiReference() {
        return control;
    }

    @Override
    public void setLayoutConstraints(final Object layoutConstraints) {
        swtControlDelegate.setLayoutConstraints(layoutConstraints);
    }

    @Override
    public Object getLayoutConstraints() {
        return swtControlDelegate.getLayoutConstraints();
    }

    @Override
    public void redraw() {
        swtControlDelegate.redraw();
    }

    @Override
    public void setRedrawEnabled(final boolean enabled) {
        swtControlDelegate.setRedrawEnabled(enabled);
    }

    @Override
    public void setForegroundColor(final IColorConstant colorValue) {
        swtControlDelegate.setForegroundColor(colorValue);
    }

    @Override
    public void setBackgroundColor(final IColorConstant colorValue) {
        swtControlDelegate.setBackgroundColor(colorValue);
    }

    @Override
    public IColorConstant getForegroundColor() {
        return swtControlDelegate.getForegroundColor();
    }

    @Override
    public IColorConstant getBackgroundColor() {
        return swtControlDelegate.getBackgroundColor();
    }

    @Override
    public void setCursor(final Cursor cursor) {
        swtControlDelegate.setCursor(cursor);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        swtControlDelegate.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return swtControlDelegate.isEnabled();
    }

    @Override
    public void setVisible(final boolean visible) {
        swtControlDelegate.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return swtControlDelegate.isVisible();
    }

    @Override
    public Dimension getMinSize() {
        return swtControlDelegate.getMinSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return swtControlDelegate.getPreferredSize();
    }

    @Override
    public Dimension getMaxSize() {
        return swtControlDelegate.getMaxSize();
    }

    @Override
    public Dimension getSize() {
        return swtControlDelegate.getSize();
    }

    @Override
    public void setSize(final Dimension size) {
        swtControlDelegate.setSize(size);
    }

    @Override
    public Position getPosition() {
        return swtControlDelegate.getPosition();
    }

    @Override
    public void setPosition(final Position position) {
        swtControlDelegate.setPosition(position);
    }

    @Override
    public boolean requestFocus() {
        return swtControlDelegate.requestFocus();
    }

    @Override
    public void addFocusListener(final IFocusListener listener) {
        swtControlDelegate.addFocusListener(listener);
    }

    @Override
    public void removeFocusListener(final IFocusListener listener) {
        swtControlDelegate.removeFocusListener(listener);
    }

    @Override
    public void addKeyListener(final IKeyListener listener) {
        swtControlDelegate.addKeyListener(listener);
    }

    @Override
    public void removeKeyListener(final IKeyListener listener) {
        swtControlDelegate.removeKeyListener(listener);
    }

    @Override
    public void addMouseListener(final IMouseListener mouseListener) {
        swtControlDelegate.addMouseListener(mouseListener);
    }

    @Override
    public void removeMouseListener(final IMouseListener mouseListener) {
        swtControlDelegate.removeMouseListener(mouseListener);
    }

    @Override
    public void addMouseMotionListener(final IMouseMotionListener listener) {
        swtControlDelegate.addMouseMotionListener(listener);
    }

    @Override
    public void removeMouseMotionListener(final IMouseMotionListener listener) {
        swtControlDelegate.addMouseMotionListener(listener);
    }

    @Override
    public void addComponentListener(final IComponentListener componentListener) {
        swtControlDelegate.addComponentListener(componentListener);
    }

    @Override
    public void removeComponentListener(final IComponentListener componentListener) {
        swtControlDelegate.removeComponentListener(componentListener);
    }

    @Override
    public IPopupMenuSpi createPopupMenu() {
        return swtControlDelegate.createPopupMenu();
    }

    @Override
    public void addPopupDetectionListener(final IPopupDetectionListener listener) {
        swtControlDelegate.addPopupDetectionListener(listener);
    }

    @Override
    public void removePopupDetectionListener(final IPopupDetectionListener listener) {
        swtControlDelegate.removePopupDetectionListener(listener);
    }

    @Override
    public void setToolTipText(final String toolTip) {
        swtControlDelegate.setToolTipText(toolTip);
    }

    @Override
    public IDragSourceSpi getDragSource() {
        return swtControlDelegate.getDragSource();
    }

    @Override
    public IDropTargetSpi getDropTarget() {
        return swtControlDelegate.getDropTarget();
    }

}
