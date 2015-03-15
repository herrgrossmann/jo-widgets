/*
 * Copyright (c) 2010, Manuel Woelker, Michael Grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.jowidgets.spi.impl.swing.common.image;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jowidgets.common.image.IImageDescriptor;
import org.jowidgets.common.image.IStreamFactoryImageDescriptor;
import org.jowidgets.common.image.IStreamImageDescriptor;
import org.jowidgets.common.image.IUrlImageDescriptor;
import org.jowidgets.spi.impl.image.IImageFactory;
import org.jowidgets.util.Assert;
import org.jowidgets.util.io.IoUtils;

@SuppressWarnings("deprecation")
public class SwingImageLoader implements IImageFactory<Image> {

	private final IImageDescriptor imageDescriptor;

	public SwingImageLoader(final IImageDescriptor imageDescriptor) {
		Assert.paramNotNull(imageDescriptor, "imageDescriptor");
		this.imageDescriptor = imageDescriptor;
	}

	@Override
	public Image createImage() {
		InputStream inputStream = null;
		try {
			inputStream = getInputStream();
			return ImageIO.read(inputStream);
		}
		catch (final IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IoUtils.tryCloseSilent(inputStream);
		}
	}

	private InputStream getInputStream() throws IOException {
		if (imageDescriptor instanceof IUrlImageDescriptor) {
			return ((IUrlImageDescriptor) imageDescriptor).getImageUrl().openStream();
		}
		else if (imageDescriptor instanceof IStreamFactoryImageDescriptor) {
			return ((IStreamFactoryImageDescriptor) imageDescriptor).createInputStream();
		}
		else if (imageDescriptor instanceof IStreamImageDescriptor) {
			return ((IStreamImageDescriptor) imageDescriptor).getInputStream();
		}
		else {
			throw new IllegalArgumentException("Image decriptor type '" + imageDescriptor + "' is not known");
		}
	}
}
