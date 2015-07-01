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

package org.jowidgets.impl.widgets.basic.factory.internal;

import org.jowidgets.api.widgets.IComposite;
import org.jowidgets.api.widgets.IScrollComposite;
import org.jowidgets.api.widgets.blueprint.ICompositeBluePrint;
import org.jowidgets.api.widgets.descriptor.IScrollCompositeDescriptor;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.common.widgets.factory.IWidgetFactory;
import org.jowidgets.impl.spi.ISpiBluePrintFactory;
import org.jowidgets.impl.spi.blueprint.IScrollCompositeBluePrintSpi;
import org.jowidgets.impl.widgets.basic.BorderedScrollCompositeImpl;
import org.jowidgets.impl.widgets.basic.ScrollCompositeImpl;
import org.jowidgets.spi.IWidgetsServiceProvider;
import org.jowidgets.spi.widgets.IScrollCompositeSpi;
import org.jowidgets.tools.widgets.blueprint.BPF;

public class ScrollCompositeFactory extends AbstractWidgetFactory implements
        IWidgetFactory<IScrollComposite, IScrollCompositeDescriptor> {

    public ScrollCompositeFactory(
        final IGenericWidgetFactory genericWidgetFactory,
        final IWidgetsServiceProvider widgetsServiceProvider,
        final ISpiBluePrintFactory bpF) {

        super(genericWidgetFactory, widgetsServiceProvider, bpF);
    }

    @Override
    public IScrollComposite create(final Object parentUiReference, final IScrollCompositeDescriptor descriptor) {
        if (descriptor.getBorder() != null) {
            return createBorderScrollComposite(parentUiReference, descriptor);
        }
        else {
            return createNoBorderScrollComposite(parentUiReference, descriptor);
        }
    }

    private IScrollComposite createBorderScrollComposite(
        final Object parentUiReference,
        final IScrollCompositeDescriptor descriptor) {

        //create a composite with the desired border
        final ICompositeBluePrint compositeBp = BPF.composite();
        compositeBp.setSetup(descriptor);
        final IComposite composite = getGenericWidgetFactory().create(parentUiReference, compositeBp);

        return new BorderedScrollCompositeImpl(composite, descriptor);
    }

    private IScrollComposite createNoBorderScrollComposite(
        final Object parentUiReference,
        final IScrollCompositeDescriptor descriptor) {
        final IScrollCompositeBluePrintSpi bp = getSpiBluePrintFactory().scrollComposite().setSetup(descriptor);
        final IScrollCompositeSpi scrollContainerSpi = getSpiWidgetFactory().createScrollComposite(
                getGenericWidgetFactory(),
                parentUiReference,
                bp);
        return new ScrollCompositeImpl(scrollContainerSpi, descriptor);
    }

}
