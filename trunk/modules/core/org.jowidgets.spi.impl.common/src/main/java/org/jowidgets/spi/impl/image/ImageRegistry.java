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
package org.jowidgets.spi.impl.image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jowidgets.common.image.IImageCommon;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.image.IImageDescriptor;
import org.jowidgets.common.image.IImageHandle;
import org.jowidgets.common.image.IImageHandleFactory;
import org.jowidgets.common.image.IImageProvider;
import org.jowidgets.common.image.IImageRegistry;
import org.jowidgets.common.image.IImageStreamProvider;
import org.jowidgets.common.image.IImageUrlProvider;
import org.jowidgets.util.Assert;

public class ImageRegistry implements IImageRegistry {

	private final Map<Object, IImageHandle> imageMap = new HashMap<Object, IImageHandle>();
	private final IImageHandleFactory imageHandleFactory;

	public ImageRegistry(final IImageHandleFactory imageHandleFactory) {
		Assert.paramNotNull(imageHandleFactory, "imageHandleFactory");
		this.imageHandleFactory = imageHandleFactory;
	}

	@Override
	public synchronized void registerImageConstant(final IImageConstant key, final IImageHandle imageHandle) {
		Assert.paramNotNull(key, "key");
		Assert.paramNotNull(imageHandle, "imageHandle");
		imageMap.put(key, imageHandle);
	}

	@Override
	public synchronized IImageHandle getImageHandle(final IImageConstant key) {
		IImageHandle result = imageMap.get(key);
		if (result == null && key instanceof IImageDescriptor) {
			registerImageConstant(key, (IImageDescriptor) key);
			result = imageMap.get(key);
		}
		return result;
	}

	@Override
	public void registerImageProvider(final IImageProvider imageProvider) {
		Assert.paramNotNull(imageProvider, "imageProvider");
		registerImageConstant((IImageConstant) imageProvider, (IImageDescriptor) imageProvider);
	}

	@Override
	public void registerImageUrl(final IImageUrlProvider imageUrlProvider) {
		Assert.paramNotNull(imageUrlProvider, "imageUrlProvider");
		registerImageConstant(imageUrlProvider, imageUrlProvider.getImageUrl());
	}

	@Override
	public void registerImageStream(final IImageStreamProvider imageStreamProvider) {
		Assert.paramNotNull(imageStreamProvider, "imageStreamProvider");
		registerImageConstant(imageStreamProvider, (IImageDescriptor) imageStreamProvider);
	}

	@Override
	public void registerImageConstant(final IImageConstant key, final IImageConstant substitude) {
		Assert.paramNotNull(key, "key");
		Assert.paramNotNull(substitude, "substitude");
		final IImageHandle imageHandle = imageMap.get(substitude);
		if (imageHandle == null) {
			throw new IllegalArgumentException("Substitude is not registered");
		}
		registerImageConstant(key, imageHandle);
	}

	@Override
	public void registerImageConstant(final IImageConstant key, final IImageUrlProvider urlProvider) {
		Assert.paramNotNull(key, "key");
		Assert.paramNotNull(urlProvider, "urlProvider");
		registerImageConstant(key, urlProvider.getImageUrl());
	}

	@Override
	public void registerImageConstant(final IImageConstant key, final URL url) {
		Assert.paramNotNull(key, "key");
		Assert.paramNotNull(url, "url (for key '" + key + "')");
		registerImageConstant(key, new UrlImageDescriptorImpl(url));
	}

	@Override
	public void registerImageConstant(final IImageConstant key, final IImageDescriptor descriptor) {
		Assert.paramNotNull(key, "key");
		Assert.paramNotNull(descriptor, "descriptor (for key '" + key + "')");
		imageMap.put(key, imageHandleFactory.createImageHandle(descriptor));
	}

	@Override
	public <T extends Enum<?> & IImageUrlProvider> void registerImageEnum(final Class<T> enumClass) {
		for (final IImageUrlProvider imageUrlProvider : enumClass.getEnumConstants()) {
			registerImageUrl(imageUrlProvider);
		}
	}

	@Override
	public void unRegisterImage(final IImageCommon image) {
		Assert.paramNotNull(image, "image");
		imageMap.remove(image);
		image.dispose();
	}

	protected IImageHandleFactory getImageHandleFactory() {
		return imageHandleFactory;
	}

}
