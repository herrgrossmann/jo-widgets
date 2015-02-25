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

package org.jowidgets.api.widgets;

import java.util.List;

import org.jowidgets.api.controller.ITreeNodeCheckableObservable;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.types.CheckedState;
import org.jowidgets.common.widgets.ITreeNodeCommon;
import org.jowidgets.common.widgets.controller.IKeyObservable;

public interface ITreeNode extends IItem, ITreeContainer, ITreeNodeCheckableObservable, IKeyObservable, ITreeNodeCommon {

	/**
	 * @return The tree this node belongs to
	 */
	ITree getTree();

	/**
	 * @return True if this node is a leaf (has no children), false otherwise.
	 */
	boolean isLeaf();

	/**
	 * @return True if this node is a top level node (has no parent), false otherwise
	 */
	boolean isTopLevel();

	/**
	 * @return Gets the path from the top level parent of this node to this node
	 *         (this node is included into the path)
	 */
	List<ITreeNode> getPath();

	/**
	 * @return A IPopupMenu for this node
	 */
	IPopupMenu createPopupMenu();

	/**
	 * Sets a popup menu for this node.
	 * The popup menu will be shown, when an popup event occurs on this node.
	 * 
	 * @param menuModel
	 *            The model of the popup menu or null, if no popup should be shown on popup events
	 */
	void setPopupMenu(IMenuModel popupMenu);

	@Override
	ITreeNode getParent();

	CheckedState getCheckedState();

	void setCheckedState(CheckedState state);

	/**
	 * Sets the node to the greyed state.
	 * If set, the isChecked() and isUnchecked() methods will return false both,
	 * and the isGreyed() method will return true
	 */
	void setGreyed();

	/**
	 * Checks if node is unchecked. If true it is not checked and not greyed, if false it my be greyed or checked
	 * 
	 * @return The unchecked state
	 */
	boolean isUnchecked();

	boolean isCheckable();
}
