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
package org.jowidgets.spi.impl.dummy.widgets;

import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.spi.dnd.IDragSourceSpi;
import org.jowidgets.spi.dnd.IDropTargetSpi;
import org.jowidgets.spi.impl.dummy.ui.UIDContainer;
import org.jowidgets.spi.widgets.ICompositeSpi;

public class DummyComposite extends DummyContainer implements ICompositeSpi {

    private final DummyControl dummyControlDelegate;

    public DummyComposite(final IGenericWidgetFactory factory, final UIDContainer container) {
        super(factory, container);

        this.dummyControlDelegate = new DummyControl(container);
    }

    @Override
    public void setLayoutConstraints(final Object layoutConstraints) {
        dummyControlDelegate.setLayoutConstraints(layoutConstraints);
    }

    @Override
    public Object getLayoutConstraints() {
        return dummyControlDelegate.getLayoutConstraints();
    }

    @Override
    public Dimension getMinSize() {
        return dummyControlDelegate.getMinSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return dummyControlDelegate.getPreferredSize();
    }

    @Override
    public Dimension getMaxSize() {
        return dummyControlDelegate.getMaxSize();
    }

    @Override
    public IDragSourceSpi getDragSource() {
        return dummyControlDelegate.getDragSource();
    }

    @Override
    public IDropTargetSpi getDropTarget() {
        return dummyControlDelegate.getDropTarget();
    }

}
