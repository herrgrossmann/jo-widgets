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

import org.jowidgets.api.dnd.IDragSource;
import org.jowidgets.api.dnd.IDropTarget;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.ILabel;
import org.jowidgets.api.widgets.ITextLabel;
import org.jowidgets.api.widgets.descriptor.IIconDescriptor;
import org.jowidgets.api.widgets.descriptor.ITextLabelDescriptor;
import org.jowidgets.api.widgets.descriptor.setup.ILabelSetup;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.widgets.IIconCommon;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.tools.widgets.blueprint.BPF;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;
import org.jowidgets.tools.widgets.wrapper.ControlWrapper;
import org.jowidgets.util.NullCompatibleEquivalence;

public class LabelImpl extends ControlWrapper implements ILabel {

	private final IIconCommon iconWidget;
	private final ITextLabel textLabelWidget;
	private final IComposite composite;
	private String text;
	private IImageConstant icon;

	public LabelImpl(final IComposite composite, final ILabelSetup setup) {

		super(composite);

		this.composite = composite;

		final IIconDescriptor iconDescriptor = BPF.icon(setup.getIcon()).setSetup(setup);
		this.iconWidget = composite.add(iconDescriptor, "w 0::");
		this.icon = setup.getIcon();

		final ITextLabelDescriptor textLabelDescriptor = BPF.textLabel().setSetup(setup);
		this.textLabelWidget = composite.add(textLabelDescriptor, "w 0::");

		setLayout();

		VisibiliySettingsInvoker.setVisibility(setup, this);
		ColorSettingsInvoker.setColors(setup, this);
	}

	@Override
	public void setText(final String text) {
		if (!NullCompatibleEquivalence.equals(text, this.text)) {
			this.text = text;
			textLabelWidget.setText(text);
			composite.layoutBegin();
			composite.layoutEnd();
		}
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setToolTipText(final String text) {
		textLabelWidget.setToolTipText(text);
		iconWidget.setToolTipText(text);
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
	public void setEnabled(final boolean enabled) {
		textLabelWidget.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled() {
		return textLabelWidget.isEnabled();
	}

	@Override
	public void setPopupMenu(final IMenuModel popupMenu) {
		//TODO MG this might not work, popup must be set on label an text also. 
		//For that, label and texfield must be api widgets 
		composite.setPopupMenu(popupMenu);
	}

	@Override
	public void addPopupDetectionListener(final IPopupDetectionListener listener) {
		composite.addPopupDetectionListener(listener);
	}

	@Override
	public void removePopupDetectionListener(final IPopupDetectionListener listener) {
		composite.removePopupDetectionListener(listener);
	}

	@Override
	public void setMarkup(final Markup markup) {
		textLabelWidget.setMarkup(markup);
	}

	@Override
	public void setFontSize(final int size) {
		textLabelWidget.setFontSize(size);
	}

	@Override
	public void setFontName(final String fontName) {
		textLabelWidget.setFontName(fontName);
	}

	@Override
	public void setIcon(final IImageConstant icon) {
		final boolean layoutChanged = (icon == null && this.icon != null) || (icon != null && this.icon == null);
		this.icon = icon;

		final Dimension lastPreferredSize = iconWidget.getPreferredSize();
		iconWidget.setIcon(icon);
		if (layoutChanged) {
			setLayout();
		}
		if (!lastPreferredSize.equals(iconWidget.getPreferredSize()) || layoutChanged) {
			composite.layoutBegin();
			composite.layoutEnd();
		}

	}

	private void setLayout() {
		if (icon == null) {
			//TODO This will no work under swt, because it leads to space greedy label
			//this.composite.setLayout(new MigLayoutDescriptor("0[]0[grow, 0::]0", "0[]0"));
			this.composite.setLayout(new MigLayoutDescriptor("0[][grow, 0::]0", "0[]0"));
		}
		else {
			this.composite.setLayout(new MigLayoutDescriptor("0[][grow, 0::]0", "0[]0"));
		}
	}

	@Override
	public IImageConstant getIcon() {
		return icon;
	}

	@Override
	public IDragSource getDragSource() {
		return textLabelWidget.getDragSource();
	}

	@Override
	public IDropTarget getDropTarget() {
		return textLabelWidget.getDropTarget();
	}

}
