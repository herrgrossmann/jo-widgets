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

package org.jowidgets.spi.impl.mask;

import java.util.Arrays;
import java.util.List;

public class MatchResult {

    private final int lastMatch;
    private final int lastMatchMaskPos;
    private final int[] indices;
    private final boolean matching;

    public MatchResult(final List<Integer> matchResult, final int length, final boolean matching) {
        this.indices = new int[length];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = -1;
        }

        int index = 0;
        for (final Integer integer : matchResult) {
            indices[index] = integer.intValue();
            index++;
        }

        this.matching = matching;

        int last = -1;
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] != -1) {
                last = i;
            }
            else {
                break;
            }
        }
        this.lastMatch = last;
        if (last > -1) {
            this.lastMatchMaskPos = getMaskIndex(last);
        }
        else {
            this.lastMatchMaskPos = -1;
        }
    }

    public int getMaskIndex(final int index) {
        return indices[index];
    }

    public int getLastMatch() {
        return lastMatch;
    }

    public int getLastMatchMaskPos() {
        return lastMatchMaskPos;
    }

    public boolean isMatching() {
        return matching;
    }

    @Override
    public String toString() {
        return "MatchResult [matching="
            + matching
            + ", lastMatch="
            + lastMatch
            + ", lastMatchMaskPos="
            + lastMatchMaskPos
            + ", indices="
            + Arrays.toString(indices)
            + "]";
    }

}
