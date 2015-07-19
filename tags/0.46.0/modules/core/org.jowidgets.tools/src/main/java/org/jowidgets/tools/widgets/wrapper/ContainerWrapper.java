/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.tools.widgets.wrapper;

import java.util.Collection;
import java.util.List;

import org.jowidgets.api.controller.IContainerListener;
import org.jowidgets.api.controller.IContainerRegistry;
import org.jowidgets.api.controller.IListenerFactory;
import org.jowidgets.api.layout.ILayoutFactory;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IControl;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Rectangle;
import org.jowidgets.common.widgets.controller.IComponentListener;
import org.jowidgets.common.widgets.controller.IFocusListener;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.common.widgets.descriptor.IWidgetDescriptor;
import org.jowidgets.common.widgets.factory.ICustomWidgetCreator;
import org.jowidgets.common.widgets.layout.ILayoutDescriptor;
import org.jowidgets.common.widgets.layout.ILayouter;

public class ContainerWrapper extends ComponentWrapper implements IContainer {

    public ContainerWrapper(final IContainer widget) {
        super(widget);
    }

    @Override
    protected IContainer getWidget() {
        return (IContainer) super.getWidget();
    }

    @Override
    public Rectangle getClientArea() {
        return getWidget().getClientArea();
    }

    @Override
    public Dimension computeDecoratedSize(final Dimension clientAreaSize) {
        return getWidget().computeDecoratedSize(clientAreaSize);
    }

    @Override
    public void setLayout(final ILayoutDescriptor layoutDescriptor) {
        getWidget().setLayout(layoutDescriptor);
    }

    @Override
    public <LAYOUT_TYPE extends ILayouter> LAYOUT_TYPE setLayout(final ILayoutFactory<LAYOUT_TYPE> layoutFactory) {
        return getWidget().setLayout(layoutFactory);
    }

    @Override
    public void layoutBegin() {
        getWidget().layoutBegin();
    }

    @Override
    public void layout() {
        getWidget().layout();
    }

    @Override
    public void layoutEnd() {
        getWidget().layoutEnd();
    }

    @Override
    public void layoutLater() {
        getWidget().layoutLater();
    }

    @Override
    public void removeAll() {
        getWidget().removeAll();
    }

    @Override
    public List<IControl> getChildren() {
        return getWidget().getChildren();
    }

    @Override
    public boolean remove(final IControl control) {
        return getWidget().remove(control);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(
        final int index,
        final IWidgetDescriptor<? extends WIDGET_TYPE> descriptor,
        final Object layoutConstraints) {
        return getWidget().add(index, descriptor, layoutConstraints);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(
        final int index,
        final IWidgetDescriptor<? extends WIDGET_TYPE> descriptor) {
        return getWidget().add(index, descriptor);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(
        final int index,
        final ICustomWidgetCreator<WIDGET_TYPE> creator,
        final Object layoutConstraints) {
        return getWidget().add(index, creator, layoutConstraints);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(final int index, final ICustomWidgetCreator<WIDGET_TYPE> creator) {
        return getWidget().add(index, creator);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(
        final IWidgetDescriptor<? extends WIDGET_TYPE> descriptor,
        final Object layoutConstraints) {
        return getWidget().add(descriptor, layoutConstraints);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(
        final ICustomWidgetCreator<WIDGET_TYPE> creator,
        final Object layoutConstraints) {
        return getWidget().add(creator, layoutConstraints);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(final IWidgetDescriptor<? extends WIDGET_TYPE> descriptor) {
        return getWidget().add(descriptor);
    }

    @Override
    public <WIDGET_TYPE extends IControl> WIDGET_TYPE add(final ICustomWidgetCreator<WIDGET_TYPE> creator) {
        return getWidget().add(creator);
    }

    @Override
    public void setTabOrder(final Collection<? extends IControl> tabOrder) {
        getWidget().setTabOrder(tabOrder);
    }

    @Override
    public void setTabOrder(final IControl... controls) {
        getWidget().setTabOrder(controls);
    }

    @Override
    public void addContainerListener(final IContainerListener listener) {
        getWidget().addContainerListener(listener);
    }

    @Override
    public void removeContainerListener(final IContainerListener listener) {
        getWidget().removeContainerListener(listener);
    }

    @Override
    public void addContainerRegistry(final IContainerRegistry registry) {
        getWidget().addContainerRegistry(registry);
    }

    @Override
    public void removeContainerRegistry(final IContainerRegistry registry) {
        getWidget().removeContainerRegistry(registry);
    }

    @Override
    public void addComponentListenerRecursive(final IListenerFactory<IComponentListener> listenerFactory) {
        getWidget().addComponentListenerRecursive(listenerFactory);
    }

    @Override
    public void removeComponentListenerRecursive(final IListenerFactory<IComponentListener> listenerFactory) {
        getWidget().removeComponentListenerRecursive(listenerFactory);
    }

    @Override
    public void addFocusListenerRecursive(final IListenerFactory<IFocusListener> listenerFactory) {
        getWidget().addFocusListenerRecursive(listenerFactory);
    }

    @Override
    public void removeFocusListenerRecursive(final IListenerFactory<IFocusListener> listenerFactory) {
        getWidget().removeFocusListenerRecursive(listenerFactory);
    }

    @Override
    public void addKeyListenerRecursive(final IListenerFactory<IKeyListener> listenerFactory) {
        getWidget().addKeyListenerRecursive(listenerFactory);
    }

    @Override
    public void removeKeyListenerRecursive(final IListenerFactory<IKeyListener> listenerFactory) {
        getWidget().removeKeyListenerRecursive(listenerFactory);
    }

    @Override
    public void addMouseListenerRecursive(final IListenerFactory<IMouseListener> listenerFactory) {
        getWidget().addMouseListenerRecursive(listenerFactory);
    }

    @Override
    public void removeMouseListenerRecursive(final IListenerFactory<IMouseListener> listenerFactory) {
        getWidget().removeMouseListenerRecursive(listenerFactory);
    }

    @Override
    public void addPopupDetectionListenerRecursive(final IListenerFactory<IPopupDetectionListener> listenerFactory) {
        getWidget().addPopupDetectionListenerRecursive(listenerFactory);
    }

    @Override
    public void removePopupDetectionListenerRecursive(final IListenerFactory<IPopupDetectionListener> listenerFactory) {
        getWidget().removePopupDetectionListenerRecursive(listenerFactory);
    }

}
