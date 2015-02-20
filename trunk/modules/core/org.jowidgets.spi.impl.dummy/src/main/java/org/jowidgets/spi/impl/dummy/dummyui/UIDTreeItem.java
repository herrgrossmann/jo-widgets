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

package org.jowidgets.spi.impl.dummy.dummyui;

import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Markup;

public class UIDTreeItem extends UIDComponent {

	private boolean expanded;
	private boolean checked;
	private boolean greyed;
	private boolean checkable;
	private String text;
	private IImageConstant icon;
	private Markup markup;

	public UIDTreeItem() {
		this.checkable = true;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(final boolean expanded) {
		this.expanded = expanded;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public IImageConstant getIcon() {
		return icon;
	}

	public void setIcon(final IImageConstant icon) {
		this.icon = icon;
	}

	public Markup getMarkup() {
		return markup;
	}

	public void setMarkup(final Markup markup) {
		this.markup = markup;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setGreyed(final boolean greyed) {
		this.greyed = greyed;
	}

	public boolean isGreyed() {
		return greyed;
	}

	public void setCheckable(final boolean checkable) {
		this.checkable = checkable;
	}

	public boolean isCheckable() {
		return checkable;
	}
}
