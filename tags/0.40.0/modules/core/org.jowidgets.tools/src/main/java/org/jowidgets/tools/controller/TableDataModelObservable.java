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

package org.jowidgets.tools.controller;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jowidgets.common.model.ITableDataModelListener;
import org.jowidgets.common.model.ITableDataModelObservable;
import org.jowidgets.util.Assert;

public class TableDataModelObservable implements ITableDataModelObservable {

	private final Set<ITableDataModelListener> listeners;

	public TableDataModelObservable() {
		this.listeners = new LinkedHashSet<ITableDataModelListener>();
	}

	@Override
	public void addDataModelListener(final ITableDataModelListener listener) {
		Assert.paramNotNull(listener, "listener");
		listeners.add(listener);
	}

	@Override
	public void removeDataModelListener(final ITableDataModelListener listener) {
		Assert.paramNotNull(listener, "listener");
		listeners.remove(listener);
	}

	public void fireRowsAdded(final int[] rowIndices) {
		for (final ITableDataModelListener listener : new LinkedList<ITableDataModelListener>(listeners)) {
			listener.rowsAdded(rowIndices);
		}
	}

	public void fireRowsRemoved(final int[] rowIndices) {
		for (final ITableDataModelListener listener : new LinkedList<ITableDataModelListener>(listeners)) {
			listener.rowsRemoved(rowIndices);
		}
	}

	public void fireRowsChanged(final int[] rowIndices) {
		for (final ITableDataModelListener listener : new LinkedList<ITableDataModelListener>(listeners)) {
			listener.rowsChanged(rowIndices);
		}
	}

	public void fireDataChanged() {
		for (final ITableDataModelListener listener : new LinkedList<ITableDataModelListener>(listeners)) {
			listener.dataChanged();
		}
	}

	public void fireSelectionChanged() {
		for (final ITableDataModelListener listener : new LinkedList<ITableDataModelListener>(listeners)) {
			listener.selectionChanged();
		}
	}

}
