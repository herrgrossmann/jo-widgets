/*
 * Copyright (c) 2011, grossmann
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

package org.jowidgets.api.model.item;

import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Accelerator;
import org.jowidgets.common.types.Modifier;

public interface IItemModel extends IItemModelObservable {

	String getId();

	String getText();

	String getToolTipText();

	IImageConstant getIcon();

	Accelerator getAccelerator();

	Character getMnemonic();

	boolean isEnabled();

	void setId(String id);

	void setText(final String text);

	void setToolTipText(String toolTipText);

	void setIcon(IImageConstant icon);

	void setAccelerator(Accelerator accelerator);

	void setAccelerator(final char key, final Modifier... modifier);

	void setMnemonic(Character mnemonic);

	void setMnemonic(char mnemonic);

	void setEnabled(boolean enabled);

	/**
	 * Makes a deep copy of the item and its children.
	 * Registered listeners on items won't be copied.
	 * 
	 * @return A new instance that is a clone of this instance
	 */
	IItemModel createCopy();

}
