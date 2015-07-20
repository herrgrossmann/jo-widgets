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

package org.jowidgets.workbench.toolkit.api;

import org.jowidgets.api.model.item.IMenuBarModel;
import org.jowidgets.api.model.item.IToolBarModel;
import org.jowidgets.api.widgets.content.IContentCreator;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Position;
import org.jowidgets.workbench.api.ICloseCallback;
import org.jowidgets.workbench.api.ILoginCallback;
import org.jowidgets.workbench.api.IWorkbenchDescriptor;

public interface IWorkbenchModelBuilder extends IWorkbenchPartBuilder<IWorkbenchModelBuilder> {

    IWorkbenchModelBuilder setDescriptor(IWorkbenchDescriptor descriptor);

    IWorkbenchModelBuilder setInitialDimension(Dimension initialDimension);

    IWorkbenchModelBuilder setInitialMaximized(boolean maximized);

    IWorkbenchModelBuilder setDecorated(boolean decorated);

    IWorkbenchModelBuilder setInitialPosition(Position initialPosition);

    IWorkbenchModelBuilder setInitialSplitWeight(double initialSplitWeigth);

    IWorkbenchModelBuilder setApplicationNavigator(boolean hasApplicationNavigator);

    IWorkbenchModelBuilder setApplicationsCloseable(boolean applicationsCloseable);

    IWorkbenchModelBuilder setToolBar(IToolBarModel toolBarModel);

    IWorkbenchModelBuilder setMenuBar(IMenuBarModel menuBarModel);

    IWorkbenchModelBuilder setStatusBarCreator(IContentCreator statusBarContentCreator);

    IWorkbenchModelBuilder setLoginCallback(ILoginCallback loginCallback);

    IWorkbenchModelBuilder setCloseCallback(ICloseCallback closeCallback);

    IWorkbenchModelBuilder setViewFactoy(IViewFactory viewFactory);

    IWorkbenchModelBuilder setInitializeCallback(IWorkbenchInitializeCallback initializeCallback);

    IWorkbenchModelBuilder addInitializeCallback(IWorkbenchInitializeCallback initializeCallback);

    IWorkbenchModelBuilder addShutdownHook(Runnable shutdownHook);

    IWorkbenchModelBuilder addApplication(IWorkbenchApplicationModel applicationModel);

    IWorkbenchModelBuilder addApplication(int index, IWorkbenchApplicationModel applicationModel);

    IWorkbenchModelBuilder addApplication(IWorkbenchApplicationModelBuilder applicationModelBuilder);

    IWorkbenchModelBuilder addApplication(int index, IWorkbenchApplicationModelBuilder applicationModelBuilder);

    IWorkbenchModelBuilder addApplication(String id, String label, String tooltip, IImageConstant icon);

    IWorkbenchModelBuilder addApplication(String id, String label, IImageConstant icon);

    IWorkbenchModelBuilder addApplication(String id, String label, String tooltip);

    IWorkbenchModelBuilder addApplication(String id, String label);

    IWorkbenchModelBuilder addApplication(String id);

    IWorkbenchModel build();

}
