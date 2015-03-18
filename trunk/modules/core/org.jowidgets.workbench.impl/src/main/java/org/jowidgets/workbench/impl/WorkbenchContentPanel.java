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

import java.util.HashMap;
import java.util.Map;

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IControl;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.workbench.api.ILayout;
import org.jowidgets.workbench.api.LayoutScope;

public class WorkbenchContentPanel {

    private final IBluePrintFactory bpf;
    private final IContainer mainContainer;
    private final IControl emptyContent;

    private final Map<String, LayoutPanel> componentScopeLayouts;
    private final Map<String, LayoutPanel> applicationScopeLayouts;
    private final Map<String, LayoutPanel> workbenchScopeLayouts;

    private IControl lastContent;

    public WorkbenchContentPanel(final IContainer mainContainer) {
        super();
        this.componentScopeLayouts = new HashMap<String, LayoutPanel>();
        this.applicationScopeLayouts = new HashMap<String, LayoutPanel>();
        this.workbenchScopeLayouts = new HashMap<String, LayoutPanel>();

        this.bpf = Toolkit.getBluePrintFactory();
        this.mainContainer = mainContainer;
        mainContainer.setLayout(new MigLayoutDescriptor("hidemode 3", "0[grow, 0::]0", "0[grow, 0::]0"));
        this.emptyContent = mainContainer.add(bpf.tabFolder(), "hidemode 3, growx, growy");
        this.lastContent = emptyContent;
    }

    public void setEmptyContent() {
        switchContent(emptyContent);
    }

    public void setLayout(final ComponentContext componentContext, final ILayout layout) {
        switchContent(getLayoutPanel(componentContext, layout).getContentPane());
    }

    public void resetLayout(final ComponentContext componentContext, final ILayout layout) {}

    public void disposeComponent(final ComponentContext componentContext) {}

    private void switchContent(final IControl newContent) {
        if (lastContent != newContent) {
            Dimension lastSize = null;
            if (lastContent != null) {
                lastSize = lastContent.getSize();
                lastContent.setVisible(false);
            }

            if (!newContent.getSize().equals(lastSize)) {
                newContent.setSize(lastSize);
            }
            newContent.setVisible(true);

            lastContent = newContent;
        }
    }

    private LayoutPanel getLayoutPanel(final ComponentContext componentContext, final ILayout layout) {
        LayoutPanel result = null;
        final String gloabId = getGlobalLayoutId(componentContext, layout);

        if (layout.getScope() == LayoutScope.COMPONENT) {

            result = componentScopeLayouts.get(gloabId);
            if (result == null) {
                result = new LayoutPanel(mainContainer, componentContext, layout);
                componentScopeLayouts.put(gloabId, result);
            }
        }
        else if (layout.getScope() == LayoutScope.WORKBENCH_APPLICATION) {
            result = applicationScopeLayouts.get(gloabId);
            if (result == null) {
                result = new LayoutPanel(mainContainer, componentContext, layout);
                applicationScopeLayouts.put(gloabId, result);
            }
            else {
                result.setComponent(componentContext);
            }
        }
        else if (layout.getScope() == LayoutScope.WORKBENCH) {
            result = workbenchScopeLayouts.get(gloabId);
            if (result == null) {
                result = new LayoutPanel(mainContainer, componentContext, layout);
                workbenchScopeLayouts.put(gloabId, result);
            }
            else {
                result.setComponent(componentContext);
            }
        }
        else {
            throw new IllegalArgumentException("Layout scope '" + layout.getScope() + "' is not supported.");
        }
        return result;
    }

    private String getGlobalLayoutId(final ComponentContext componentContext, final ILayout layout) {
        if (layout.getScope() == LayoutScope.WORKBENCH) {
            return layout.getId();
        }
        else if (layout.getScope() == LayoutScope.WORKBENCH_APPLICATION) {
            return componentContext.getWorkbenchApplicationContext().getId() + "#" + layout.getId();
        }
        else if (layout.getScope() == LayoutScope.COMPONENT) {
            return componentContext.getWorkbenchApplicationContext().getId()
                + "#"
                + componentContext.getComponentNodeContext().getGlobalId()
                + "#"
                + layout.getId();
        }
        else {
            throw new IllegalArgumentException("LayoutScope '" + layout.getScope() + "' is not supported.");
        }

    }
}
