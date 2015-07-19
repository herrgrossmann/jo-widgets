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

import java.util.UUID;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.command.EnabledState;
import org.jowidgets.api.command.IAction;
import org.jowidgets.api.command.IActionBuilder;
import org.jowidgets.api.command.ICommandExecutor;
import org.jowidgets.api.command.IExecutionContext;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IInputDialog;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.tools.command.EnabledChecker;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IComponentNode;
import org.jowidgets.workbench.api.IComponentNodeContext;
import org.jowidgets.workbench.api.IFolderContext;
import org.jowidgets.workbench.api.ILayout;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.api.IWorkbenchApplicationContext;
import org.jowidgets.workbench.toolkit.api.IViewLayoutBuilder;
import org.jowidgets.workbench.tools.ViewLayoutBuilder;

public class ActionFactory {

    private final InputDialogFactory inputDialogFactory = new InputDialogFactory();

    public IAction createAddFolderAction(final IWorkbenchApplicationContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Add new Folder");
        actionBuilder.setIcon(SilkIcons.FOLDER_ADD);
        actionBuilder.setCommand(new ICommandExecutor() {

            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IInputDialog<String> inputDialog = createInputDialog(executionContext, "Folder name");
                inputDialog.setVisible(true);
                if (inputDialog.isOkPressed()) {
                    final IComponentNode componentNode = new FolderNodeDemo(UUID.randomUUID().toString(), inputDialog.getValue());
                    context.add(componentNode);
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createAddComponentAction(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Add new Component");
        actionBuilder.setIcon(SilkIcons.APPLICATION_ADD);
        actionBuilder.setCommand(new ICommandExecutor() {

            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IInputDialog<String> inputDialog = createInputDialog(executionContext, "Component name");
                inputDialog.setVisible(true);
                if (inputDialog.isOkPressed()) {
                    final IComponentNode componentNode = new ComponentNodeDemo1(
                        UUID.randomUUID().toString(),
                        inputDialog.getValue());
                    context.add(componentNode);
                    context.setExpanded(true);
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createAddFolderAction(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Add new Folder");
        actionBuilder.setIcon(SilkIcons.FOLDER_ADD);
        actionBuilder.setCommand(new ICommandExecutor() {

            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IInputDialog<String> inputDialog = createInputDialog(executionContext, "Folder name");
                inputDialog.setVisible(true);
                if (inputDialog.isOkPressed()) {
                    final IComponentNode componentNode = new FolderNodeDemo(UUID.randomUUID().toString(), inputDialog.getValue());

                    final IComponentNodeContext parentTreeNode = context.getParent();
                    if (parentTreeNode == null) {
                        context.getWorkbenchApplicationContext().add(componentNode);
                    }
                    else {
                        parentTreeNode.add(componentNode);
                    }
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createDeleteAction(
        final IComponentNodeContext context,
        final IComponentNode componentNode,
        final String text,
        final IImageConstant icon) {

        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText(text);
        actionBuilder.setIcon(icon);
        actionBuilder.setCommand(new ICommandExecutor() {

            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IComponentNodeContext parentTreeNode = context.getParent();
                if (parentTreeNode == null) {
                    context.getWorkbenchApplicationContext().remove(componentNode);
                }
                else {
                    parentTreeNode.remove(componentNode);
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createAddViewAction(final IFolderContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Add new View");
        actionBuilder.setIcon(SilkIcons.ADD);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IInputDialog<String> inputDialog = createInputDialog(executionContext, "View name");
                inputDialog.setVisible(true);
                if (inputDialog.isOkPressed()) {
                    final IViewLayoutBuilder viewLayoutBuilder = new ViewLayoutBuilder();
                    viewLayoutBuilder.setId(DynamicViewDemo.ID_PREFIX + UUID.randomUUID());
                    viewLayoutBuilder.setLabel(inputDialog.getValue());
                    context.addView(viewLayoutBuilder.build());
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createRemoveViewAction(final IViewContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Remove this View");
        actionBuilder.setIcon(SilkIcons.DELETE);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.dispose();
            }
        });
        return actionBuilder.build();
    }

    public IAction createActivateViewAction(final IViewContext context, final String viewLabel) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Activate " + viewLabel);
        actionBuilder.setIcon(SilkIcons.ACCEPT);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.activate();
            }
        });
        return actionBuilder.build();
    }

    public IAction createHideViewAction(final IViewContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Hide this View");
        actionBuilder.setIcon(SilkIcons.ARROW_OUT);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.setHidden(true);
            }
        });
        return actionBuilder.build();
    }

    public IAction createUnHideViewAction(final IViewContext context, final String viewLabel) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Unhide " + viewLabel);
        actionBuilder.setIcon(SilkIcons.ARROW_IN);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.setHidden(false);
            }
        });
        return actionBuilder.build();
    }

    public IAction createResetLayoutAction(final IComponentContext context, final ILayout layout) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Reset layout");
        actionBuilder.setIcon(SilkIcons.NEW);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.resetLayout(layout);
            }
        });
        return actionBuilder.build();
    }

    public IAction createRenameComponentTreeNode(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Rename...");
        actionBuilder.setIcon(SilkIcons.BOOK_EDIT);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                final IInputDialog<String> inputDialog = createInputDialog(executionContext, "Component name");
                inputDialog.setVisible(true);
                if (inputDialog.isOkPressed()) {
                    context.setLabel(inputDialog.getValue());
                }
            }

        });

        return actionBuilder.build();
    }

    public IAction createSelectParentNode(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Select parent Component");
        actionBuilder.setIcon(SilkIcons.TABLE_GO);
        final EnabledChecker enabledChecker = new EnabledChecker();
        enabledChecker.setEnabledState(context.getParent() == null
                ? EnabledState.disabled("cannot select parent because this is a root node") : EnabledState.ENABLED);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.getParent().setSelected(true);
            }
        }, enabledChecker);

        return actionBuilder.build();
    }

    public IAction createExpandNode(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Expand");
        actionBuilder.setIcon(SilkIcons.PLUGIN_ADD);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.setExpanded(true);
            }
        });

        return actionBuilder.build();
    }

    public IAction createCollapseNode(final IComponentNodeContext context) {
        final IActionBuilder actionBuilder = Toolkit.getActionBuilderFactory().create();
        actionBuilder.setText("Collapse");
        actionBuilder.setIcon(SilkIcons.PLUGIN_DELETE);
        actionBuilder.setCommand(new ICommandExecutor() {
            @Override
            public void execute(final IExecutionContext executionContext) throws Exception {
                context.setExpanded(false);
            }
        });

        return actionBuilder.build();
    }

    private IInputDialog<String> createInputDialog(final IExecutionContext executionContext, final String inputName) {
        return inputDialogFactory.createInputDialog(executionContext, inputName);
    }
}
