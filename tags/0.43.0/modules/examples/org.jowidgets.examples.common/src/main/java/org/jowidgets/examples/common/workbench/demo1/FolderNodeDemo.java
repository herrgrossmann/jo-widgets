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

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.examples.common.workbench.base.AbstractDemoComponentNode;
import org.jowidgets.workbench.api.IComponent;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IComponentNode;
import org.jowidgets.workbench.api.IComponentNodeContext;

public class FolderNodeDemo extends AbstractDemoComponentNode {

    private final List<IComponentNode> children;

    public FolderNodeDemo(final String id, final String label) {
        this(id, label, null, SilkIcons.FOLDER, new LinkedList<IComponentNode>());
    }

    public FolderNodeDemo(final String id, final String label, final List<IComponentNode> children) {
        this(id, label, null, SilkIcons.FOLDER, children);
    }

    public FolderNodeDemo(
        final String id,
        final String label,
        final String tooltip,
        final IImageConstant icon,
        final List<IComponentNode> children) {

        super(id, label, tooltip, icon);

        this.children = children;
    }

    @Override
    public void onContextInitialize(final IComponentNodeContext context) {
        //create menus
        final ActionFactory actionFactory = new ActionFactory();
        final IMenuModel popupMenu = context.getPopupMenu();
        popupMenu.addAction(actionFactory.createAddFolderAction(context));
        popupMenu.addAction(actionFactory.createAddComponentAction(context));
        popupMenu.addAction(actionFactory.createDeleteAction(context, this, "Delete " + getLabel(), SilkIcons.FOLDER_DELETE));
        popupMenu.addSeparator();
        popupMenu.addAction(actionFactory.createRenameComponentTreeNode(context));
        popupMenu.addAction(actionFactory.createSelectParentNode(context));
        popupMenu.addSeparator();
        popupMenu.addAction(actionFactory.createExpandNode(context));
        popupMenu.addAction(actionFactory.createCollapseNode(context));

        //add children
        for (final IComponentNode componentTreeNode : children) {
            context.add(componentTreeNode);
        }
    }

    @Override
    public IComponent createComponent(final IComponentContext context) {
        return null;
    }

}
