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

package org.jowidgets.examples.common.demo;

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IButton;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;

public class DemoMessagesComposite {

    public DemoMessagesComposite(final IContainer parentContainer) {

        final IBluePrintFactory bpF = Toolkit.getBluePrintFactory();

        parentContainer.setLayout(new MigLayoutDescriptor("[300::, grow]", "[][][][][]"));

        final IButton infoMessageButton = parentContainer.add(
                bpF.button("Info message demo", "Shows an info message"),
                "grow, sg bg, wrap");
        infoMessageButton.addActionListener(new IActionListener() {
            @Override
            public void actionPerformed() {
                Toolkit.getMessagePane().showInfo(
                        "Info message title",
                        "This is an info message! \nHere comes some more information in text form about the kitchens sink.");
            }
        });

        final IButton warningMessageButton = parentContainer.add(
                bpF.button("Warning message demo", "Shows an warning message"),
                "grow, sg bg, wrap");
        warningMessageButton.addActionListener(new IActionListener() {
            @Override
            public void actionPerformed() {
                Toolkit.getMessagePane().showWarning(
                        "Warning message title",
                        "This is warning message! \nHere comes some more information in text form about the kitchens sink.");
            }
        });

        final IButton errorMessageButton = parentContainer.add(
                bpF.button("Error message demo", "Shows an error message"),
                "grow, sg bg, wrap");
        errorMessageButton.addActionListener(new IActionListener() {
            @Override
            public void actionPerformed() {
                Toolkit.getMessagePane().showError(
                        "Error message title",
                        "This is an error message! \nHere comes some more information in text form about the kitchens sink.");
            }
        });

        final IButton yesNoQuestionButton = parentContainer.add(
                bpF.button("Yes/No question demo", "Shows an Yes / No question"),
                "grow, sg bg, wrap");
        yesNoQuestionButton.addActionListener(new IActionListener() {
            @Override
            public void actionPerformed() {
                Toolkit.getQuestionPane()
                        .askYesNoQuestion(
                                "Question title",
                                "This is an important question! \nHere comes some more information in text form about the kitchens sink.");
            }
        });

        final IButton yesNoCancelQuestionButton = parentContainer.add(
                bpF.button("Yes/No/Cancel question demo", "Shows an Yes/No/Cancel question"),
                "grow, sg bg, wrap");
        yesNoCancelQuestionButton.addActionListener(new IActionListener() {
            @Override
            public void actionPerformed() {
                Toolkit.getQuestionPane()
                        .askYesNoCancelQuestion(
                                "Question title",
                                "This is an important question! \nHere comes some more information in text form about the kitchens sink.");
            }
        });

    }

}
