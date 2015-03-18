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

package org.jowidgets.spi.impl.swing.common.widgets;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.types.SelectionPolicy;
import org.jowidgets.spi.impl.controller.TreeSelectionObservableSpi;
import org.jowidgets.spi.impl.swing.common.dnd.IDropSelectionProvider;
import org.jowidgets.spi.impl.swing.common.options.SwingOptions;
import org.jowidgets.spi.impl.swing.common.util.ColorConvert;
import org.jowidgets.spi.impl.swing.common.util.PositionConvert;
import org.jowidgets.spi.impl.swing.common.widgets.base.JoTreeNode;
import org.jowidgets.spi.impl.swing.common.widgets.base.JoTreeNodeRenderer;
import org.jowidgets.spi.widgets.ITreeNodeSpi;
import org.jowidgets.spi.widgets.ITreeSpi;
import org.jowidgets.spi.widgets.controller.ITreeSelectionListenerSpi;
import org.jowidgets.spi.widgets.setup.ITreeSetupSpi;
import org.jowidgets.util.Assert;
import org.jowidgets.util.Tuple;

public class TreeImpl extends SwingControl implements ITreeSpi, IDropSelectionProvider {

    private final Map<JoTreeNode, TreeNodeImpl> nodes;
    private final TreeSelectionObservableSpi treeObservable;

    private final JTree tree;
    private final DefaultMutableTreeNode mutableRootNode;
    private final DefaultTreeModel treeModel;
    private final ITreeNodeSpi rootNode;

    private List<JoTreeNode> lastSelection;

    public TreeImpl(final ITreeSetupSpi setup) {
        super(createComponent(setup));

        if (setup.isChecked()) {
            //CHECKSTYLE:OFF
            System.out.println("Checked Tree is not jet implemented for swing");
            //CHECKSTYLE:ON
        }

        this.nodes = new HashMap<JoTreeNode, TreeNodeImpl>();
        this.treeObservable = new TreeSelectionObservableSpi();
        this.lastSelection = new LinkedList<JoTreeNode>();

        if (getUiReference() instanceof JScrollPane) {
            this.tree = (JTree) ((JScrollPane) getUiReference()).getViewport().getView();
        }
        else {
            this.tree = (JTree) getUiReference();
        }

        this.treeModel = (DefaultTreeModel) tree.getModel();
        this.mutableRootNode = (DefaultMutableTreeNode) treeModel.getRoot();

        tree.setCellRenderer(new JoTreeNodeRenderer());

        tree.addTreeExpansionListener(new TreeExpansionListener() {

            @Override
            public void treeExpanded(final TreeExpansionEvent event) {
                final TreePath path = event.getPath();
                if (path.getLastPathComponent() != mutableRootNode) {
                    final JoTreeNode node = (JoTreeNode) path.getLastPathComponent();
                    final TreeNodeImpl treeNodeImpl = nodes.get(node);
                    if (treeNodeImpl != null) {
                        treeNodeImpl.fireExpandedChanged(true);
                    }
                }
            }

            @Override
            public void treeCollapsed(final TreeExpansionEvent event) {
                final TreePath path = event.getPath();
                if (path.getLastPathComponent() != mutableRootNode) {
                    final JoTreeNode node = (JoTreeNode) path.getLastPathComponent();
                    final TreeNodeImpl treeNodeImpl = nodes.get(node);
                    if (treeNodeImpl != null) {
                        treeNodeImpl.fireExpandedChanged(false);
                    }
                }
            }
        });

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(final TreeSelectionEvent e) {
                fireSelectionChange();
            }
        });

        setMouseListener(new MouseAdapter() {});
        tree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(final MouseEvent e) {
                trigger(e);
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                trigger(e);
            }

            private void trigger(final MouseEvent e) {
                if (e.isPopupTrigger()) {
                    final TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                    //This is necessary when tree is inside a viewport
                    final Point popupPosition = new Point(e.getLocationOnScreen());
                    SwingUtilities.convertPointFromScreen(popupPosition, getUiReference());
                    final Position position = PositionConvert.convert(popupPosition);

                    if (path != null) {
                        final JoTreeNode node = (JoTreeNode) path.getLastPathComponent();
                        if (!e.isShiftDown() && !e.isControlDown()) {
                            tree.setSelectionPath(new TreePath(node.getPath()));
                        }
                        nodes.get(node).firePopupDetected(position);
                    }
                    else {
                        getPopupDetectionObservable().firePopupDetected(position);
                    }
                }
            }

        });

        if (!SwingOptions.isDefaultTreeTransferHandler()) {
            tree.setTransferHandler(null);
        }

        this.rootNode = new TreeNodeImpl(this, mutableRootNode);
    }

    @Override
    public ITreeNodeSpi getRootNode() {
        return rootNode;
    }

    @Override
    public Object getDropSelection(final Object dropLocation) {
        if (dropLocation instanceof JTree.DropLocation) {
            final JTree.DropLocation treeDropLocation = (JTree.DropLocation) dropLocation;
            final Object lastNode = treeDropLocation.getPath().getLastPathComponent();
            if (lastNode instanceof JoTreeNode) {
                return nodes.get(lastNode);
            }
        }
        return null;
    }

    @Override
    public ITreeNodeSpi getNodeAt(final Position position) {
        Assert.paramNotNull(position, "position");
        final TreePath treePath = tree.getPathForLocation(position.getX(), position.getY());
        if (treePath != null) {
            final Object lastNode = treePath.getLastPathComponent();
            if (lastNode instanceof JoTreeNode) {
                return nodes.get(lastNode);
            }
        }
        return null;
    }

    @Override
    public List<ITreeNodeSpi> getSelectedNodes() {
        final List<ITreeNodeSpi> result = new LinkedList<ITreeNodeSpi>();
        final TreePath[] selectionPaths = tree.getSelectionPaths();
        if (selectionPaths != null) {
            for (final TreePath selectionPath : selectionPaths) {
                final Object selectedNode = selectionPath.getLastPathComponent();
                if (selectedNode instanceof JoTreeNode) {
                    result.add(nodes.get(selectedNode));
                }
            }
        }
        return result;
    }

    @Override
    public void setForegroundColor(final IColorConstant colorValue) {
        tree.setForeground(ColorConvert.convert(colorValue));
        super.setForegroundColor(colorValue);
    }

    @Override
    public void setBackgroundColor(final IColorConstant colorValue) {
        tree.setBackground(ColorConvert.convert(colorValue));
        super.setBackgroundColor(colorValue);
    }

    @Override
    public IColorConstant getForegroundColor() {
        return ColorConvert.convert(tree.getForeground());
    }

    @Override
    public IColorConstant getBackgroundColor() {
        return ColorConvert.convert(tree.getBackground());
    }

    @Override
    public void addTreeSelectionListener(final ITreeSelectionListenerSpi listener) {
        treeObservable.addTreeSelectionListener(listener);
    }

    @Override
    public void removeTreeSelectionListener(final ITreeSelectionListenerSpi listener) {
        treeObservable.removeTreeSelectionListener(listener);
    }

    protected void registerNode(final JoTreeNode joTreeNode, final TreeNodeImpl nodeImpl) {
        nodes.put(joTreeNode, nodeImpl);
    }

    protected void unRegisterNode(final JoTreeNode joTreeNode) {
        for (int i = 0; i < joTreeNode.getChildCount(); i++) {
            unRegisterNode((JoTreeNode) joTreeNode.getChildAt(i));
        }
        nodes.remove(joTreeNode);
        lastSelection.remove(joTreeNode);
    }

    protected DefaultMutableTreeNode getMutableRootNode() {
        return mutableRootNode;
    }

    protected JTree getTree() {
        return tree;
    }

    protected DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    private void fireSelectionChange() {

        final List<JoTreeNode> newSelection = new LinkedList<JoTreeNode>();
        final TreePath[] selectionPaths = tree.getSelectionPaths();
        if (selectionPaths != null) {
            for (final TreePath selectionPath : selectionPaths) {
                final Object selectedNode = selectionPath.getLastPathComponent();
                if (selectedNode instanceof JoTreeNode) {
                    newSelection.add((JoTreeNode) selectedNode);
                }
            }
        }

        for (final JoTreeNode wasSelected : lastSelection) {
            if (!newSelection.contains(wasSelected)) {
                final TreeNodeImpl treeNode = nodes.get(wasSelected);
                if (treeNode != null) {
                    treeNode.fireSelectionChanged(false);
                }
            }
        }

        for (final JoTreeNode isSelected : newSelection) {
            if (!lastSelection.contains(isSelected)) {
                final TreeNodeImpl treeNode = nodes.get(isSelected);
                if (treeNode != null) {
                    treeNode.fireSelectionChanged(true);
                }
            }
        }

        lastSelection = newSelection;
        treeObservable.fireSelectionChanged();
    }

    private static Tuple<Component, Component> createComponent(final ITreeSetupSpi setup) {

        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

        final JTree tree = new JTree(rootNode);
        tree.setModel(treeModel);

        rootNode.setAllowsChildren(true);
        tree.setShowsRootHandles(true);
        tree.setDoubleBuffered(true);
        tree.setRootVisible(false);
        tree.setBorder(BorderFactory.createEmptyBorder());

        ToolTipManager.sharedInstance().registerComponent(tree);

        if (SelectionPolicy.MULTI_SELECTION == setup.getSelectionPolicy()) {
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        }
        else if (SelectionPolicy.SINGLE_SELECTION == setup.getSelectionPolicy()) {
            tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        }
        else {
            throw new IllegalArgumentException("SelectionPolicy '" + setup.getSelectionPolicy() + "' is not known");
        }

        if (setup.isContentScrolled()) {
            final JScrollPane result = new JScrollPane(tree);
            result.setBorder(BorderFactory.createEmptyBorder());
            result.setViewportBorder(BorderFactory.createEmptyBorder());
            return new Tuple<Component, Component>(result, tree);
        }
        else {
            return new Tuple<Component, Component>(tree, tree);
        }
    }

}
