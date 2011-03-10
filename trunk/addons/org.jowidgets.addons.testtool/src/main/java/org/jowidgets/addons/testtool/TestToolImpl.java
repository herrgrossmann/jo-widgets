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

package org.jowidgets.addons.testtool;

import org.jowidgets.addons.testtool.internal.TestToolUtilities;
import org.jowidgets.addons.testtool.internal.UserAction;
import org.jowidgets.api.controler.ITabItemListener;
import org.jowidgets.api.controler.ITreeListener;
import org.jowidgets.api.controler.ITreePopupDetectionListener;
import org.jowidgets.api.controler.ITreePopupEvent;
import org.jowidgets.api.controler.ITreeSelectionEvent;
import org.jowidgets.api.controler.ITreeSelectionListener;
import org.jowidgets.api.model.IListModelListener;
import org.jowidgets.api.widgets.IFrame;
import org.jowidgets.api.widgets.ITabFolder;
import org.jowidgets.api.widgets.ITabItem;
import org.jowidgets.api.widgets.IToolBar;
import org.jowidgets.api.widgets.ITree;
import org.jowidgets.api.widgets.ITreeNode;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.IWidgetCommon;
import org.jowidgets.common.widgets.controler.IActionListener;
import org.jowidgets.common.widgets.controler.IPopupDetectionListener;
import org.jowidgets.test.api.widgets.IButtonUi;

public final class TestToolImpl implements ITestTool {

	private final TestToolUtilities testToolUtilities;

	public TestToolImpl() {
		testToolUtilities = new TestToolUtilities();
	}

	// TODO LG save recorded user actions
	// TODO LG generate and add id (maybe in utility class)
	// TODO LG Remove Checkstyle off/on
	@Override
	public void record(final IWidgetCommon widget, final UserAction action, final String id) {
		// CHECKSTYLE:OFF
		System.out.println("Recorded: "
			+ widget.getClass().getSimpleName()
			+ "\nwith ID : "
			+ id
			+ "\nAction was: "
			+ action.toString());
		// CHECKSTYLE:ON
	}

	// TODO LG implement
	@Override
	public void replay() {}

	@Override
	public void register(final IWidgetCommon widget) {
		if (widget instanceof IFrame) {
			final IFrame frame = (IFrame) widget;
			frame.getMenuBarModel().addListModelListener(new IListModelListener() {

				@Override
				public void childRemoved(final int index) {
					System.out.println("Menuitem removed at index : " + index);
				}

				@Override
				public void childAdded(final int index) {
					System.out.println("Menuitem added at index : " + index);
				}
			});
		}
		if (widget instanceof IButtonUi) {
			final IButtonUi button = (IButtonUi) widget;
			button.addActionListener(new IActionListener() {

				@Override
				public void actionPerformed() {
					record(widget, UserAction.CLICK, testToolUtilities.createWidgetID(button));
				}
			});
		}
		// TODO LG check why the listener doesnt work on toolbar
		if (widget instanceof IToolBar) {
			final IToolBar toolBar = (IToolBar) widget;
			toolBar.getModel().addListModelListener(new IListModelListener() {

				@Override
				public void childRemoved(final int index) {
					System.out.println("child removed at " + index);
				}

				@Override
				public void childAdded(final int index) {
					System.out.println("child added at " + index);
				}
			});
			toolBar.addPopupDetectionListener(new IPopupDetectionListener() {

				@Override
				public void popupDetected(final Position position) {
					System.out.println("popup at position: " + position);
				}
			});
		}
		// TODO LG use ITreeUi instead of ITree
		if (widget instanceof ITree) {
			final ITree tree = (ITree) widget;
			tree.addTreeSelectionListener(new ITreeSelectionListener() {

				@Override
				public void selectionChanged(final ITreeSelectionEvent event) {
					record(widget, UserAction.SELECT, testToolUtilities.createWidgetID(event.getSelectedSingle()));
				}
			});
			tree.addTreeListener(new ITreeListener() {

				@Override
				public void nodeExpanded(final ITreeNode node) {
					record(widget, UserAction.SELECT, testToolUtilities.createWidgetID(node));
				}

				@Override
				public void nodeCollapsed(final ITreeNode node) {
					record(widget, UserAction.SELECT, testToolUtilities.createWidgetID(node));
				}
			});
			tree.addTreePopupDetectionListener(new ITreePopupDetectionListener() {

				@Override
				public void popupDetected(final ITreePopupEvent event) {
					record(widget, UserAction.CLICK, testToolUtilities.createWidgetID(event.getNode()));
				}
			});
		}
		if (widget instanceof ITabFolder) {
			final ITabFolder folder = (ITabFolder) widget;
			folder.addPopupDetectionListener(new IPopupDetectionListener() {

				@Override
				public void popupDetected(final Position position) {
					record(widget, UserAction.CLICK, "");
				}
			});
			for (final ITabItem item : folder.getItems()) {
				item.addTabItemListener(new ITabItemListener() {

					@Override
					public void selectionChanged(final boolean selected) {
						System.out.println(selected);
					}

					@Override
					public void onClose(final IVetoable vetoable) {
						System.out.println(vetoable);
					}
				});
			}
		}
	}
}
