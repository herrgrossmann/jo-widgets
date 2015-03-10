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

package org.jowidgets.api.command;

import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.types.Accelerator;
import org.jowidgets.common.types.Modifier;
import org.jowidgets.common.types.VirtualKey;

public interface IActionBuilder {

	IActionBuilder setText(String text);

	IActionBuilder setToolTipText(final String toolTipText);

	IActionBuilder setIcon(IImageConstant icon);

	IActionBuilder setMnemonic(final Character mnemonic);

	IActionBuilder setMnemonic(final char mnemonic);

	IActionBuilder setAccelerator(Accelerator accelerator);

	IActionBuilder setAccelerator(final char key, final Modifier... modifier);

	IActionBuilder setAccelerator(final VirtualKey virtualKey, final Modifier... modifier);

	IActionBuilder setEnabled(boolean enabled);

	IActionBuilder setCommand(ICommand command);

	IActionBuilder setCommand(ICommandExecutor command);

	IActionBuilder setCommand(ICommandExecutor command, IEnabledChecker executableStateChecker);

	IActionBuilder setCommand(ICommandExecutor command, IExceptionHandler exceptionHandler);

	IActionBuilder setCommand(ICommandExecutor command, IEnabledChecker enabledChecker, IExceptionHandler exceptionHandler);

	/**
	 * Set's the ExceptionHandler of the action. The actions ExceptionHandler handles exceptions that are not
	 * handled by the command's exception handler.
	 * 
	 * The actions ExceptionHandler should be implemented independently of the current command. If exception handling
	 * is command specific, the commands exception handler should be used for that.
	 * 
	 * If no exception handler is set, a default handler will be used for the action.
	 * 
	 * @param exceptionHandler The ExceptionHandler to set
	 * @return this instance
	 */
	IActionBuilder setActionExceptionHandler(IExceptionHandler exceptionHandler);

	/**
	 * Creates a new command action defined by the builder setup
	 * 
	 * @return A new command action, never null
	 */
	ICommandAction build();

}
