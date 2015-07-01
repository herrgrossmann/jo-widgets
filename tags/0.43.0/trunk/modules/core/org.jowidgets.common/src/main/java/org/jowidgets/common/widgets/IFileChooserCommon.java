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

package org.jowidgets.common.widgets;

import java.io.File;
import java.util.List;

import org.jowidgets.common.types.DialogResult;
import org.jowidgets.common.types.IFileChooserFilter;

public interface IFileChooserCommon extends IDisplayCommon {

    /**
     * Sets the selected file or directory of the chooser that will be shown
     * when the chooser will be opened the first time.
     * 
     * @param file The file or directory to set
     */
    void setSelectedFile(File file);

    /**
     * Opens the chooser and blocks until the user has pressed OK or CANCEL
     * 
     * @return DialogResult.OK or DialogResult.CANCEL
     */
    DialogResult open();

    /**
     * Gets the files that was selected by the user.
     * 
     * @return The files that was selected by the user or an empty list.
     */
    List<File> getSelectedFiles();

    /**
     * Gets the filter that was selected by the user
     * 
     * @return The selected filter or null
     */
    IFileChooserFilter getSelectedFilter();

}
