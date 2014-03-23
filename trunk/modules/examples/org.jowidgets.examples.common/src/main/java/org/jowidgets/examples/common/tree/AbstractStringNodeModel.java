/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.examples.common.tree;

import org.jowidgets.api.model.tree.ITreeNodeModel;
import org.jowidgets.api.model.tree.ITreeNodeRenderer;
import org.jowidgets.tools.model.tree.AbstractTreeNodeModel;
import org.jowidgets.tools.model.tree.DefaultTreeNodeRenderer;
import org.jowidgets.tools.model.tree.TreeNodeModelAdapter;

abstract class AbstractStringNodeModel extends AbstractTreeNodeModel<String> implements ITreeNodeModel<String> {

	private static final ITreeNodeRenderer<String> RENDERER = new DefaultTreeNodeRenderer<String>();

	private final String data;

	AbstractStringNodeModel(final String data) {
		this.data = data;
		setExpanded(true);

		addTreeNodeModelListener(new TreeNodeModelAdapter() {
			@Override
			public void selectionChanged() {
				//CHECKSTYLE:OFF
				System.out.println("SELECTION CHANGED: " + isSelected() + " / " + getData());
				//CHECKSTYLE:ON
			}

			@Override
			public void checkedChanged() {
				//CHECKSTYLE:OFF
				System.out.println("CHECKED CHANGED: " + getCheckedState() + " / " + getData());
				//CHECKSTYLE:ON
			}

			@Override
			public void expansionChanged() {
				//CHECKSTYLE:OFF
				System.out.println("EXPANSION CHANGED: " + isExpanded() + " / " + getData());
				//CHECKSTYLE:ON
			}

		});
	}

	@Override
	public final ITreeNodeRenderer<String> getRenderer() {
		return RENDERER;
	}

	@Override
	public final String getData() {
		return data;
	}

}
