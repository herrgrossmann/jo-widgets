/*
 * Copyright (c) 2010, grossmann
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

package org.jowidgets.impl.widgets.basic;

import org.jowidgets.api.controller.IDisposeListener;
import org.jowidgets.api.model.item.ICheckedItemModel;
import org.jowidgets.api.model.item.IToolBarItemModel;
import org.jowidgets.api.widgets.IToolBar;
import org.jowidgets.api.widgets.IToolBarToggleButton;
import org.jowidgets.api.widgets.descriptor.setup.IItemSetup;
import org.jowidgets.impl.base.delegate.SelectableItemModelBindingDelegate;
import org.jowidgets.impl.base.delegate.ToolBarItemDiposableDelegate;
import org.jowidgets.impl.model.item.CheckedItemModelBuilder;
import org.jowidgets.impl.widgets.common.wrapper.ToolBarToggleButtonSpiWrapper;
import org.jowidgets.impl.widgets.common.wrapper.invoker.ToolBarToggleButtonSpiInvoker;
import org.jowidgets.spi.widgets.IToolBarToggleButtonSpi;

public class ToolBarToggleButtonImpl extends ToolBarToggleButtonSpiWrapper implements IToolBarToggleButton {

    private final IToolBar parent;
    private final ToolBarItemDiposableDelegate disposableDelegate;

    public ToolBarToggleButtonImpl(
        final IToolBar parent,
        final IToolBarToggleButtonSpi toolBarToggleButtonSpi,
        final IItemSetup setup) {
        super(toolBarToggleButtonSpi, new SelectableItemModelBindingDelegate(new ToolBarToggleButtonSpiInvoker(
            toolBarToggleButtonSpi), new CheckedItemModelBuilder().build()));

        this.parent = parent;
        this.disposableDelegate = new ToolBarItemDiposableDelegate(this, getItemModelBindingDelegate());

        setText(setup.getText());
        setToolTipText(setup.getToolTipText());
        setIcon(setup.getIcon());

    }

    @Override
    public IToolBar getParent() {
        return parent;
    }

    @Override
    public void dispose() {
        disposableDelegate.dispose();
    }

    @Override
    public boolean isDisposed() {
        return disposableDelegate.isDisposed();
    }

    @Override
    public void addDisposeListener(final IDisposeListener listener) {
        disposableDelegate.addDisposeListener(listener);
    }

    @Override
    public void removeDisposeListener(final IDisposeListener listener) {
        disposableDelegate.removeDisposeListener(listener);
    }

    @Override
    public ICheckedItemModel getModel() {
        return (ICheckedItemModel) getItemModelBindingDelegate().getModel();
    }

    @Override
    public void setModel(final ICheckedItemModel model) {
        getItemModelBindingDelegate().setModel(model);
    }

    @Override
    public void setModel(final IToolBarItemModel model) {
        if (model instanceof ICheckedItemModel) {
            setModel((ICheckedItemModel) model);
        }
        else {
            throw new IllegalArgumentException("Model type '" + ICheckedItemModel.class.getName() + "' expected");
        }
    }

}
