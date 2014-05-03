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
package org.jowidgets.impl.widgets.basic.blueprint;

import java.util.Collection;
import java.util.List;

import org.jowidgets.api.convert.IObjectStringConverter;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.blueprint.IActionMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.IButtonBluePrint;
import org.jowidgets.api.widgets.blueprint.ICheckedMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.IComboBoxBluePrint;
import org.jowidgets.api.widgets.blueprint.IComboBoxSelectionBluePrint;
import org.jowidgets.api.widgets.blueprint.ICompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.IDialogBluePrint;
import org.jowidgets.api.widgets.blueprint.IFrameBluePrint;
import org.jowidgets.api.widgets.blueprint.IIconBluePrint;
import org.jowidgets.api.widgets.blueprint.IMainMenuBluePrint;
import org.jowidgets.api.widgets.blueprint.IRadioMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.IScrollCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.ISplitCompositeBluePrint;
import org.jowidgets.api.widgets.blueprint.ISubMenuBluePrint;
import org.jowidgets.api.widgets.blueprint.ITextLabelBluePrint;
import org.jowidgets.api.widgets.blueprint.convenience.ISetupBuilderConvenienceRegistry;
import org.jowidgets.api.widgets.blueprint.defaults.IDefaultsInitializerRegistry;
import org.jowidgets.api.widgets.blueprint.factory.IBasicBluePrintFactory;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Orientation;
import org.jowidgets.impl.convert.DefaultObjectStringConverter;

public class BasicBluePrintFactory extends BasicSimpleBluePrintFactory implements IBasicBluePrintFactory {

	public BasicBluePrintFactory(
		final ISetupBuilderConvenienceRegistry setupBuilderConvenienceRegistry,
		final IDefaultsInitializerRegistry defaultInitializerRegistry) {
		super(setupBuilderConvenienceRegistry, defaultInitializerRegistry);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////some convenience methods starting here///////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public final IFrameBluePrint frame(final String title) {
		return frame().setTitle(title);
	}

	@Override
	public final IFrameBluePrint frame(final String title, final IImageConstant icon) {
		return frame(title).setIcon(icon);
	}

	@Override
	public final IDialogBluePrint dialog(final String title) {
		return dialog().setTitle(title);
	}

	@Override
	public final IDialogBluePrint dialog(final String title, final IImageConstant icon) {
		return dialog(title).setIcon(icon);
	}

	@Override
	public final ICompositeBluePrint compositeWithBorder() {
		return composite().setBorder();
	}

	@Override
	public final ICompositeBluePrint composite(final String borderTitle) {
		return composite().setBorder(borderTitle);
	}

	@Override
	public ISplitCompositeBluePrint splitHorizontal() {
		return splitComposite().setOrientation(Orientation.HORIZONTAL);
	}

	@Override
	public ISplitCompositeBluePrint splitVertical() {
		return splitComposite().setOrientation(Orientation.VERTICAL);
	}

	@Override
	public final IButtonBluePrint button(final String text) {
		return button().setText(text);
	}

	@Override
	public final IButtonBluePrint button(final String text, final String toolTipText) {
		return button(text).setToolTipText(toolTipText);
	}

	@Override
	public IButtonBluePrint buttonOk() {
		return button(Messages.getString("BasicBluePrintFactory.ok"));
	}

	@Override
	public IButtonBluePrint buttonCancel() {
		return button(Messages.getString("BasicBluePrintFactory.cancel"));
	}

	@Override
	public IButtonBluePrint buttonClose() {
		return button(Messages.getString("BasicBluePrintFactory.close"));
	}

	@Override
	public IButtonBluePrint buttonDetails() {
		return button(Messages.getString("BasicBluePrintFactory.details"));
	}

	@Override
	public final IIconBluePrint icon(final IImageConstant icon) {
		return icon().setIcon(icon);
	}

	@Override
	public final ITextLabelBluePrint textLabel(final String text) {
		return textLabel().setText(text);
	}

	@Override
	public final ITextLabelBluePrint textLabel(final String text, final String tooltipText) {
		return textLabel(text).setToolTipText(tooltipText);
	}

	@Override
	public final IComboBoxBluePrint<String> comboBox() {
		return comboBox(Toolkit.getConverterProvider().string());
	}

	@Override
	public final IComboBoxBluePrint<String> comboBox(final String... elements) {
		return comboBox().setElements(elements);
	}

	@Override
	public IComboBoxBluePrint<String> comboBox(final List<String> elements) {
		return comboBox().setElements(elements);
	}

	@Override
	public final IComboBoxSelectionBluePrint<String> comboBoxSelection() {
		return comboBoxSelection(Toolkit.getConverterProvider().string());
	}

	@Override
	public final IComboBoxSelectionBluePrint<String> comboBoxSelection(final String... elements) {
		return comboBoxSelection().setElements(elements);
	}

	@Override
	public IComboBoxSelectionBluePrint<String> comboBoxSelection(final List<String> elements) {
		return comboBoxSelection().setElements(elements);
	}

	@Override
	public final <ENUM_TYPE extends Enum<?>> IComboBoxSelectionBluePrint<ENUM_TYPE> comboBoxSelection(
		final ENUM_TYPE... enumValues) {
		final IObjectStringConverter<ENUM_TYPE> converter = DefaultObjectStringConverter.getInstance();
		return comboBoxSelection(converter).setElements(enumValues);
	}

	@Override
	public <VALUE_TYPE> IComboBoxSelectionBluePrint<VALUE_TYPE> comboBoxSelection(final Collection<VALUE_TYPE> elements) {
		final IObjectStringConverter<VALUE_TYPE> converter = DefaultObjectStringConverter.getInstance();
		return comboBoxSelection(converter).setElements(elements);
	}

	@Override
	public IComboBoxBluePrint<String> comboBoxString() {
		return comboBox(Toolkit.getConverterProvider().string());
	}

	@Override
	public IComboBoxBluePrint<Long> comboBoxLongNumber() {
		return comboBox(Toolkit.getConverterProvider().longNumber());
	}

	@Override
	public IComboBoxBluePrint<Integer> comboBoxIntegerNumber() {
		return comboBox(Toolkit.getConverterProvider().integerNumber());
	}

	@Override
	public IComboBoxBluePrint<Short> comboBoxShortNumber() {
		return comboBox(Toolkit.getConverterProvider().shortNumber());
	}

	@Override
	public IComboBoxSelectionBluePrint<String> comboBoxSelectionString() {
		return comboBoxSelection(Toolkit.getConverterProvider().string());
	}

	@Override
	public IComboBoxSelectionBluePrint<Long> comboBoxSelectionLongNumber() {
		return comboBoxSelection(Toolkit.getConverterProvider().longNumber());
	}

	@Override
	public IComboBoxSelectionBluePrint<Integer> comboBoxSelectionIntegerNumber() {
		return comboBoxSelection(Toolkit.getConverterProvider().integerNumber());
	}

	@Override
	public IComboBoxSelectionBluePrint<Short> comboBoxSelectionShortNumber() {
		return comboBoxSelection(Toolkit.getConverterProvider().shortNumber());
	}

	@Override
	public IComboBoxSelectionBluePrint<Boolean> comboBoxSelectionBoolean() {
		final IComboBoxSelectionBluePrint<Boolean> result = comboBoxSelection(Toolkit.getConverterProvider().boolLong());
		result.setElements(Boolean.TRUE, Boolean.FALSE);
		return result;
	}

	@Override
	public final IScrollCompositeBluePrint scrollCompositeWithBorder() {
		return scrollComposite().setBorder();
	}

	@Override
	public final IScrollCompositeBluePrint scrollComposite(final String borderTitle) {
		return scrollComposite().setBorder(borderTitle);
	}

	@Override
	public IActionMenuItemBluePrint menuItem(final String text) {
		return menuItem().setText(text);
	}

	@Override
	public IRadioMenuItemBluePrint radioMenuItem(final String text) {
		return radioMenuItem().setText(text);
	}

	@Override
	public ICheckedMenuItemBluePrint checkedMenuItem(final String text) {
		return checkedMenuItem().setText(text);
	}

	@Override
	public ISubMenuBluePrint subMenu(final String text) {
		return subMenu().setText(text);
	}

	@Override
	public IMainMenuBluePrint mainMenu(final String text) {
		return mainMenu().setText(text);
	}

}
