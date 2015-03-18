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
package org.jowidgets.spi.impl.swing.common.widgets;

import java.awt.Container;

import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.spi.dnd.IDragSourceSpi;
import org.jowidgets.spi.dnd.IDropTargetSpi;
import org.jowidgets.spi.impl.swing.common.dnd.IDropSelectionProvider;
import org.jowidgets.spi.impl.swing.common.dnd.ImmutableDropSelection;
import org.jowidgets.spi.widgets.ICompositeSpi;

public class SwingComposite extends SwingContainer implements ICompositeSpi {

    private final SwingControl swingControlDelegate;

    public SwingComposite(final IGenericWidgetFactory factory, final Container container) {
        this(factory, container, null);
    }

    public SwingComposite(
        final IGenericWidgetFactory factory,
        final Container container,
        IDropSelectionProvider dropSelectionProvider) {
        super(factory, container);
        if (dropSelectionProvider == null) {
            if (this instanceof IDropSelectionProvider) {
                dropSelectionProvider = (IDropSelectionProvider) this;
            }
            else {
                dropSelectionProvider = new ImmutableDropSelection(this);
            }
        }
        this.swingControlDelegate = new SwingControl(container, dropSelectionProvider);
    }

    @Override
    public void setLayoutConstraints(final Object layoutConstraints) {
        swingControlDelegate.setLayoutConstraints(layoutConstraints);
    }

    @Override
    public Object getLayoutConstraints() {
        return swingControlDelegate.getLayoutConstraints();
    }

    @Override
    public Dimension getMinSize() {
        return swingControlDelegate.getMinSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return swingControlDelegate.getPreferredSize();
    }

    @Override
    public Dimension getMaxSize() {
        return swingControlDelegate.getMaxSize();
    }

    @Override
    public IDragSourceSpi getDragSource() {
        return swingControlDelegate.getDragSource();
    }

    @Override
    public IDropTargetSpi getDropTarget() {
        return swingControlDelegate.getDropTarget();
    }

}
