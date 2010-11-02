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
package org.jowidgets.impl.image;

import java.net.URL;

import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.image.IImageHandleFactory;
import org.jowidgets.common.image.IImageRegistry;

public class ImageConstantRegisterService {

	private final IImageRegistry imageRegistry;
	private final IImageHandleFactory imageHandleFactory;
	private final String rootPath;

	public ImageConstantRegisterService(
		final String rootPath,
		final IImageRegistry imageRegistry,
		final IImageHandleFactory imageHandleFactorySpi) {

		this.rootPath = rootPath;
		this.imageRegistry = imageRegistry;
		this.imageHandleFactory = imageHandleFactorySpi;
	}

	public final void registerImage(final IImageConstant imageConstant, final String relPath) {
		final URL url = getClass().getClassLoader().getResource(rootPath + relPath);
		imageRegistry.registerImageConstant(imageConstant, imageHandleFactory.createImageHandle(url));
	}

	protected IImageHandleFactory getImageHandleFactory() {
		return imageHandleFactory;
	}

}
