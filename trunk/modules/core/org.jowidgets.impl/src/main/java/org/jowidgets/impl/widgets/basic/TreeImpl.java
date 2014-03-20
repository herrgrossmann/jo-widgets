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

package org.jowidgets.impl.widgets.basic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jowidgets.api.controller.IDisposeListener;
import org.jowidgets.api.controller.IParentListener;
import org.jowidgets.api.controller.ITreeListener;
import org.jowidgets.api.controller.ITreePopupDetectionListener;
import org.jowidgets.api.controller.ITreeSelectionListener;
import org.jowidgets.api.dnd.ITreeDropLocation.TreeDropPosition;
import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.api.widgets.IPopupMenu;
import org.jowidgets.api.widgets.ITree;
import org.jowidgets.api.widgets.ITreeContainer;
import org.jowidgets.api.widgets.ITreeNode;
import org.jowidgets.api.widgets.descriptor.ITreeDescriptor;
import org.jowidgets.api.widgets.descriptor.ITreeNodeDescriptor;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.impl.base.delegate.ControlDelegate;
import org.jowidgets.impl.base.delegate.TreeContainerDelegate;
import org.jowidgets.impl.dnd.IDropSelectionProvider;
import org.jowidgets.impl.dnd.TreeDropLocationImpl;
import org.jowidgets.impl.event.TreePopupEvent;
import org.jowidgets.impl.event.TreeSelectionEvent;
import org.jowidgets.impl.widgets.common.wrapper.AbstractControlSpiWrapper;
import org.jowidgets.spi.dnd.ITreeDropLocationSpi;
import org.jowidgets.spi.dnd.ITreeDropLocationSpi.TreeDropPositionSpi;
import org.jowidgets.spi.widgets.ITreeNodeSpi;
import org.jowidgets.spi.widgets.ITreeSpi;
import org.jowidgets.spi.widgets.controller.ITreeSelectionListenerSpi;
import org.jowidgets.tools.controller.TreeObservable;
import org.jowidgets.tools.controller.TreePopupDetectionObservable;
import org.jowidgets.tools.controller.TreeSelectionObservable;
import org.jowidgets.tools.widgets.invoker.ColorSettingsInvoker;
import org.jowidgets.tools.widgets.invoker.VisibiliySettingsInvoker;
import org.jowidgets.util.Assert;
import org.jowidgets.util.EmptyCheck;

public class TreeImpl extends AbstractControlSpiWrapper implements ITree, IDropSelectionProvider {

	private final ControlDelegate controlDelegate;
	private final TreeSelectionObservable treeSelectionObservable;
	private final TreeObservable treeObservable;
	private final TreePopupDetectionObservable treePopupDetectionObservable;
	private final TreeContainerDelegate treeContainerDelegate;
	private final ITreeSelectionListenerSpi treeSelectionListenerSpi;
	private final Map<ITreeNodeSpi, ITreeNode> nodes;

	private final IImageConstant defaultInnerIcon;
	private final IImageConstant defaultLeafIcon;

	private List<ITreeNodeSpi> lastSelectionSpi;
	private List<ITreeNode> selection;

	public TreeImpl(final ITreeSpi widgetSpi, final ITreeDescriptor descriptor) {
		super(widgetSpi);

		this.defaultInnerIcon = descriptor.getDefaultInnerIcon();
		this.defaultLeafIcon = descriptor.getDefaultLeafIcon();

		this.controlDelegate = new ControlDelegate(widgetSpi, this);

		this.treeSelectionObservable = new TreeSelectionObservable();
		this.treeObservable = new TreeObservable();
		this.treePopupDetectionObservable = new TreePopupDetectionObservable();

		this.nodes = new HashMap<ITreeNodeSpi, ITreeNode>();
		this.lastSelectionSpi = new LinkedList<ITreeNodeSpi>();
		this.selection = Collections.emptyList();

		VisibiliySettingsInvoker.setVisibility(descriptor, this);
		ColorSettingsInvoker.setColors(descriptor, this);

		this.treeSelectionListenerSpi = new TreeSelectionListenerSpi();
		getWidget().addTreeSelectionListener(treeSelectionListenerSpi);

		addPopupDetectionListener(new IPopupDetectionListener() {
			@Override
			public void popupDetected(final Position position) {
				treePopupDetectionObservable.firePopupDetected(new TreePopupEvent(position, null));
			}
		});

		this.treeContainerDelegate = new TreeContainerDelegate(this, null, null, widgetSpi.getRootNode());
	}

	@Override
	public ITreeSpi getWidget() {
		return (ITreeSpi) super.getWidget();
	}

	@Override
	public IContainer getParent() {
		return controlDelegate.getParent();
	}

	@Override
	public void setParent(final IContainer parent) {
		controlDelegate.setParent(parent);
	}

	@Override
	public void addParentListener(final IParentListener<IContainer> listener) {
		controlDelegate.addParentListener(listener);
	}

	@Override
	public void removeParentListener(final IParentListener<IContainer> listener) {
		controlDelegate.removeParentListener(listener);
	}

	@Override
	public boolean isReparentable() {
		return controlDelegate.isReparentable();
	}

	@Override
	public Object getDropSelection(final Object spiSelection) {
		if (spiSelection instanceof ITreeDropLocationSpi) {
			final ITreeDropLocationSpi dropLoacation = (ITreeDropLocationSpi) spiSelection;
			return new TreeDropLocationImpl(
				nodes.get(dropLoacation.getTreeNode()),
				getDropPosition(dropLoacation.getDropPosition()));
		}
		return null;
	}

	private TreeDropPosition getDropPosition(final TreeDropPositionSpi dropPositionSpi) {
		if (TreeDropPositionSpi.ON.equals(dropPositionSpi)) {
			return TreeDropPosition.ON;
		}
		else if (TreeDropPositionSpi.BEFORE.equals(dropPositionSpi)) {
			return TreeDropPosition.BEFORE;
		}
		else if (TreeDropPositionSpi.AFTER.equals(dropPositionSpi)) {
			return TreeDropPosition.AFTER;
		}
		else {
			return null;
		}
	}

	@Override
	public void addDisposeListener(final IDisposeListener listener) {
		controlDelegate.addDisposeListener(listener);
	}

	@Override
	public void removeDisposeListener(final IDisposeListener listener) {
		controlDelegate.removeDisposeListener(listener);
	}

	@Override
	public boolean isDisposed() {
		return controlDelegate.isDisposed();
	}

	@Override
	public void dispose() {
		if (!isDisposed()) {
			treeContainerDelegate.dispose();
			controlDelegate.dispose();
		}
	}

	@Override
	public IPopupMenu createPopupMenu() {
		return controlDelegate.createPopupMenu();
	}

	@Override
	public Collection<ITreeNode> getSelection() {
		return selection;
	}

	@Override
	public void setSelection(Collection<? extends ITreeNode> newSelection) {
		if (newSelection == null) {
			newSelection = Collections.emptyList();
		}
		//first check if the nodes are part of the tree
		for (final ITreeNode node : newSelection) {
			if (node.getTree() != this) {
				throw new IllegalArgumentException("The node '" + node + "' is not assigned to this tree");
			}
		}

		getWidget().removeTreeSelectionListener(treeSelectionListenerSpi);

		try {
			for (final ITreeNode node : selection) {
				node.setSelected(false);
			}
			for (final ITreeNode node : newSelection) {
				node.setSelected(true);
			}
		}
		catch (final RuntimeException exeption) {
			getWidget().addTreeSelectionListener(treeSelectionListenerSpi);
			throw exeption;
		}

		getWidget().addTreeSelectionListener(treeSelectionListenerSpi);

		afterSelectionChanged();
	}

	@Override
	public void setSelection(final ITreeNode... selection) {
		if (!EmptyCheck.isEmpty(selection)) {
			setSelection(Arrays.asList(selection));
		}
		else {
			clearSelection();
		}
	}

	@Override
	public void clearSelection() {
		final List<? extends ITreeNode> emptyList = Collections.emptyList();
		setSelection(emptyList);
	}

	@Override
	public void addTreePopupDetectionListener(final ITreePopupDetectionListener listener) {
		treePopupDetectionObservable.addTreePopupDetectionListener(listener);
	}

	@Override
	public void removeTreePopupDetectionListener(final ITreePopupDetectionListener listener) {
		treePopupDetectionObservable.removeTreePopupDetectionListener(listener);
	}

	@Override
	public void addTreeSelectionListener(final ITreeSelectionListener listener) {
		treeSelectionObservable.addTreeSelectionListener(listener);
	}

	@Override
	public void removeTreeSelectionListener(final ITreeSelectionListener listener) {
		treeSelectionObservable.removeTreeSelectionListener(listener);
	}

	@Override
	public void addTreeListener(final ITreeListener listener) {
		treeObservable.addTreeListener(listener);
	}

	@Override
	public void removeTreeListener(final ITreeListener listener) {
		treeObservable.removeTreeListener(listener);
	}

	@Override
	public ITreeContainer getParentContainer() {
		return treeContainerDelegate.getParentContainer();
	}

	@Override
	public ITreeNode addNode() {
		return treeContainerDelegate.addNode();
	}

	@Override
	public ITreeNode addNode(final int index) {
		return treeContainerDelegate.addNode(index);
	}

	@Override
	public ITreeNode addNode(final ITreeNodeDescriptor descriptor) {
		return treeContainerDelegate.addNode(descriptor);
	}

	@Override
	public ITreeNode addNode(final int index, final ITreeNodeDescriptor descriptor) {
		return treeContainerDelegate.addNode(index, descriptor);
	}

	@Override
	public void removeNode(final ITreeNode node) {
		treeContainerDelegate.removeNode(node);
	}

	@Override
	public void removeNode(final int index) {
		treeContainerDelegate.removeNode(index);
	}

	@Override
	public void removeAllNodes() {
		treeContainerDelegate.removeAllNodes();
	}

	@Override
	public List<ITreeNode> getChildren() {
		return treeContainerDelegate.getChildren();
	}

	@Override
	public void setAllChildrenExpanded(final boolean expanded) {
		treeContainerDelegate.setAllChildrenExpanded(expanded);
	}

	@Override
	public int getLevel() {
		return treeContainerDelegate.getLevel();
	}

	@Override
	public ITreeNode getNodeAt(final Position position) {
		Assert.paramNotNull(position, "position");
		final ITreeNodeSpi treeNodeSpi = getWidget().getNodeAt(position);
		if (treeNodeSpi != null) {
			return nodes.get(treeNodeSpi);
		}
		else {
			return null;
		}
	}

	public void registerNode(final TreeNodeImpl node) {
		nodes.put(node.getWidget(), node);
	}

	public void unRegisterNode(final TreeNodeImpl node) {
		nodes.remove(node.getWidget());
	}

	public IImageConstant getDefaultInnerIcon() {
		return defaultInnerIcon;
	}

	public IImageConstant getDefaultLeafIcon() {
		return defaultLeafIcon;
	}

	protected TreeObservable getTreeObservable() {
		return treeObservable;
	}

	protected TreePopupDetectionObservable getTreePopupDetectionObservable() {
		return treePopupDetectionObservable;
	}

	private void afterSelectionChanged() {
		final List<ITreeNode> selected = new LinkedList<ITreeNode>();
		final List<ITreeNode> unselected = new LinkedList<ITreeNode>();

		final List<ITreeNodeSpi> newSelection = getWidget().getSelectedNodes();

		for (final ITreeNodeSpi wasSelected : lastSelectionSpi) {
			if (!newSelection.contains(wasSelected)) {
				final ITreeNode unselectedNode = nodes.get(wasSelected);
				if (unselectedNode != null) {
					unselected.add(unselectedNode);
				}
			}
		}

		for (final ITreeNodeSpi isSelected : newSelection) {
			final ITreeNode selectedNode = nodes.get(isSelected);
			if (selectedNode != null) {
				selected.add(selectedNode);
			}
		}

		selection = Collections.unmodifiableList(selected);
		treeSelectionObservable.fireSelectionChanged(new TreeSelectionEvent(selection, Collections.unmodifiableList(unselected)));
		lastSelectionSpi = newSelection;

	}

	private final class TreeSelectionListenerSpi implements ITreeSelectionListenerSpi {
		@Override
		public void selectionChanged() {
			afterSelectionChanged();
		}
	}
}
