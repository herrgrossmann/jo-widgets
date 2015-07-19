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

package org.jowidgets.spi.impl.swt.common;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.jowidgets.spi.IOptionalWidgetsFactorySpi;
import org.jowidgets.spi.impl.swt.common.image.SwtImageRegistry;
import org.jowidgets.spi.impl.swt.common.widgets.CalendarImpl;
import org.jowidgets.spi.impl.swt.common.widgets.DirectoryChooserImpl;
import org.jowidgets.spi.impl.swt.common.widgets.FileChooserImpl;
import org.jowidgets.spi.widgets.ICalendarSpi;
import org.jowidgets.spi.widgets.IDirectoryChooserSpi;
import org.jowidgets.spi.widgets.IFileChooserSpi;
import org.jowidgets.spi.widgets.setup.ICalendarSetupSpi;
import org.jowidgets.spi.widgets.setup.IDirectoryChooserSetupSpi;
import org.jowidgets.spi.widgets.setup.IFileChooserSetupSpi;

public class SwtOptionalWidgetsFactory implements IOptionalWidgetsFactorySpi {

    private final SwtImageRegistry imageRegistry;

    public SwtOptionalWidgetsFactory(final SwtImageRegistry imageRegistry) {
        this.imageRegistry = imageRegistry;
    }

    @Override
    public boolean hasFileChooser() {
        try {
            FileDialog.class.getName();
        }
        catch (final NoClassDefFoundError error) {
            return false;
        }
        return true;
    }

    @Override
    public IFileChooserSpi createFileChooser(final Object parentUiReference, final IFileChooserSetupSpi setup) {
        return new FileChooserImpl(parentUiReference, setup);
    }

    @Override
    public boolean hasDirectoryChooser() {
        try {
            DirectoryDialog.class.getName();
        }
        catch (final NoClassDefFoundError error) {
            return false;
        }
        return true;
    }

    @Override
    public IDirectoryChooserSpi createDirectoryChooser(final Object parentUiReference, final IDirectoryChooserSetupSpi setup) {
        return new DirectoryChooserImpl(parentUiReference, setup);
    }

    @Override
    public boolean hasCalendar() {
        return true;
    }

    @Override
    public ICalendarSpi createCalendar(final Object parentUiReference, final ICalendarSetupSpi setup) {
        return new CalendarImpl(parentUiReference, setup, imageRegistry);
    }

}
