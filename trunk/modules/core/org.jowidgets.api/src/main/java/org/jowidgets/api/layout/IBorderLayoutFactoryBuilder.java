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

package org.jowidgets.api.layout;

import org.jowidgets.common.widgets.layout.ILayouter;

public interface IBorderLayoutFactoryBuilder {

    /**
     * Sets the top, left, right and bottom margin.
     * 
     * The default margin is 0
     * 
     * @param margin The margin to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder margin(int margin);

    /**
     * Sets the x and y gap between the center control and the outer controls.
     * 
     * The default gap is 4
     * 
     * @param margin The gap to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder gap(int gap);

    /**
     * Sets the x gap between the center control and the outer controls.
     * 
     * The default gap is 4
     * 
     * @param margin The gap to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder gapX(int gapX);

    /**
     * Sets the y gap between the center control and the outer controls.
     * 
     * The default gap is 4
     * 
     * @param margin The gap to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder gapY(int gapY);

    /**
     * Sets the left margin.
     * 
     * The default margin is 0
     * 
     * @param margin The margin to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder marginLeft(int marginLeft);

    /**
     * Sets the right margin.
     * 
     * The default margin is 0
     * 
     * @param margin The margin to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder marginRight(int marginRight);

    /**
     * Sets the top margin.
     * 
     * The default margin is 0
     * 
     * @param margin The margin to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder marginTop(int marginTop);

    /**
     * Sets the bottom margin.
     * 
     * The default margin is 0
     * 
     * @param margin The margin to set
     * 
     * @return This builder
     */
    IBorderLayoutFactoryBuilder marginBottom(int marginBottom);

    /**
     * Creates a new layout factory for 'BorderLayout'
     * 
     * @return A new layout factory for 'BorderLayout', never null
     */
    ILayoutFactory<ILayouter> build();

}
