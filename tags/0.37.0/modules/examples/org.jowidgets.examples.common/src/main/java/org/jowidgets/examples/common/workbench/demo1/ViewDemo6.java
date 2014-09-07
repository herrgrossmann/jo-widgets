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

package org.jowidgets.examples.common.workbench.demo1;

import java.util.ArrayList;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.color.Colors;
import org.jowidgets.api.model.item.IActionItemModel;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.api.model.table.IDefaultTableColumnBuilder;
import org.jowidgets.api.model.table.IDefaultTableColumnModel;
import org.jowidgets.api.model.table.ITableCellBuilder;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.ITable;
import org.jowidgets.api.widgets.blueprint.ITableBluePrint;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.model.ITableCell;
import org.jowidgets.common.model.ITableDataModel;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.common.widgets.controller.IActionListener;
import org.jowidgets.common.widgets.controller.ITableCellEvent;
import org.jowidgets.common.widgets.controller.ITableCellListener;
import org.jowidgets.common.widgets.controller.ITableCellMouseEvent;
import org.jowidgets.common.widgets.controller.ITableCellPopupDetectionListener;
import org.jowidgets.common.widgets.controller.ITableCellPopupEvent;
import org.jowidgets.common.widgets.controller.ITableColumnListener;
import org.jowidgets.common.widgets.controller.ITableColumnMouseEvent;
import org.jowidgets.common.widgets.controller.ITableColumnPopupDetectionListener;
import org.jowidgets.common.widgets.controller.ITableColumnPopupEvent;
import org.jowidgets.common.widgets.controller.ITableColumnResizeEvent;
import org.jowidgets.common.widgets.controller.ITableSelectionListener;
import org.jowidgets.examples.common.demo.DemoMenuProvider;
import org.jowidgets.examples.common.workbench.base.AbstractDemoView;
import org.jowidgets.tools.editor.TextFieldCellEditorFactory;
import org.jowidgets.tools.editor.TextFieldCellEditorFactory.ITableCellEditEvent;
import org.jowidgets.tools.editor.TextFieldCellEditorFactory.ITableCellEditorListener;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.tools.model.table.AbstractTableDataModel;
import org.jowidgets.tools.model.table.DefaultTableColumnBuilder;
import org.jowidgets.tools.model.table.DefaultTableColumnModel;
import org.jowidgets.tools.model.table.TableCellBuilder;
import org.jowidgets.util.ValueHolder;
import org.jowidgets.workbench.api.IComponentNodeContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;

public class ViewDemo6 extends AbstractDemoView implements IView {

	public static final String ID = ViewDemo6.class.getName();
	public static final String DEFAULT_LABEL = "View6";
	public static final String DEFAULT_TOOLTIP = "View6 tooltip";
	public static final IImageConstant DEFAULT_ICON = SilkIcons.ROSETTE;

	public ViewDemo6(final IViewContext context, final DemoMenuProvider menuProvider) {
		super(ID);
		context.getToolBar().addItemsOfModel(menuProvider.getToolBarModel());
		context.getToolBarMenu().addItemsOfModel(menuProvider.getMenuModel());

		final ActionFactory actionFactory = new ActionFactory();
		final IComponentNodeContext treeNodeContent = context.getComponentNodeContext();
		treeNodeContent.getPopupMenu().addSeparator();
		treeNodeContent.getPopupMenu().addAction(actionFactory.createActivateViewAction(context, DEFAULT_LABEL));
		treeNodeContent.getPopupMenu().addAction(actionFactory.createUnHideViewAction(context, DEFAULT_LABEL));
		context.getToolBarMenu().addSeparator();
		context.getToolBarMenu().addAction(actionFactory.createHideViewAction(context));

		createContent(context.getContainer());
	}

	private void createContent(final IContainer container) {

		container.setLayout(MigLayoutFactory.growingInnerCellLayout());
		final IBluePrintFactory bpf = Toolkit.getBluePrintFactory();

		final int rowCount = 20000;
		final int columnCount = 50;

		final IDefaultTableColumnModel columnModel = new DefaultTableColumnModel(columnCount);
		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			final IDefaultTableColumnBuilder columnBuilder = new DefaultTableColumnBuilder();
			columnBuilder.setText("Column " + columnIndex);
			columnBuilder.setToolTipText("Tooltip of column " + columnIndex);
			columnBuilder.setWidth(100);
			columnModel.setColumn(columnIndex, columnBuilder);
		}

		final ITableDataModel dataModel = new AbstractTableDataModel() {

			@Override
			public int getRowCount() {
				return rowCount;
			}

			@Override
			public ITableCell getCell(final int rowIndex, final int columnIndex) {
				final ITableCellBuilder cellBuilder = new TableCellBuilder();
				cellBuilder.setEditable(true);
				cellBuilder.setText("Cell (" + rowIndex + " / " + columnIndex + ")");
				if (rowIndex % 2 == 0) {
					cellBuilder.setBackgroundColor(Colors.DEFAULT_TABLE_EVEN_BACKGROUND_COLOR);
				}
				return cellBuilder.build();
			}
		};

		final TextFieldCellEditorFactory editor = new TextFieldCellEditorFactory();
		final ITableBluePrint tableBp = bpf.table(columnModel, dataModel).setEditor(editor).setEditable(true);
		final ITable table = container.add(tableBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);

		table.addTableSelectionListener(new ITableSelectionListener() {
			@Override
			public void selectionChanged() {
				//CHECKSTYLE:OFF
				System.out.println("New selection: " + table.getSelection());
				//CHECKSTYLE:ON
			}
		});

		table.addTableCellListener(new ITableCellListener() {

			@Override
			public void mouseReleased(final ITableCellMouseEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("mouseReleased: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void mousePressed(final ITableCellMouseEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("mousePressed: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void mouseDoubleClicked(final ITableCellMouseEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("mouseDoubleClicked: " + event);
				//CHECKSTYLE:ON
			}
		});

		table.addTableColumnListener(new ITableColumnListener() {

			@Override
			public void mouseClicked(final ITableColumnMouseEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("mouseClicked: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void columnResized(final ITableColumnResizeEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("columnResized: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void columnPermutationChanged() {
				//CHECKSTYLE:OFF
				System.out.println("columnPermutationChanged: " + table.getColumnPermutation());
				//CHECKSTYLE:ON
			}
		});

		editor.addTableCellEditorListener(new ITableCellEditorListener() {

			@Override
			public void onEdit(final IVetoable veto, final ITableCellEditEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("onEdit: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void editFinished(final ITableCellEditEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("editFinished: " + event);
				//CHECKSTYLE:ON
			}

			@Override
			public void editCanceled(final ITableCellEvent event) {
				//CHECKSTYLE:OFF
				System.out.println("editCanceled: " + event);
				//CHECKSTYLE:ON
			}
		});

		final ValueHolder<Integer> selectedColumn = new ValueHolder<Integer>();
		final ValueHolder<Integer> selectedRow = new ValueHolder<Integer>();

		final IPopupMenu popupMenu = table.createPopupMenu();
		final IMenuModel popupMenuModel = popupMenu.getModel();
		final IActionItemModel item1 = popupMenuModel.addActionItem();
		final IActionItemModel reloadAction = popupMenuModel.addActionItem("Reload", SilkIcons.ARROW_REFRESH_SMALL);
		final IActionItemModel packTableAction = popupMenuModel.addActionItem("Fit all columns", SilkIcons.ARROW_INOUT);
		final IActionItemModel packColumnAction = popupMenuModel.addActionItem("Fit column", SilkIcons.ARROW_INOUT);

		reloadAction.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				table.setCursor(Cursor.WAIT);
				final ArrayList<Integer> permutation = table.getColumnPermutation();
				table.resetFromModel();
				table.setColumnPermutation(permutation);
				table.setCursor(Cursor.DEFAULT);
			}
		});

		packTableAction.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				table.setCursor(Cursor.WAIT);
				table.pack();
				table.setCursor(Cursor.DEFAULT);
			}
		});

		packColumnAction.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				table.setCursor(Cursor.WAIT);
				table.pack(selectedColumn.get().intValue());
				table.setCursor(Cursor.DEFAULT);
			}
		});

		table.addTableCellPopupDetectionListener(new ITableCellPopupDetectionListener() {
			@Override
			public void popupDetected(final ITableCellPopupEvent event) {
				selectedColumn.set(Integer.valueOf(event.getColumnIndex()));
				selectedRow.set(Integer.valueOf(event.getRowIndex()));
				item1.setText("Item1 (" + event.getRowIndex() + " / " + event.getColumnIndex() + ")");
				popupMenu.show(event.getPosition());
			}
		});

		table.addTableColumnPopupDetectionListener(new ITableColumnPopupDetectionListener() {
			@Override
			public void popupDetected(final ITableColumnPopupEvent event) {
				selectedColumn.set(Integer.valueOf(event.getColumnIndex()));
				item1.setText("Item1 (" + event.getColumnIndex() + ")");
				popupMenu.show(event.getPosition());
			}
		});
	}

}
