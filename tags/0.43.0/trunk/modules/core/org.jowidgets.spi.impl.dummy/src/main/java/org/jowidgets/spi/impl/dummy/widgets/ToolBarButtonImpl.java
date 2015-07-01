/*
 * Copyright (c) 2011, Lukas Gross
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

package org.jowidgets.spi.impl.dummy.widgets;

import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.spi.impl.dummy.dummyui.AbstractUIDButton;
import org.jowidgets.spi.impl.dummy.dummyui.UIDObservable;
import org.jowidgets.spi.widgets.IToolBarButtonSpi;

public class ToolBarButtonImpl extends ToolBarItemImpl implements IToolBarButtonSpi {

    private final UIDObservable actionObservable;

    public ToolBarButtonImpl(final AbstractUIDButton button) {
        super(button);
        getUiReference().addActionListener(new IActionListener() {

            @Override
            public void actionPerformed() {
                actionObservable.fireActionPerformed();
            }
        });
        actionObservable = new UIDObservable();
    }

    @Override
    public void addActionListener(final IActionListener actionListener) {
        actionObservable.addActionListener(actionListener);
    }

    @Override
    public void removeActionListener(final IActionListener actionListener) {
        actionObservable.removeActionListener(actionListener);
    }

}
