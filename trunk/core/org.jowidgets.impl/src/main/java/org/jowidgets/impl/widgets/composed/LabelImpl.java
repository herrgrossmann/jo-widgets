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
import org.jowidgets.api.widgets.IComponent;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.ILabel;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.descriptor.IIconDescriptor;
import org.jowidgets.api.widgets.descriptor.ITextLabelDescriptor;
import org.jowidgets.api.widgets.descriptor.setup.ILabelSetup;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.IComponentCommon;
import org.jowidgets.common.widgets.IIconCommon;
import org.jowidgets.common.widgets.ITextLabelCommon;
import org.jowidgets.common.widgets.controler.IPopupDetectionListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.impl.widgets.basic.factory.internal.util.ColorSettingsInvoker;
import org.jowidgets.impl.widgets.basic.factory.internal.util.VisibiliySettingsInvoker;
import org.jowidgets.impl.widgets.composed.blueprint.BluePrintFactory;

public class LabelImpl implements ILabel {

	private final IIconCommon iconWidget;
	private final ITextLabelCommon textLabelWidget;
	private final IComposite compositeWidget;

	public LabelImpl(final IComposite compositeWidget, final ILabelSetup setup) {

		super();

		this.compositeWidget = compositeWidget;
		this.compositeWidget.setLayout(new MigLayoutDescriptor("0[][]0", "0[]0"));

		final BluePrintFactory bpF = new BluePrintFactory();

		final IIconDescriptor iconDescriptor = bpF.icon(setup.getIcon());
		this.iconWidget = compositeWidget.add(iconDescriptor, "w 0::");

		final ITextLabelDescriptor textLabelDescriptor = bpF.textLabel().setSetup(setup);
		this.textLabelWidget = compositeWidget.add(textLabelDescriptor, "");

		VisibiliySettingsInvoker.setVisibility(setup, this);
		ColorSettingsInvoker.setColors(setup, this);
	}

	@Override
	public void setText(final String text) {
		textLabelWidget.setText(text);
	}

	@Override
	public void setToolTipText(final String text) {
		textLabelWidget.setToolTipText(text);
		iconWidget.setToolTipText(text);
	}

	@Override
	public IContainer getParent() {
		return compositeWidget.getParent();
	}

	@Override
	public void setParent(final IComponent parent) {
		compositeWidget.setParent(parent);
	}

	@Override
	public boolean isReparentable() {
		return compositeWidget.isReparentable();
	}

	@Override
	public Object getUiReference() {
		return compositeWidget.getUiReference();
	}

	@Override
	public void setLayoutConstraints(final Object layoutConstraints) {
		compositeWidget.setLayoutConstraints(layoutConstraints);
	}

	@Override
	public Object getLayoutConstraints() {
		return compositeWidget.getLayoutConstraints();
	}

	@Override
	public void redraw() {
		compositeWidget.redraw();
	}

	@Override
	public void setRedrawEnabled(final boolean enabled) {
		compositeWidget.setRedrawEnabled(enabled);
	}

	@Override
	public void setCursor(final Cursor cursor) {
		compositeWidget.setCursor(cursor);
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		textLabelWidget.setForegroundColor(colorValue);
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		textLabelWidget.setBackgroundColor(colorValue);
		iconWidget.setBackgroundColor(colorValue);
	}

	@Override
	public IColorConstant getForegroundColor() {
		return textLabelWidget.getForegroundColor();
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return textLabelWidget.getBackgroundColor();
	}

	@Override
	public void setVisible(final boolean visible) {
		compositeWidget.setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return compositeWidget.isVisible();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		textLabelWidget.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {
		return textLabelWidget.isEnabled();
	}

	@Override
	public Dimension getSize() {
		return compositeWidget.getSize();
	}

	@Override
	public void setSize(final Dimension size) {
		compositeWidget.setSize(size);
	}

	@Override
	public Position getPosition() {
		return compositeWidget.getPosition();
	}

	@Override
	public void setPosition(final Position position) {
		compositeWidget.setPosition(position);
	}

	@Override
	public Position toScreen(final Position localPosition) {
		return compositeWidget.toScreen(localPosition);
	}

	@Override
	public Position toLocal(final Position screenPosition) {
		return compositeWidget.toLocal(screenPosition);
	}

	@Override
	public Position fromComponent(final IComponentCommon component, final Position componentPosition) {
		return compositeWidget.fromComponent(component, componentPosition);
	}

	@Override
	public Position toComponent(final Position componentPosition, final IComponentCommon component) {
		return compositeWidget.toComponent(componentPosition, component);
	}

	@Override
	public IPopupMenu createPopupMenu() {
		return compositeWidget.createPopupMenu();
	}

	@Override
	public void setPopupMenu(final IMenuModel popupMenu) {
		//TODO MG this might not work, popup must be set on label an text also. 
		//For that, label and texfield must be api widgets 
		compositeWidget.setPopupMenu(popupMenu);
	}

	@Override
	public void addPopupDetectionListener(final IPopupDetectionListener listener) {
		compositeWidget.addPopupDetectionListener(listener);
	}

	@Override
	public void removePopupDetectionListener(final IPopupDetectionListener listener) {
		compositeWidget.removePopupDetectionListener(listener);
	}

	@Override
	public void setMarkup(final Markup markup) {
		textLabelWidget.setMarkup(markup);
	}

	@Override
	public void setIcon(final IImageConstant icon) {
		iconWidget.setIcon(icon);
	}

}
