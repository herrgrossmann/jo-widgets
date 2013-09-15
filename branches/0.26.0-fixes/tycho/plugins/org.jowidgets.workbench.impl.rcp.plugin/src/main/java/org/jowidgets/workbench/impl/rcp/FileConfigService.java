/*
 * Copyright (c) 2011, H. Westphal
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of jo-widgets.org nor the
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

package org.jowidgets.workbench.impl.rcp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jowidgets.workbench.api.IWorkbenchConfigurationService;

public final class FileConfigService implements IWorkbenchConfigurationService {

	private final String filePath;

	public FileConfigService(final String filePath) {
		this.filePath = filePath;
	}

	@Override
	public Serializable loadConfiguration() {
		try {
			final ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));
			try {
				final Serializable configuration = (Serializable) oos.readObject();
				// CHECKSTYLE:OFF
				System.out.println("Read configuration: " + configuration);
				// CHECKSTYLE:ON
				return configuration;
			}
			finally {
				oos.close();
			}
		}
		catch (final Exception e) {
			// CHECKSTYLE:OFF
			System.err.println("An error occured while loading config: " + e);
			// CHECKSTYLE:ON
			return null;
		}
	}

	@Override
	public void saveConfiguration(final Serializable configuration) {
		try {
			final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
			try {
				oos.writeObject(configuration);
				// CHECKSTYLE:OFF
				System.out.println("Wrote configuration: " + configuration);
				// CHECKSTYLE:ON
			}
			finally {
				oos.close();
			}
		}
		catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
