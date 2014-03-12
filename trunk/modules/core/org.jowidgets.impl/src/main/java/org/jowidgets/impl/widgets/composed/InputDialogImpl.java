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
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.impl.widgets.composed;

import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.types.InputDialogDefaultButtonPolicy;
import org.jowidgets.api.widgets.IButton;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IFrame;
import org.jowidgets.api.widgets.IInputDialog;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.api.widgets.descriptor.IButtonDescriptor;
import org.jowidgets.api.widgets.descriptor.setup.IInputDialogSetup;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.common.widgets.controller.IComponentListener;
import org.jowidgets.common.widgets.controller.IFocusListener;
import org.jowidgets.common.widgets.controller.IInputListener;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.impl.widgets.composed.blueprint.BluePrintFactory;
import org.jowidgets.tools.widgets.wrapper.WindowWrapper;
import org.jowidgets.validation.IValidationConditionListener;
import org.jowidgets.validation.IValidationMessage;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidator;

public class InputDialogImpl<INPUT_TYPE> extends WindowWrapper implements IInputDialog<INPUT_TYPE> {

	private final IFrame dialogWidget;
	private final InputCompositeImpl<INPUT_TYPE> inputCompositeWidget;

	private INPUT_TYPE value;
	private boolean okPressed;

	public InputDialogImpl(final IFrame dialogWidget, final IInputDialogSetup<INPUT_TYPE> setup) {
		super(dialogWidget);
		this.okPressed = false;
		this.dialogWidget = dialogWidget;

		final IBluePrintFactory bpF = new BluePrintFactory();

		this.dialogWidget.setLayout(new MigLayoutDescriptor("0[grow, 0::]0", "0[grow, 0::][]"));

		// composite widget
		final IComposite compositeWidget = dialogWidget.add(
				bpF.composite().setBorder(setup.getBorder()),
				"growx, growy, h 0::,w 0::, wrap");
		this.inputCompositeWidget = new InputCompositeImpl<INPUT_TYPE>(compositeWidget, setup);

		// buttons
		final IComposite buttonBar = dialogWidget.add(bpF.composite(), "align right");
		buttonBar.setLayout(new MigLayoutDescriptor("[][]", "[]"));

		final String buttonCellConstraints = "w 100::, sg bg";

		final ValidationButton validationButton = new ValidationButton(setup.getOkButton(), buttonBar, buttonCellConstraints);

		final IButton okButton = validationButton.getButtonWidget();
		final IButton cancelButton = buttonBar.add(setup.getCancelButton(), buttonCellConstraints);

		if (setup.getDefaultButtonPolicy() == InputDialogDefaultButtonPolicy.OK) {
			dialogWidget.setDefaultButton(okButton);
		}
		else if (setup.getDefaultButtonPolicy() == InputDialogDefaultButtonPolicy.CANCEL) {
			dialogWidget.setDefaultButton(cancelButton);
		}
		else if (setup.getDefaultButtonPolicy() != InputDialogDefaultButtonPolicy.DISABLED) {
			throw new IllegalArgumentException(InputDialogDefaultButtonPolicy.class.getSimpleName()
				+ " '"
				+ setup.getDefaultButtonPolicy()
				+ "' is not supported");
		}

		okButton.addActionListener(new IActionListener() {

			@Override
			public void actionPerformed() {
				okPressed = true;
				value = inputCompositeWidget.getValue();
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new IActionListener() {

			@Override
			public void actionPerformed() {
				setVisible(false);
			}
		});

	}

	@Override
	public boolean hasModifications() {
		return inputCompositeWidget.hasModifications();
	}

	@Override
	public void resetModificationState() {
		inputCompositeWidget.resetModificationState();
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			this.okPressed = false;
		}
		dialogWidget.setVisible(visible);
	}

	@Override
	public void setMinSize(final Dimension minSize) {
		dialogWidget.setMinSize(minSize);
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		inputCompositeWidget.setForegroundColor(colorValue);
		dialogWidget.setForegroundColor(colorValue);
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		inputCompositeWidget.setBackgroundColor(colorValue);
		dialogWidget.setBackgroundColor(colorValue);
	}

	@Override
	public IColorConstant getForegroundColor() {
		return inputCompositeWidget.getForegroundColor();
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return inputCompositeWidget.getBackgroundColor();
	}

	@Override
	public void setCursor(final Cursor cursor) {
		inputCompositeWidget.setCursor(cursor);
	}

	@Override
	public void addInputListener(final IInputListener listener) {
		inputCompositeWidget.addInputListener(listener);
	}

	@Override
	public void removeInputListener(final IInputListener listener) {
		inputCompositeWidget.removeInputListener(listener);
	}

	@Override
	public void addValidationConditionListener(final IValidationConditionListener listener) {
		inputCompositeWidget.addValidationConditionListener(listener);
	}

	@Override
	public void removeValidationConditionListener(final IValidationConditionListener listener) {
		inputCompositeWidget.removeValidationConditionListener(listener);
	}

	@Override
	public void setValue(final INPUT_TYPE content) {
		inputCompositeWidget.setValue(content);
	}

	@Override
	public INPUT_TYPE getValue() {
		return value;
	}

	@Override
	public IValidationResult validate() {
		return inputCompositeWidget.validate();
	}

	@Override
	public boolean isOkPressed() {
		return okPressed;
	}

	@Override
	public void setEditable(final boolean editable) {
		inputCompositeWidget.setEditable(editable);
	}

	@Override
	public boolean isEditable() {
		return inputCompositeWidget.isEditable();
	}

	@Override
	public void addValidator(final IValidator<INPUT_TYPE> validator) {
		inputCompositeWidget.addValidator(validator);
	}

	@Override
	public boolean requestFocus() {
		return inputCompositeWidget.requestFocus();
	}

	@Override
	public void addFocusListener(final IFocusListener listener) {
		inputCompositeWidget.addFocusListener(listener);
	}

	@Override
	public void removeFocusListener(final IFocusListener listener) {
		inputCompositeWidget.removeFocusListener(listener);
	}

	@Override
	public void addKeyListener(final IKeyListener listener) {
		inputCompositeWidget.addKeyListener(listener);
	}

	@Override
	public void removeKeyListener(final IKeyListener listener) {
		inputCompositeWidget.removeKeyListener(listener);
	}

	@Override
	public void addMouseListener(final IMouseListener mouseListener) {
		inputCompositeWidget.addMouseListener(mouseListener);
	}

	@Override
	public void removeMouseListener(final IMouseListener mouseListener) {
		inputCompositeWidget.removeMouseListener(mouseListener);
	}

	@Override
	public void addComponentListener(final IComponentListener componentListener) {
		inputCompositeWidget.addComponentListener(componentListener);
	}

	@Override
	public void removeComponentListener(final IComponentListener componentListener) {
		inputCompositeWidget.removeComponentListener(componentListener);
	}

	@Override
	public IPopupMenu createPopupMenu() {
		return inputCompositeWidget.createPopupMenu();
	}

	@Override
	public void setPopupMenu(final IMenuModel popupMenu) {
		inputCompositeWidget.setPopupMenu(popupMenu);
	}

	@Override
	public void addPopupDetectionListener(final IPopupDetectionListener listener) {
		inputCompositeWidget.addPopupDetectionListener(listener);
	}

	@Override
	public void removePopupDetectionListener(final IPopupDetectionListener listener) {
		inputCompositeWidget.removePopupDetectionListener(listener);
	}

	private class ValidationButton {

		private final IButtonDescriptor buttonDescriptor;
		private final IContainer parentContainer;
		private final IButton buttonWidget;

		ValidationButton(final IButtonDescriptor buttonDescriptor, final IContainer parentContainer, final String cellConstraints) {
			super();
			this.parentContainer = parentContainer;
			this.buttonWidget = parentContainer.add(buttonDescriptor, cellConstraints);

			this.buttonDescriptor = new BluePrintFactory().button().setSetup(buttonDescriptor);

			inputCompositeWidget.addValidationConditionListener(new IValidationConditionListener() {
				@Override
				public void validationConditionsChanged() {
					onInputChanged();
				}
			});

			onInputChanged();
		}

		public IButton getButtonWidget() {
			return buttonWidget;
		}

		private void onInputChanged() {
			final IValidationMessage firstWorst = inputCompositeWidget.validate().getWorstFirst();
			setValidationResult(firstWorst);
		}

		private void setValidationResult(final IValidationMessage firstWorst) {
			parentContainer.layoutBegin();
			if (firstWorst.getType().isValid()) {
				buttonWidget.setEnabled(true);
				buttonWidget.setToolTipText(buttonDescriptor.getToolTipText());
			}
			else {
				buttonWidget.setEnabled(false);
				buttonWidget.setToolTipText(firstWorst.getText());
			}
			parentContainer.layoutEnd();

		}
	}

}
