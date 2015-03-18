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

package org.jowidgets.examples.common.workbench.base;

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.types.QuestionResult;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.api.IWorkbenchApplication;

public abstract class AbstractDemoApplication implements IWorkbenchApplication {

    private final String id;

    public AbstractDemoApplication(final String id) {
        super();
        this.id = id;
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public String getTooltip() {
        return null;
    }

    @Override
    public IImageConstant getIcon() {
        return null;
    }

    @Override
    public void onActiveStateChanged(final boolean active) {
        // CHECKSTYLE:OFF
        System.out.println("activated= " + active + ", " + id);
        // CHECKSTYLE:ON
    }

    @Override
    public void onVisibleStateChanged(final boolean visible) {
        // CHECKSTYLE:OFF
        System.out.println("visibility= " + visible + ", " + id);
        // CHECKSTYLE:ON
    }

    @Override
    public void onDispose() {
        // CHECKSTYLE:OFF
        System.out.println(getId() + " onDispose");
        // CHECKSTYLE:ON
    }

    @Override
    public void onClose(final IVetoable vetoable) {
        final QuestionResult result = Toolkit.getQuestionPane()
                .askYesNoQuestion("Would you really like to quit the application?");
        if (result != QuestionResult.YES) {
            vetoable.veto();
        }
    }

    @Override
    public IView createView(final String viewId, final IViewContext viewContext) {
        return null;
    }

}
