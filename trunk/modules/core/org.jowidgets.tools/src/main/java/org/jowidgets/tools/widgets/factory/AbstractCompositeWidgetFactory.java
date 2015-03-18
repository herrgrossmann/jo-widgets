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
package org.jowidgets.tools.widgets.factory;

import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.api.widgets.descriptor.ICompositeDescriptor;
import org.jowidgets.common.widgets.descriptor.IWidgetDescriptor;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.common.widgets.factory.IWidgetFactory;

public abstract class AbstractCompositeWidgetFactory<WIDGET_TYPE extends IWidget, DESCRIPTOR_TYPE extends IWidgetDescriptor<WIDGET_TYPE>> implements
        IWidgetFactory<WIDGET_TYPE, DESCRIPTOR_TYPE> {

    protected abstract WIDGET_TYPE createWidget(IComposite composite, DESCRIPTOR_TYPE descriptor);

    @Override
    public WIDGET_TYPE create(final Object parentUiReference, final DESCRIPTOR_TYPE descriptor) {

        final IBluePrintFactory bpF = Toolkit.getBluePrintFactory();
        final IGenericWidgetFactory gwF = Toolkit.getWidgetFactory();

        final IComposite compositeWidget = gwF.create(parentUiReference, bpF.composite());

        if (compositeWidget == null) {
            throw new IllegalStateException("Could not create widget with descriptor interface class '"
                + ICompositeDescriptor.class
                + "' from '"
                + IGenericWidgetFactory.class.getName()
                + "'");
        }

        return createWidget(compositeWidget, descriptor);
    }

}
