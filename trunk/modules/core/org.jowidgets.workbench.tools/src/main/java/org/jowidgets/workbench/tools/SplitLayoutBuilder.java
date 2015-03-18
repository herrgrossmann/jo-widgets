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

package org.jowidgets.workbench.tools;

import org.jowidgets.common.types.Orientation;
import org.jowidgets.common.types.SplitResizePolicy;
import org.jowidgets.workbench.api.ILayoutContainer;
import org.jowidgets.workbench.api.ISplitLayout;
import org.jowidgets.workbench.toolkit.api.ILayoutContainerBuilder;
import org.jowidgets.workbench.toolkit.api.ISplitLayoutBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchToolkit;

public class SplitLayoutBuilder implements ISplitLayoutBuilder {

    private final ISplitLayoutBuilder builder;

    public SplitLayoutBuilder() {
        this(builder());
    }

    public SplitLayoutBuilder(final ISplitLayoutBuilder builder) {
        super();
        this.builder = builder;
    }

    @Override
    public final ISplitLayoutBuilder setOrientation(final Orientation orientation) {
        builder.setOrientation(orientation);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setHorizontal() {
        builder.setHorizontal();
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setVertical() {
        builder.setVertical();
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setWeight(final double weigth) {
        builder.setWeight(weigth);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setResizePolicy(final SplitResizePolicy splitResizePolicy) {
        builder.setResizePolicy(splitResizePolicy);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setResizeFirst() {
        builder.setResizeFirst();
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setResizeSecond() {
        builder.setResizeSecond();
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setResizeBoth() {
        builder.setResizeBoth();
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setFirstContainer(final ILayoutContainer firstContainer) {
        builder.setFirstContainer(firstContainer);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setFirstContainer(final ILayoutContainerBuilder firstContainerBuilder) {
        builder.setFirstContainer(firstContainerBuilder);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setSecondContainer(final ILayoutContainer secondContainer) {
        builder.setSecondContainer(secondContainer);
        return this;
    }

    @Override
    public final ISplitLayoutBuilder setSecondContainer(final ILayoutContainerBuilder secondContainerBuilder) {
        builder.setSecondContainer(secondContainerBuilder);
        return this;
    }

    @Override
    public final ISplitLayout build() {
        return builder.build();
    }

    public static ISplitLayoutBuilder builder() {
        return WorkbenchToolkit.getLayoutBuilderFactory().split();
    }

}
