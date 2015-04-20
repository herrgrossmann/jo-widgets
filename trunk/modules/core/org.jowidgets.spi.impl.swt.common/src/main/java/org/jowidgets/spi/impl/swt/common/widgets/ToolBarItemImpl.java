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

package org.jowidgets.spi.impl.swt.common.widgets;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ToolItem;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.Position;
import org.jowidgets.spi.impl.swt.common.image.SwtImageRegistry;
import org.jowidgets.spi.widgets.IToolBarItemSpi;

public class ToolBarItemImpl implements IToolBarItemSpi {

    private final ToolItem toolItem;
    private final SwtImageRegistry imageRegistry;

    public ToolBarItemImpl(final ToolItem item, final SwtImageRegistry imageRegistry) {
        super();
        this.toolItem = item;
        this.imageRegistry = imageRegistry;
    }

    @Override
    public ToolItem getUiReference() {
        return toolItem;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        toolItem.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return toolItem.isEnabled();
    }

    @Override
    public void setText(final String text) {
        if (text != null) {
            toolItem.setText(text);
        }
        else {
            toolItem.setText("");
        }
    }

    @Override
    public void setToolTipText(final String text) {
        toolItem.setToolTipText(text);
    }

    @Override
    public void setIcon(final IImageConstant icon) {
        toolItem.setImage(imageRegistry.getImage(icon));
    }

    @Override
    public Position getPosition() {
        final Rectangle bounds = toolItem.getBounds();
        return new Position(bounds.x, bounds.y);
    }

    @Override
    public Dimension getSize() {
        final Rectangle bounds = toolItem.getBounds();
        return new Dimension(bounds.width, bounds.height);
    }

}
