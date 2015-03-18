/*
 * Copyright (c) 2012, grossmann
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

package org.jowidgets.util;

/**
 * A value of a specific type that will be initialized later.
 * 
 * @param <VALUE_TYPE> The type of the value that will be initialized later
 */
public interface IFutureValue<VALUE_TYPE> {

    /**
     * Adds a callback that will be invoked in the following manner:
     * 
     * 1. If the value is already initialized, the given callback will be invoked immediately
     * 
     * 2. If the value was not already initialized, the given callback will be invoked later
     * but immediately after the value was initialized
     * 
     * Remark: After the initialize method was invoked on the callback, the callback reference will be cleared
     * automatically on this future, because a future value can not be initialized twice.
     * Clients of this interface may not remove the callback for initialized values manually
     * 
     * @param callback The callback that gets the initialized value
     */
    void addFutureCallback(IFutureValueCallback<VALUE_TYPE> callback);

    /**
     * Removes a creation callback if no longer interested on the value.
     * 
     * @param callback The callback to remove
     */
    void removeFutureCallback(IFutureValueCallback<VALUE_TYPE> callback);

    /**
     * @return True if the value was already initialized, false otherwise
     */
    boolean isInitialized();

    /**
     * Gets the value if the future is initialized and throw a runtime exception otherwise
     * 
     * @return The value of the future if initialized (may be null)
     * @throws IllegalStateException if the future is not already initialized
     */
    VALUE_TYPE getValue();

}
