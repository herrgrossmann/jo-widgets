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

package org.jowidgets.impl.widgets.common.wrapper;

import org.jowidgets.api.layout.miglayout.IMigLayoutFactoryBuilder;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Rectangle;
import org.jowidgets.common.widgets.IFrameCommon;
import org.jowidgets.common.widgets.layout.ILayoutDescriptor;
import org.jowidgets.common.widgets.layout.ILayouter;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.spi.widgets.IFrameSpi;
import org.jowidgets.util.Assert;

public abstract class AbstractFrameSpiWrapper extends AbstractWindowSpiWrapper implements IFrameCommon {

    private ILayouter layouter;

    public AbstractFrameSpiWrapper(final IFrameSpi widget) {
        super(widget);
    }

    @Override
    public IFrameSpi getWidget() {
        return (IFrameSpi) super.getWidget();
    }

    @Override
    public void setLayout(final ILayoutDescriptor layoutDescriptor) {
        Assert.paramNotNull(layoutDescriptor, "layoutDescriptor");
        if (!Toolkit.hasSpiMigLayoutSupport() && layoutDescriptor instanceof MigLayoutDescriptor && this instanceof IContainer) {
            final IMigLayoutFactoryBuilder migLayoutBuilder = Toolkit.getLayoutFactoryProvider().migLayoutBuilder();
            migLayoutBuilder.descriptor((MigLayoutDescriptor) layoutDescriptor);
            setLayoutImpl(migLayoutBuilder.build().create((IContainer) this));
        }
        else {
            setLayoutImpl(layoutDescriptor);
        }
    }

    private void setLayoutImpl(final ILayoutDescriptor layoutDescriptor) {
        Assert.paramNotNull(layoutDescriptor, "layoutDescriptor");
        if (layoutDescriptor instanceof ILayouter) {
            this.layouter = (ILayouter) layoutDescriptor;
        }
        else {
            this.layouter = null;
        }
        getWidget().setLayout(layoutDescriptor);
    }

    @Override
    public void pack() {
        if (layouter != null) {
            setSize(layouter.getPreferredSize());
        }
        else {
            super.pack();
        }
    }

    public void layout() {
        layoutBegin();
        layoutEnd();
    }

    @Override
    public void layoutBegin() {
        getWidget().layoutBegin();
    }

    @Override
    public void layoutEnd() {
        getWidget().layoutEnd();
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
    public void setTitle(final String title) {
        getWidget().setTitle(title);
    }

    @Override
    public void setMaximized(final boolean maximized) {
        getWidget().setMaximized(maximized);
    }

    @Override
    public boolean isMaximized() {
        return getWidget().isMaximized();
    }

    @Override
    public void setIconfied(final boolean iconfied) {
        getWidget().setIconfied(iconfied);
    }

    @Override
    public boolean isIconfied() {
        return getWidget().isIconfied();
    }

}
