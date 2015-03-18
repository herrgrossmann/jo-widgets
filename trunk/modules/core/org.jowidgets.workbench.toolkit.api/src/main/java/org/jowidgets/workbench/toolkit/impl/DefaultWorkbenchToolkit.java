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

package org.jowidgets.workbench.toolkit.impl;

import org.jowidgets.workbench.api.IComponent;
import org.jowidgets.workbench.toolkit.api.IComponentFactory;
import org.jowidgets.workbench.toolkit.api.ILayoutBuilderFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchPartBuilderFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchPartFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchToolkit;

public class DefaultWorkbenchToolkit implements IWorkbenchToolkit {

    private ILayoutBuilderFactory layoutBuilderFactory;
    private IWorkbenchPartBuilderFactory workbenchPartBuilderFactory;
    private IWorkbenchPartFactory workbenchPartFactory;

    @Override
    public ILayoutBuilderFactory getLayoutBuilderFactory() {
        if (layoutBuilderFactory == null) {
            this.layoutBuilderFactory = new LayoutBuilderFactory();
        }
        return layoutBuilderFactory;
    }

    @Override
    public IWorkbenchPartBuilderFactory getWorkbenchPartBuilderFactory() {
        if (workbenchPartBuilderFactory == null) {
            workbenchPartBuilderFactory = new WorkbenchPartBuilderFactory();
        }
        return workbenchPartBuilderFactory;
    }

    @Override
    public IWorkbenchPartFactory getWorkbenchPartFactory() {
        if (workbenchPartFactory == null) {
            workbenchPartFactory = new WorkbenchPartFactory();
        }
        return workbenchPartFactory;
    }

    @Override
    public IComponentFactory createComponentFactory(final Class<? extends IComponent> componentType) {
        return new TypeBasedComponentFactory(componentType);
    }

}
