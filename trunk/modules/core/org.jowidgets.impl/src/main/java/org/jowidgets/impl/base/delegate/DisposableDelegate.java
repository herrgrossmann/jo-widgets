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

package org.jowidgets.impl.base.delegate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jowidgets.api.controller.IDisposeListener;
import org.jowidgets.util.Assert;

public class DisposableDelegate {

    private final Set<IDisposeListener> listeners;

    private boolean disposed;

    public DisposableDelegate() {
        this.listeners = new LinkedHashSet<IDisposeListener>();
        this.disposed = false;
    };

    public void addDisposeListener(final IDisposeListener listener) {
        Assert.paramNotNull(listener, "listener");
        listeners.add(listener);
    }

    public void removeDisposeListener(final IDisposeListener listener) {
        Assert.paramNotNull(listener, "listener");
        listeners.remove(listener);
    }

    public void dispose() {
        if (!disposed) {
            fireOnDispose();
            this.disposed = true;
            listeners.clear();
        }
    }

    public boolean isDisposed() {
        return disposed;
    }

    public void fireOnDispose() {
        for (final IDisposeListener listener : new ArrayList<IDisposeListener>(listeners)) {
            listener.onDispose();
        }
    }

}
