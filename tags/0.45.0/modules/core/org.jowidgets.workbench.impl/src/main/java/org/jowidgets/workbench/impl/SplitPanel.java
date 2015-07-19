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

package org.jowidgets.workbench.impl;

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.ISplitComposite;
import org.jowidgets.api.widgets.blueprint.ISplitCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.workbench.api.ISplitLayout;

public class SplitPanel implements ILayoutPanel {

    private final ILayoutPanel firstContainerContext;
    private final ILayoutPanel secondContainerContext;

    public SplitPanel(final ISplitLayout splitLayout, final IContainer parentContainer, final ComponentContext component) {
        final IBluePrintFactory bpf = Toolkit.getBluePrintFactory();
        parentContainer.setLayout(MigLayoutFactory.growingInnerCellLayout());

        final ISplitCompositeBluePrint splitCompositeBp = bpf.splitComposite();
        splitCompositeBp.disableBorders();
        splitCompositeBp.setOrientation(splitLayout.getOrientation());
        if (splitLayout.getResizePolicy() != null) {
            splitCompositeBp.setResizePolicy(splitLayout.getResizePolicy());
        }
        splitCompositeBp.setWeight(splitLayout.getWeight());

        final ISplitComposite splitComposite = parentContainer.add(splitCompositeBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);

        firstContainerContext = new LayoutContainerContext(splitLayout.getFirstContainer(), splitComposite.getFirst(), component);

        secondContainerContext = new LayoutContainerContext(
            splitLayout.getSecondContainer(),
            splitComposite.getSecond(),
            component);

    }

    @Override
    public void setComponent(final ComponentContext component) {
        firstContainerContext.setComponent(component);
        secondContainerContext.setComponent(component);
    }

}
