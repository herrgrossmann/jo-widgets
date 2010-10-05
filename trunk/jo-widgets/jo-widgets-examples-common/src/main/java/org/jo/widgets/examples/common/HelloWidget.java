/*
 * Copyright (c) 2010, Michael Grossmann
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
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jo.widgets.examples.common;

import org.jo.widgets.api.look.AutoCenterPolicy;
import org.jo.widgets.api.look.Position;
import org.jo.widgets.api.widgets.IActionWidget;
import org.jo.widgets.api.widgets.ICompositeWidget;
import org.jo.widgets.api.widgets.IInputCompositeWidget;
import org.jo.widgets.api.widgets.IInputDialogWidget;
import org.jo.widgets.api.widgets.IInputWidget;
import org.jo.widgets.api.widgets.IRootWindowWidget;
import org.jo.widgets.api.widgets.IValidationLabelWidget;
import org.jo.widgets.api.widgets.blueprint.IInputCompositeBluePrint;
import org.jo.widgets.api.widgets.blueprint.IInputDialogBluePrint;
import org.jo.widgets.api.widgets.blueprint.IRootWindowBluePrint;
import org.jo.widgets.api.widgets.blueprint.IScrollCompositeBluePrint;
import org.jo.widgets.api.widgets.blueprint.ITextLabelBluePrint;
import org.jo.widgets.api.widgets.blueprint.IValidationLabelBluePrint;
import org.jo.widgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jo.widgets.api.widgets.blueprint.factory.impl.BluePrintFactory;
import org.jo.widgets.api.widgets.controler.IActionListener;
import org.jo.widgets.api.widgets.factory.IGenericWidgetFactory;
import org.jo.widgets.api.widgets.factory.INativeWidgetFactory;
import org.jo.widgets.api.widgets.factory.impl.GenericWidgetFactory;

public class HelloWidget {

	private final IRootWindowWidget rootWindow;

	public HelloWidget(final INativeWidgetFactory nativeWidgetFactory, final String title) {

		final IGenericWidgetFactory factory = new GenericWidgetFactory(nativeWidgetFactory);
		final BluePrintFactory bpF = new BluePrintFactory();

		// create the root window
		final IRootWindowBluePrint rootWindowBp = bpF.rootWindow(title);
		rootWindowBp.setMigLayout("[left, grow]", "[top, grow]");
		rootWindow = factory.createRootWindow(rootWindowBp);

		// create dialog
		final IInputDialogWidget<String> dialog = createDialogWidget(bpF);

		// create the scrolled content
		final IScrollCompositeBluePrint scrollCompositeBp = bpF.scrollComposite("Titled border");
		scrollCompositeBp.setMigLayout("[][grow, 250:250:800][260::]", "[]5[]5[]5[][][][][]");
		final ICompositeWidget group = rootWindow.add(scrollCompositeBp, "grow");

		// base descriptor for left labels
		final ITextLabelBluePrint labelBp = bpF.textLabel().alignRight();

		final IValidationLabelBluePrint validationLabelBp = bpF.validationLabel();

		// row1
		group.add(labelBp.setText("Number 1").setToolTipText("Very useful numbers here"), "sg lg");
		final IInputWidget<Long> widget1 = group.add(bpF.inputFieldLongNumber(), "growx");
		final IValidationLabelWidget valLabel1 = group.add(validationLabelBp, "wrap");
		valLabel1.registerInputWidget(widget1);

		// row2
		group.add(labelBp.setText("Number 2").setToolTipText("Very very useful numbers here"), "sg lg");
		final IInputWidget<Integer> widget2 = group.add(bpF.inputFieldIntegerNumber(), "growx");
		final IValidationLabelWidget valLabel2 = group.add(validationLabelBp, "wrap");
		valLabel2.registerInputWidget(widget2);

		// row3
		group.add(labelBp.setText("String").setToolTipText("Very special input here"), "sg lg");
		final IInputWidget<String> widget3 = group.add(bpF.textField(), "growx");
		final IValidationLabelWidget valLabel3 = group.add(validationLabelBp, "wrap");
		valLabel3.registerInputWidget(widget3);

		// row4
		group.add(bpF.textSeparator("Integrated input compsosite", "The tooltip text").alignCenter(), "grow, span");

		// row5
		final IInputCompositeBluePrint<String> inputCompositeBluePrint = bpF.inputComposite(new HelloContentCreator());
		inputCompositeBluePrint.setContentScrolled(false).setContentBorder();
		inputCompositeBluePrint.setMissingInputText("Do input all mandatory(*) fields!");
		final IInputCompositeWidget<String> inputComposite = group.add(inputCompositeBluePrint, "grow, span");

		// row6
		group.add(bpF.textSeparator("Button follows", "The tooltip text").alignCenter(), "grow, span");

		// row7
		group.add(labelBp.setText("Open dialog").setToolTipText("This opens an dialog"), "sg lg");
		final IActionWidget button = group.add(bpF.button("Open"), "growx");

		// open dialog on button
		button.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				dialog.setVisible(true);
				System.out.println(dialog.isOkPressed());
				System.out.println("Value:" + dialog.getValue());
			}
		});

		rootWindow.setPosition(new Position(200, 200));
	}

	public void start() {
		rootWindow.setVisible(true);
	}

	private IInputDialogWidget<String> createDialogWidget(final IBluePrintFactory bpF) {
		final IInputDialogBluePrint<String> inputDialogBp = bpF.inputDialog(new HelloContentCreator());
		inputDialogBp.setTitle("Test dialog").setAutoCenterPolicy(AutoCenterPolicy.ONCE);
		inputDialogBp.setOkButton("very ok", "This is very ok");
		inputDialogBp.setCancelButton("cancel user input", "cancel the current user input");
		inputDialogBp.setMissingInputText("Do input all mandatory(*) fields!");
		inputDialogBp.setContentBorder().setContentScrolled(true);
		final IInputDialogWidget<String> dialog = rootWindow.createChildWindow(inputDialogBp);

		return dialog;
	}

}
