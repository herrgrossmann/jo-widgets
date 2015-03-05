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

package org.jowidgets.spi.impl.controller;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jowidgets.common.widgets.controller.IItemStateListener;
import org.jowidgets.common.widgets.controller.IItemStateObservable;
import org.jowidgets.util.Assert;
import org.jowidgets.util.NullCompatibleEquivalence;

public class ItemStateObservable implements IItemStateObservable {

	private final Set<IItemStateListener> itemListeners;

	private Boolean lastState;

	public ItemStateObservable() {
		this.itemListeners = new LinkedHashSet<IItemStateListener>();
	}

	@Override
	public final void addItemListener(final IItemStateListener listener) {
		Assert.paramNotNull(listener, "listener");
		this.itemListeners.add(listener);
	}

	@Override
	public final void removeItemListener(final IItemStateListener listener) {
		Assert.paramNotNull(listener, "listener");
		this.itemListeners.remove(listener);
	}

	public final void fireItemStateChanged(final boolean state) {
		if (!NullCompatibleEquivalence.equals(Boolean.valueOf(state), lastState)) {
			for (final IItemStateListener listener : new LinkedList<IItemStateListener>(itemListeners)) {
				listener.itemStateChanged();
			}
			lastState = state;
		}
	}

}
