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

package org.jowidgets.spi.impl.dummy.dummyui;

import java.util.HashSet;
import java.util.Set;

import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controler.IActionListener;
import org.jowidgets.common.widgets.controler.IActionObservable;
import org.jowidgets.common.widgets.controler.IInputListener;
import org.jowidgets.common.widgets.controler.IInputObservable;
import org.jowidgets.common.widgets.controler.IItemStateListener;
import org.jowidgets.common.widgets.controler.IItemStateObservable;
import org.jowidgets.common.widgets.controler.IMenuListener;
import org.jowidgets.common.widgets.controler.IMenuObservable;
import org.jowidgets.common.widgets.controler.IPopupDetectionListener;
import org.jowidgets.common.widgets.controler.IPopupDetectionObservable;
import org.jowidgets.common.widgets.controler.IWindowListener;
import org.jowidgets.common.widgets.controler.IWindowObservable;

public class UIDObservable implements
		IActionObservable,
		IInputObservable,
		IWindowObservable,
		IItemStateObservable,
		IMenuObservable,
		IPopupDetectionObservable {

	private final Set<IInputListener> inputListeners;
	private final Set<IActionListener> actionListeners;
	private final Set<IWindowListener> windowListeners;
	private final Set<IItemStateListener> itemStateListeners;
	private final Set<IMenuListener> menuListeners;
	private final Set<IPopupDetectionListener> popupListeners;

	public UIDObservable() {
		super();
		this.inputListeners = new HashSet<IInputListener>();
		this.actionListeners = new HashSet<IActionListener>();
		this.windowListeners = new HashSet<IWindowListener>();
		this.itemStateListeners = new HashSet<IItemStateListener>();
		this.menuListeners = new HashSet<IMenuListener>();
		this.popupListeners = new HashSet<IPopupDetectionListener>();
	}

	@Override
	public void addActionListener(final IActionListener listener) {
		actionListeners.add(listener);
	}

	@Override
	public void removeActionListener(final IActionListener listener) {
		actionListeners.remove(listener);
	}

	@Override
	public void addInputListener(final IInputListener listener) {
		inputListeners.add(listener);
	}

	@Override
	public void removeInputListener(final IInputListener listener) {
		inputListeners.remove(listener);
	}

	@Override
	public void addWindowListener(final IWindowListener listener) {
		windowListeners.add(listener);
	}

	@Override
	public void removeWindowListener(final IWindowListener listener) {
		windowListeners.remove(listener);
	}

	@Override
	public void addItemListener(final IItemStateListener listener) {
		itemStateListeners.add(listener);
	}

	@Override
	public void removeItemListener(final IItemStateListener listener) {
		itemStateListeners.remove(listener);
	}

	@Override
	public void addMenuListener(final IMenuListener listener) {
		menuListeners.add(listener);
	}

	@Override
	public void removeMenuListener(final IMenuListener listener) {
		menuListeners.remove(listener);
	}

	@Override
	public void addPopupDetectionListener(final IPopupDetectionListener listener) {
		popupListeners.add(listener);
	}

	@Override
	public void removePopupDetectionListener(final IPopupDetectionListener listener) {
		popupListeners.remove(listener);
	}

	public void fireActionPerformed() {
		for (final IActionListener listener : actionListeners) {
			listener.actionPerformed();
		}
	}

	public void fireItemStateChanged() {
		for (final IItemStateListener listener : itemStateListeners) {
			listener.itemStateChanged();
		}
	}

	public void fireInputChanged(final Object source) {
		for (final IInputListener listener : inputListeners) {
			listener.inputChanged(source);
		}
	}

	public void firePopupDetected(final Position position) {
		for (final IPopupDetectionListener listener : popupListeners) {
			listener.popupDetected(position);
		}
	}

	void fireWindowActivated() {
		for (final IWindowListener listener : windowListeners) {
			listener.windowActivated();
		}
	}

	void fireWindowDeactivated() {
		for (final IWindowListener listener : windowListeners) {
			listener.windowDeactivated();
		}
	}

	void fireWindowIconified() {
		for (final IWindowListener listener : windowListeners) {
			listener.windowIconified();
		}
	}

	void fireWindowDeiconified() {
		for (final IWindowListener listener : windowListeners) {
			listener.windowDeiconified();
		}
	}

	void fireWindowClosed() {
		for (final IWindowListener listener : windowListeners) {
			listener.windowClosed();
		}
	}

	void fireMenuActivated() {
		for (final IMenuListener listener : menuListeners) {
			listener.menuActivated();
		}
	}

	void fireMenuDeactivated() {
		for (final IMenuListener listener : menuListeners) {
			listener.menuDeactivated();
		}
	}
}
