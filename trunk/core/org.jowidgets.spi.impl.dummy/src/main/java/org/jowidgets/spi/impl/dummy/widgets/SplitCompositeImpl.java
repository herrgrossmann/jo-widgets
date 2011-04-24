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

package org.jowidgets.spi.impl.dummy.widgets;

import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.spi.impl.dummy.dummyui.UIDContainer;
import org.jowidgets.spi.impl.dummy.dummyui.UIDSplitPane;
import org.jowidgets.spi.widgets.ICompositeSpi;
import org.jowidgets.spi.widgets.ISplitCompositeSpi;
import org.jowidgets.spi.widgets.setup.ISplitCompositeSetupSpi;

public class SplitCompositeImpl extends DummyControl implements ISplitCompositeSpi {

	private final ICompositeSpi first;
	private final ICompositeSpi second;

	public SplitCompositeImpl(final IGenericWidgetFactory factory, final ISplitCompositeSetupSpi setup) {
		super(new UIDSplitPane(setup.getOrientation(), setup.getWeight(), setup.getResizePolicy()));

		final UIDContainer content1 = new UIDContainer();
		final UIDContainer content2 = new UIDContainer();

		content1.setBorder(setup.getFirstBorder());
		content2.setBorder(setup.getSecondBorder());

		first = new DummyComposite(factory, content1);
		second = new DummyComposite(factory, content2);

		first.setLayout(setup.getFirstLayout());
		second.setLayout(setup.getSecondLayout());

		final UIDSplitPane splitPane = getUiReference();

		splitPane.setFirst(content1);
		splitPane.setSecond(content2);

		splitPane.setDividerSize(setup.getDividerSize());
	}

	@Override
	public ICompositeSpi getFirst() {
		return first;
	}

	@Override
	public ICompositeSpi getSecond() {
		return second;
	}

	@Override
	public void setClientAreaMinSizes(final Dimension firstMinSize, final Dimension secondMinSize) {
		// TODO LG implement setMinSizes
	}

	@Override
	public UIDSplitPane getUiReference() {
		return (UIDSplitPane) super.getUiReference();
	}

}
