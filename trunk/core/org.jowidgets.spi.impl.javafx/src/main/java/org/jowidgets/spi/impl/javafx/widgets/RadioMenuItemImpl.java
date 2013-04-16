/*
 * Copyright (c) 2012, David Bauknecht
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

package org.jowidgets.spi.impl.javafx.widgets;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.RadioMenuItem;

import org.jowidgets.common.widgets.controller.IItemStateListener;
import org.jowidgets.spi.impl.controller.ItemStateObservable;
import org.jowidgets.spi.widgets.ISelectableMenuItemSpi;

public class RadioMenuItemImpl extends MenuItemImpl implements ISelectableMenuItemSpi {
	private final ItemStateObservable itemStateObservable;

	public RadioMenuItemImpl() {
		this(new RadioMenuItem(""));

	}

	public RadioMenuItemImpl(final RadioMenuItem radioItem) {
		super(radioItem);
		this.itemStateObservable = new ItemStateObservable();

		getUiReference().selectedProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(final Observable paramObservable) {
				itemStateObservable.fireItemStateChanged();

			}
		});
	}

	@Override
	public RadioMenuItem getUiReference() {
		return (RadioMenuItem) super.getUiReference();
	}

	@Override
	public boolean isSelected() {
		return getUiReference().isSelected();
	}

	@Override
	public void setSelected(final boolean selected) {
		getUiReference().setSelected(selected);
	}

	@Override
	public void addItemListener(final IItemStateListener listener) {
		itemStateObservable.addItemListener(listener);

	}

	@Override
	public void removeItemListener(final IItemStateListener listener) {
		itemStateObservable.removeItemListener(listener);

	}

}
