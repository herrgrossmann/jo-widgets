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

package org.jowidgets.spi.impl.swing.common.options;

public final class SwingOptions {

    private static boolean oneTouchExpandableSplits = false;
    private static boolean internalFramesForTabFolders = false;
    private static boolean joWidgetsTabLayout = false;
    private static boolean wrapTabLayout = false;
    private static boolean nativeMigLayout = true;
    private static boolean defaultTableTransferHandler = true;
    private static boolean defaultTreeTransferHandler = true;
    private static Long clipbaordPollingMillis = Long.valueOf(1000L);

    private SwingOptions() {}

    public static boolean isOneTouchExpandableSplits() {
        return oneTouchExpandableSplits;
    }

    public static void setOneTouchExpandableSplits(final boolean oneTouchExpandableSplits) {
        SwingOptions.oneTouchExpandableSplits = oneTouchExpandableSplits;
    }

    public static boolean isInternalFramesForTabFolders() {
        return internalFramesForTabFolders;
    }

    public static void setInternalFramesForTabFolders(final boolean internalFramesForTabFolders) {
        SwingOptions.internalFramesForTabFolders = internalFramesForTabFolders;
    };

    public static boolean isJoWidgetsTabLayout() {
        return joWidgetsTabLayout;
    }

    public static void setJoWidgetsTabLayout(final boolean joWidgetsTabLayout) {
        SwingOptions.joWidgetsTabLayout = joWidgetsTabLayout;
    }

    public static void setWrapTabLayout(final boolean wrapTabLayout) {
        SwingOptions.wrapTabLayout = wrapTabLayout;
    }

    public static boolean isWrapTapLayout() {
        return SwingOptions.wrapTabLayout;
    }

    public static boolean hasNativeMigLayout() {
        return nativeMigLayout;
    }

    public static void setNativeMigLayout(final boolean useNativeMigLayout) {
        SwingOptions.nativeMigLayout = useNativeMigLayout;
    }

    public static boolean isDefaultTableTransferHandler() {
        return defaultTableTransferHandler;
    }

    public static void setDefaultTableTransferHandler(final boolean defaultTableTransferHandler) {
        SwingOptions.defaultTableTransferHandler = defaultTableTransferHandler;
    }

    public static boolean isDefaultTreeTransferHandler() {
        return defaultTreeTransferHandler;
    }

    public static void setDefaultTreeTransferHandler(final boolean defaultTreeTransferHandler) {
        SwingOptions.defaultTreeTransferHandler = defaultTreeTransferHandler;
    }

    public static Long getClipbaordPollingMillis() {
        return clipbaordPollingMillis;
    }

    /**
     * Sets the clipboard polling millis. If set to null, clipboard polling is deactivated.
     * 
     * @param clipbaordPolling The polling interval in millis or null
     */
    public static void setClipbaordPollingMillis(final Long clipbaordPolling) {
        SwingOptions.clipbaordPollingMillis = clipbaordPolling;
    }

}
