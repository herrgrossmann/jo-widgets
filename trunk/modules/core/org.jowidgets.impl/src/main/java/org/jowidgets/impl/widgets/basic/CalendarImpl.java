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

package org.jowidgets.impl.widgets.basic;

import java.util.Date;

import org.jowidgets.api.controller.IDisposeListener;
import org.jowidgets.api.controller.IParentListener;
import org.jowidgets.api.widgets.ICalendar;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.descriptor.ICalendarDescriptor;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.impl.base.delegate.ControlDelegate;
import org.jowidgets.impl.widgets.common.wrapper.AbstractControlSpiWrapper;
import org.jowidgets.spi.widgets.ICalendarSpi;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;

public class CalendarImpl extends AbstractControlSpiWrapper implements ICalendar {

    private final ControlDelegate controlDelegate;

    public CalendarImpl(final ICalendarSpi widgetSpi, final ICalendarDescriptor setup) {
        super(widgetSpi);

        this.controlDelegate = new ControlDelegate(widgetSpi, this);

        VisibiliySettingsInvoker.setVisibility(setup, this);
        ColorSettingsInvoker.setColors(setup, this);

        if (setup.getDate() != null) {
            setDate(setup.getDate());
        }
    }

    @Override
    public ICalendarSpi getWidget() {
        return (ICalendarSpi) super.getWidget();
    }

    @Override
    public IContainer getParent() {
        return controlDelegate.getParent();
    }

    @Override
    public void setParent(final IContainer parent) {
        controlDelegate.setParent(parent);
    }

    @Override
    public void addParentListener(final IParentListener<IContainer> listener) {
        controlDelegate.addParentListener(listener);
    }

    @Override
    public void removeParentListener(final IParentListener<IContainer> listener) {
        controlDelegate.removeParentListener(listener);
    }

    @Override
    public boolean isReparentable() {
        return controlDelegate.isReparentable();
    }

    @Override
    public void addDisposeListener(final IDisposeListener listener) {
        controlDelegate.addDisposeListener(listener);
    }

    @Override
    public void removeDisposeListener(final IDisposeListener listener) {
        controlDelegate.removeDisposeListener(listener);
    }

    @Override
    public boolean isDisposed() {
        return controlDelegate.isDisposed();
    }

    @Override
    public void dispose() {
        controlDelegate.dispose();
    }

    @Override
    public IPopupMenu createPopupMenu() {
        return controlDelegate.createPopupMenu();
    }

    @Override
    public void setDate(final Date date) {
        getWidget().setDate(date);
    }

    @Override
    public Date getDate() {
        return getWidget().getDate();
    }

    @Override
    public void addInputListener(final IInputListener listener) {
        getWidget().addInputListener(listener);
    }

    @Override
    public void removeInputListener(final IInputListener listener) {
        getWidget().removeInputListener(listener);
    }

}
