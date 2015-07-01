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

package org.jowidgets.spi.impl.controller;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jowidgets.common.types.Modifier;
import org.jowidgets.common.types.MouseButton;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controller.IMouseButtonEvent;
import org.jowidgets.common.widgets.controller.IMouseEvent;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.controller.IMouseObservable;
import org.jowidgets.util.Assert;

public class MouseObservable implements IMouseObservable {

    private final Set<IMouseListener> listeners;

    public MouseObservable() {
        this.listeners = new LinkedHashSet<IMouseListener>();
    }

    @Override
    public final void addMouseListener(final IMouseListener listener) {
        Assert.paramNotNull(listener, "listener");
        this.listeners.add(listener);
    }

    @Override
    public final void removeMouseListener(final IMouseListener listener) {
        Assert.paramNotNull(listener, "listener");
        this.listeners.remove(listener);
    }

    public final void fireMousePressed(final Position position, final MouseButton mouseButton, final Set<Modifier> modifiers) {
        fireMousePressed(new MouseButtonEvent(position, mouseButton, modifiers));
    }

    public final void fireMouseReleased(final Position position, final MouseButton mouseButton, final Set<Modifier> modifiers) {
        fireMouseReleased(new MouseButtonEvent(position, mouseButton, modifiers));
    }

    public final void fireMouseDoubleClicked(final Position position, final MouseButton mouseButton, final Set<Modifier> modifiers) {
        fireMouseDoubleClicked(new MouseButtonEvent(position, mouseButton, modifiers));
    }

    public final void fireMouseEnter(final Position position) {
        fireMouseEnter(new MouseEvent(position));
    }

    public final void fireMouseExit(final Position position) {
        fireMouseExit(new MouseEvent(position));
    }

    public final void fireMousePressed(final IMouseButtonEvent mouseEvent) {
        for (final IMouseListener listener : new LinkedList<IMouseListener>(listeners)) {
            listener.mousePressed(mouseEvent);
        }
    }

    public final void fireMouseReleased(final IMouseButtonEvent mouseEvent) {
        for (final IMouseListener listener : new LinkedList<IMouseListener>(listeners)) {
            listener.mouseReleased(mouseEvent);
        }
    }

    public final void fireMouseDoubleClicked(final IMouseButtonEvent mouseEvent) {
        for (final IMouseListener listener : new LinkedList<IMouseListener>(listeners)) {
            listener.mouseDoubleClicked(mouseEvent);
        }
    }

    public final void fireMouseEnter(final IMouseEvent mouseEvent) {
        for (final IMouseListener listener : new LinkedList<IMouseListener>(listeners)) {
            listener.mouseEnter(mouseEvent);
        }
    }

    public final void fireMouseExit(final IMouseEvent mouseEvent) {
        for (final IMouseListener listener : new LinkedList<IMouseListener>(listeners)) {
            listener.mouseExit(mouseEvent);
        }
    }

}
