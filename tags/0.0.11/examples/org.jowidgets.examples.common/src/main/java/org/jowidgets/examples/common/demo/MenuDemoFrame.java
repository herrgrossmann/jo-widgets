/*
 * Copyright (c) 2010, grossmann
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

package org.jowidgets.examples.common.demo;

import org.jowidgets.api.color.Colors;
import org.jowidgets.api.command.EnabledState;
import org.jowidgets.api.command.IAction;
import org.jowidgets.api.command.IActionBuilder;
import org.jowidgets.api.command.IActionBuilderFactory;
import org.jowidgets.api.command.ICommandAction;
import org.jowidgets.api.command.ICommandExecutor;
import org.jowidgets.api.command.IExecutionContext;
import org.jowidgets.api.image.IconsSmall;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IActionMenuItem;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IInputControl;
import org.jowidgets.api.widgets.IMenu;
import org.jowidgets.api.widgets.IMenuBar;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.ISelectableMenuItem;
import org.jowidgets.api.widgets.ISubMenu;
import org.jowidgets.api.widgets.IToolBar;
import org.jowidgets.api.widgets.IToolBarButton;
import org.jowidgets.api.widgets.IToolBarContainerItem;
import org.jowidgets.api.widgets.IToolBarPopupButton;
import org.jowidgets.api.widgets.IToolBarToggleButton;
import org.jowidgets.api.widgets.blueprint.IActionMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.ICheckedMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.IRadioMenuItemBluePrint;
import org.jowidgets.api.widgets.blueprint.IToolBarToggleButtonBluePrint;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.common.types.Accelerator;
import org.jowidgets.common.types.Modifier;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controler.IActionListener;
import org.jowidgets.common.widgets.controler.IItemStateListener;
import org.jowidgets.common.widgets.controler.IPopupDetectionListener;
import org.jowidgets.common.widgets.layout.MigLayoutDescriptor;
import org.jowidgets.tools.command.EnabledChecker;
import org.jowidgets.tools.powo.JoFrame;

public class MenuDemoFrame extends JoFrame {

	private static final IBluePrintFactory BPF = Toolkit.getBluePrintFactory();

	private ICommandAction action1;
	private ICommandAction action2;

	public MenuDemoFrame() {
		super(bluePrint("Menu demo").autoPackOff());
		createActions();
		createMainMenus();

		setLayout(new MigLayoutDescriptor("0[grow]0", "0[][grow]0"));

		final IToolBar toolBar = add(BPF.toolBar(), "wrap");

		final IToolBarPopupButton toolBarPopupButton = toolBar.addItem(BPF.toolBarPopupButton());
		toolBarPopupButton.setAction(action1);
		final IPopupMenu toolBarPopupMenu = toolBar.createPopupMenu();
		addMenus(toolBarPopupMenu);
		toolBarPopupButton.addPopupDetectionListener(new IPopupDetectionListener() {
			@Override
			public void popupDetected(final Position position) {
				toolBarPopupMenu.show(position);
			}
		});

		final IToolBarButton toolBarButton = toolBar.addItem(BPF.toolBarButton());
		toolBarButton.setAction(action2);

		toolBar.addSeparator();

		final IToolBarToggleButtonBluePrint toggleButtonBp = BPF.toolBarToggleButton().setText("ToggleButton");
		toggleButtonBp.setToolTipText("Tooltip");
		final IToolBarToggleButton toggleButton = toolBar.addItem(toggleButtonBp);
		toggleButton.addItemListener(new IItemStateListener() {

			@Override
			public void itemStateChanged() {
				System.out.println("ToggleButton selected: " + toggleButton.isSelected());
			}
		});

		final IToolBarContainerItem toolBarContainer = toolBar.addItem(BPF.toolBarContainerItem());
		final IInputControl<String> textField = toolBarContainer.add(BPF.textField(), "w 200");
		textField.setValue("Test");

		toolBar.addItem(BPF.toolBarButton().setText("Button").setToolTipText("Tooltip"));

		toolBar.pack();

		final IComposite composite = add(BPF.composite().setBackgroundColor(Colors.WHITE), "growx, growy");
		final IPopupMenu popupMenu = composite.createPopupMenu();
		addMenus(popupMenu);
		composite.addPopupDetectionListener(new IPopupDetectionListener() {

			@Override
			public void popupDetected(final Position position) {
				popupMenu.show(position);
			}
		});
	}

	private void createActions() {
		final IActionBuilderFactory actionBF = Toolkit.getActionBuilderFactory();

		final IActionBuilder action1Builder = actionBF.create();
		action1Builder.setText("Action1").setToolTipText("The tooltip of Action1").setIcon(IconsSmall.INFO);
		action1Builder.setMnemonic('c').setAccelerator(new Accelerator('A', Modifier.CTRL));
		action1 = action1Builder.build();

		final EnabledChecker enabledChecker1 = new EnabledChecker();
		final EnabledChecker enabledChecker2 = new EnabledChecker();
		enabledChecker2.setEnabledState(EnabledState.disabled("Action 1 must be invoked first"));

		final ICommandExecutor command1 = new ICommandExecutor() {
			@Override
			public void execute(final IExecutionContext event) {
				final IAction action = event.getAction();
				Toolkit.getMessagePane().showInfo(action.getText(), action.getIcon(), "Hello action1");
				enabledChecker1.setEnabledState(EnabledState.disabled("Action 2 must be invoked first"));
				enabledChecker2.setEnabledState(EnabledState.ENABLED);
			}
		};

		action1.setCommand(command1, enabledChecker1);

		final IActionBuilder action2Builder = actionBF.create();
		action2Builder.setText("Action2").setToolTipText("The tooltip of Action2");
		action2Builder.setMnemonic('t').setAccelerator(new Accelerator('A', Modifier.ALT));
		action2 = action2Builder.build();

		final ICommandExecutor command2 = new ICommandExecutor() {
			@Override
			public void execute(final IExecutionContext event) {
				final IAction action = event.getAction();
				Toolkit.getMessagePane().showInfo(action.getText(), action.getIcon(), "Hello action2");
				enabledChecker1.setEnabledState(EnabledState.ENABLED);
				enabledChecker2.setEnabledState(EnabledState.disabled("Action 1 must be invoked first"));
			}
		};

		action2.setCommand(command2, enabledChecker2);
	}

	private void createMainMenus() {

		final IMenuBar menuBar = createMenuBar();
		final IMenu menu1 = menuBar.addMenu("Menu1", 'n');
		addMenus(menu1);

		final IPopupMenu popupMenu = createPopupMenu();
		addMenus(popupMenu);
		addPopupDetectionListener(new IPopupDetectionListener() {

			@Override
			public void popupDetected(final Position position) {
				popupMenu.show(position);
			}
		});
	}

	private void addMenus(final IMenu menu) {
		final ISubMenu subMenu = menu.addItem(BPF.subMenu("sub menu 1").setMnemonic('e'));
		subMenu.addItem(BPF.menuItem("sub item1"));
		subMenu.addItem(BPF.menuItem("sub item2"));

		final ISubMenu subMenu2 = subMenu.addItem(BPF.subMenu("sub menu 2"));
		subMenu2.addItem(BPF.menuItem("sub item1"));
		subMenu2.addItem(BPF.menuItem("sub item2"));
		subMenu2.addItem(BPF.menuItem("sub item3"));
		subMenu2.addItem(BPF.menuItem("sub item4"));

		subMenu.addItem(BPF.menuItem("sub item3"));
		subMenu.addSeparator();
		subMenu.addItem(BPF.menuItem("sub item4"));
		subMenu.addItem(BPF.menuItem("sub item5"));

		final IActionMenuItemBluePrint item1Bp = BPF.menuItem();
		final IActionMenuItem item1 = menu.addItem(item1Bp);
		item1.setAction(action1);

		final IActionMenuItemBluePrint item2Bp = BPF.menuItem();
		final IActionMenuItem item2 = menu.addItem(item2Bp);
		item2.setAction(action2);

		final IActionMenuItemBluePrint item3Bp = BPF.menuItem().setText("The Third Item");
		item3Bp.setToolTipText("This is the third item");
		item3Bp.setIcon(IconsSmall.WARNING).setAccelerator(new Accelerator('I', Modifier.SHIFT)).setMnemonic('e');
		final IActionMenuItem item3 = menu.addItem(1, item3Bp);

		menu.addSeparator();

		final ICheckedMenuItemBluePrint item4Bp = BPF.checkedMenuItem().setText("item4");
		final ISelectableMenuItem item4 = menu.addItem(item4Bp);

		menu.addSeparator();

		final IRadioMenuItemBluePrint item5Bp = BPF.radioMenuItem().setText("item5");
		final ISelectableMenuItem item5 = menu.addItem(item5Bp);

		final IRadioMenuItemBluePrint item6Bp = BPF.radioMenuItem().setText("item6");
		final ISelectableMenuItem item6 = menu.addItem(item6Bp);
		item6.setSelected(true);

		final IRadioMenuItemBluePrint item7Bp = BPF.radioMenuItem().setText("item7");
		final ISelectableMenuItem item7 = menu.addItem(item7Bp);

		item3.addActionListener(new IActionListener() {
			@Override
			public void actionPerformed() {
				System.out.println("Item3");
			}
		});

		item4.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				System.out.println("Item4, selected=" + item4.isSelected());
			}
		});

		item5.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				System.out.println("Item5, selected=" + item5.isSelected());
			}
		});

		item6.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				System.out.println("Item6, selected=" + item6.isSelected());
			}
		});

		item7.addItemListener(new IItemStateListener() {
			@Override
			public void itemStateChanged() {
				System.out.println("Item7, selected=" + item7.isSelected());
			}
		});

	}
}
