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

package org.jowidgets.spi.impl.dummy.ui;

import org.jowidgets.common.types.Orientation;
import org.jowidgets.common.types.SplitResizePolicy;

public class UIDSplitPane extends UIDComponent {

    private final Orientation orientation;
    private final double weight;
    private final SplitResizePolicy resizePolicy;

    private int dividerSize;
    private UIDContainer first;
    private UIDContainer second;

    public UIDSplitPane(final Orientation orientation, final double weight, final SplitResizePolicy resizePolicy) {
        this.orientation = orientation;
        this.weight = weight;
        this.resizePolicy = resizePolicy;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public double getWeight() {
        return weight;
    }

    public SplitResizePolicy getResizePolicy() {
        return resizePolicy;
    }

    public int getDividerSize() {
        return dividerSize;
    }

    public void setDividerSize(final int dividerSize) {
        this.dividerSize = dividerSize;
    }

    public UIDContainer getFirst() {
        return first;
    }

    public void setFirst(final UIDContainer first) {
        this.first = first;
    }

    public UIDContainer getSecond() {
        return second;
    }

    public void setSecond(final UIDContainer second) {
        this.second = second;
    }

}
