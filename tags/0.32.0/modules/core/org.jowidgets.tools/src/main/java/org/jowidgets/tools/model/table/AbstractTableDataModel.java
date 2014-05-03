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

package org.jowidgets.tools.model.table;

import java.util.ArrayList;
import java.util.Collection;

import org.jowidgets.common.model.ITableDataModel;
import org.jowidgets.common.model.ITableDataModelListener;
import org.jowidgets.common.model.ITableDataModelObservable;
import org.jowidgets.tools.controller.TableDataModelObservable;
import org.jowidgets.util.CollectionUtils;

public abstract class AbstractTableDataModel implements ITableDataModel, ITableDataModelObservable {

	private final TableDataModelObservable dataModelObservable;

	private ArrayList<Integer> selection;

	public AbstractTableDataModel() {
		this.dataModelObservable = new TableDataModelObservable();
		this.selection = new ArrayList<Integer>();
	}

	@Override
	public final ArrayList<Integer> getSelection() {
		return CollectionUtils.unmodifiableArrayList(selection);
	}

	@Override
	public final void setSelection(Collection<Integer> selection) {
		if (selection == null) {
			selection = new ArrayList<Integer>();
		}
		if (!this.selection.equals(selection)) {
			this.selection = new ArrayList<Integer>(selection);
			fireSelectionChanged();
		}
	}

	@Override
	public final ITableDataModelObservable getTableDataModelObservable() {
		return this;
	}

	@Override
	public final void addDataModelListener(final ITableDataModelListener listener) {
		dataModelObservable.addDataModelListener(listener);
	}

	@Override
	public final void removeDataModelListener(final ITableDataModelListener listener) {
		dataModelObservable.removeDataModelListener(listener);
	}

	public final void fireRowsAdded(final int[] rowIndices) {
		dataModelObservable.fireRowsAdded(rowIndices);
	}

	public final void fireRowsRemoved(final int[] rowIndices) {
		dataModelObservable.fireRowsRemoved(rowIndices);
	}

	public final void fireRowsChanged(final int[] rowIndices) {
		dataModelObservable.fireRowsChanged(rowIndices);
	}

	public final void fireDataChanged() {
		dataModelObservable.fireDataChanged();
	}

	private void fireSelectionChanged() {
		dataModelObservable.fireSelectionChanged();
	}

}
