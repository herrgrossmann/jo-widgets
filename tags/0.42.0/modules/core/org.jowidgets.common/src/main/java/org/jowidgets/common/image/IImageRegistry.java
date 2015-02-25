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
package org.jowidgets.common.image;

import java.net.URL;

public interface IImageRegistry {

	IImageHandle getImageHandle(IImageConstant key);

	void registerImageUrl(IImageUrlProvider imageUrlProvider);

	void registerImageStream(IImageStreamProvider imageStreamProvider);

	void registerImageProvider(IImageProvider imageProvider);

	void registerImageConstant(IImageConstant key, IImageHandle imageHandle);

	void registerImageConstant(IImageConstant key, IImageConstant substitude);

	void registerImageConstant(IImageConstant key, IImageDescriptor url);

	void registerImageConstant(IImageConstant key, URL url);

	void registerImageConstant(IImageConstant key, IImageUrlProvider urlProvider);

	<T extends Enum<?> & IImageUrlProvider> void registerImageEnum(final Class<T> enumClass);

	/**
	 * Unregisters an image. This also disposes the image. After that the image can not be used anymore
	 * 
	 * @param image The image to unregister
	 */
	void unRegisterImage(IImageCommon image);

}
