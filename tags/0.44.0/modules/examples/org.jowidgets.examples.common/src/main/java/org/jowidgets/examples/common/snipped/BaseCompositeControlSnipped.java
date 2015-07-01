/*
 * Copyright (c) 2015, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.examples.common.snipped;

import org.jowidgets.api.layout.FillLayout;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IFrame;
import org.jowidgets.api.widgets.IInputField;
import org.jowidgets.common.application.IApplication;
import org.jowidgets.common.application.IApplicationLifecycle;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.tools.controller.WindowAdapter;
import org.jowidgets.tools.widgets.base.CompositeControl;
import org.jowidgets.tools.widgets.base.Frame;
import org.jowidgets.tools.widgets.blueprint.BPF;

public final class BaseCompositeControlSnipped implements IApplication {

    @Override
    public void start(final IApplicationLifecycle lifecycle) {

        final IFrame frame = new MyFrame();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed() {
                lifecycle.finish();
            }
        });

        frame.setVisible(true);
    }

    private final class MyFrame extends Frame {

        public MyFrame() {
            super("Base composite control snipped");

            setMinPackSize(new Dimension(300, 0));

            setLayout(FillLayout.get());

            final MyControl control = new MyControl(this, "growx, growy");
            control.setText1("Hello");
            control.setText2("World");
        }

    }

    private final class MyControl extends CompositeControl {

        private final IInputField<String> field1;
        private final IInputField<String> field2;

        public MyControl(final IContainer parent, final Object layoutConstraints) {
            super(parent, layoutConstraints);

            final IComposite composite = getComposite();

            composite.setLayout(new MigLayoutDescriptor("wrap", "[][grow]", "[][]"));

            composite.add(BPF.textLabel("Label 1"));
            field1 = composite.add(BPF.inputFieldString(), "growx");

            composite.add(BPF.textLabel("Label 2"));
            field2 = composite.add(BPF.inputFieldString(), "growx");
        }

        public void setText1(final String text) {
            field1.setValue(text);
        }

        public void setText2(final String text) {
            field2.setValue(text);
        }

    }
}
