/*
 * Copyright (c) 2010, Michael Grossmann
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
package org.jowidgets.spi.impl.image;

import org.jowidgets.common.image.IImageDescriptor;
import org.jowidgets.common.image.IImageHandle;
import org.jowidgets.util.Assert;

public class ImageHandle<IMAGE_TYPE> implements IImageHandle {

	private final IImageFactory<IMAGE_TYPE> imageFactory;
	private final IImageDescriptor imageDescriptor;

	private boolean disposed;

	private IMAGE_TYPE image;

	public ImageHandle(final IMAGE_TYPE image) {
		this(new IImageFactory<IMAGE_TYPE>() {
			@Override
			public IMAGE_TYPE createImage() {
				return image;
			}
		}, null);
	}

	public ImageHandle(final IImageFactory<IMAGE_TYPE> imageFactory) {
		this(imageFactory, null);
	}

	public ImageHandle(final IImageFactory<IMAGE_TYPE> imageFactory, final IImageDescriptor imageDescriptor) {
		Assert.paramNotNull(imageFactory, "imageFactory");
		this.imageFactory = imageFactory;
		this.imageDescriptor = imageDescriptor;

		this.disposed = false;
	}

	@Override
	public final synchronized IMAGE_TYPE getImage() {
		checkDisposed();
		if (image == null) {
			image = imageFactory.createImage();
		}
		return image;
	}

	@Override
	public final IImageDescriptor getImageDescriptor() {
		checkDisposed();
		return imageDescriptor;
	}

	public final synchronized boolean isInitialized() {
		checkDisposed();
		return image != null;
	}

	public final synchronized boolean isDisposed() {
		return disposed;
	}

	public synchronized void dispose() {
		checkDisposed();
		disposed = true;
		image = null;
	}

	private void checkDisposed() {
		if (disposed) {
			throw new IllegalStateException("Image handle is disposed");
		}
	}

}
