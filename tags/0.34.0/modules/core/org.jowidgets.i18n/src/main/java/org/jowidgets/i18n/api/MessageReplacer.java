/*
 * Copyright (c) 2012, grossmann
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

package org.jowidgets.i18n.api;

import java.util.Collection;

public final class MessageReplacer {

	private static final IMessageReplacer MESSAGE_REPLACER = new MessageReplacerImpl();

	private MessageReplacer() {}

	public static IMessageReplacer getInstance() {
		return MESSAGE_REPLACER;
	}

	/**
	 * Replaces all variables (%1, ..., %n) with the given string parameters
	 * 
	 * @param message The message that contains variables
	 * @param parameter The parameters to insert
	 * 
	 * @return A new string where the variables was replaced with the parameters
	 */
	public static String replace(final IMessage message, final String... parameter) {
		return MESSAGE_REPLACER.replace(message.get(), parameter);
	}

	/**
	 * Replaces all variables (%1, ..., %n) with the given string parameters
	 * 
	 * @param message The message that contains variables
	 * @param parameter The parameters to insert
	 * 
	 * @return A new string where the variables was replaced with the parameters
	 */
	public static String replace(final String message, final String... parameter) {
		return MESSAGE_REPLACER.replace(message, parameter);
	}

	/**
	 * Replaces all variables (%1, ..., %n) with the given string parameters
	 * 
	 * @param message The message that contains variables
	 * @param parameter The parameters to insert
	 * 
	 * @return A new string where the variables was replaced with the parameters
	 */
	public static String replace(final String message, final Collection<String> parameter) {
		return MESSAGE_REPLACER.replace(message, parameter);
	}

	private static final class MessageReplacerImpl implements IMessageReplacer {

		@Override
		public String replace(final String message, final String... parameters) {
			final StringBuilder result = new StringBuilder();
			final StringBuilder digits = new StringBuilder();
			boolean digitMode = false;
			boolean toLowercase = false;
			for (final char c : message.toCharArray()) {
				if (digitMode) {
					if (c >= '0' && c <= '9') {
						digits.append(c);
					}
					else if (c == 'L') {
						toLowercase = true;
					}
					else {
						if (digits.length() > 0) {
							final int paramIndex = Integer.valueOf(digits.toString()) - 1;
							if (paramIndex > parameters.length) {
								throw new IllegalStateException("Message '" + message + "' contains to many placeholders.");
							}
							result.append(getParameter(paramIndex, toLowercase, parameters));
						}
						else {
							result.append('%');
						}
						result.append(c);
						digitMode = false;
						toLowercase = false;
					}
				}
				else if (c == '%') {
					digits.setLength(0);
					digitMode = true;
				}
				else {
					result.append(c);
				}
			}

			if (digitMode && digits.length() > 0) {
				final int paramIndex = Integer.valueOf(digits.toString()) - 1;
				if (paramIndex > parameters.length) {
					throw new IllegalStateException("Message '" + message + "' contains to many placeholders.");
				}
				result.append(getParameter(paramIndex, toLowercase, parameters));
			}

			return result.toString();
		}

		@Override
		public String replace(final String message, final Collection<String> parameters) {

			return replace(message, parameters.toArray(new String[parameters.size()]));
		}

		private String getParameter(final int paramIndex, final boolean toLowercase, final String... parameters) {
			if (toLowercase) {
				return parameters[paramIndex].toLowerCase(LocaleHolder.getUserLocale());
			}
			else {
				return parameters[paramIndex];
			}
		}
	}

}
