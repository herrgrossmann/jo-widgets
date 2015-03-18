/*
 * Copyright (c) 2011, M. Grossmann, H. Westphal
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

package org.jowidgets.workbench.api;

import java.util.List;

public interface IViewLayout extends IWorkbenchPart {

    /**
     * @return The id of the referenced view
     */
    String getId();

    /**
     * Gets the scope of the view.
     * If the scope is COMPONENT, the view will be created by its component.
     * If the scope is WORKBENCH_APPLICATION, the view will be created by its workbench application.
     * If the scope is WORKBENCH, the view will be created by the workbench.
     * 
     * @return The scope of the view.
     */
    ViewScope getScope();

    /**
     * Gets the close policy for the view.
     * 
     * If the policy is HIDE, the view will be hidden if the views close button was pressed.
     * If the policy is DISPOSE, the view will be disposed if the views close button was pressed.
     * 
     * Remark: In both cases, the onClose(IVetoable vetoable) method will be invoked!
     * 
     * @return The close policy
     */
    ClosePolicy getClosePolicy();

    boolean isHidden();

    boolean isDetachable();

    List<String> getFolderWhitelist();

    List<String> getFolderBlacklist();

}
