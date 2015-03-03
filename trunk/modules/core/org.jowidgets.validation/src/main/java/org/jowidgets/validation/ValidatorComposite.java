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

package org.jowidgets.validation;

/**
 * Accessor class for a validator composite
 */
public final class ValidatorComposite {

	private ValidatorComposite() {}

	/**
	 * Creates a new validator composite builder
	 * 
	 * @return A new validator composite builder
	 */
	public static <VALUE_TYPE> IValidatorCompositeBuilder<VALUE_TYPE> builder() {
		return new ValidatorCompositeBuilderImpl<VALUE_TYPE>();
	}

	/**
	 * Creates a new validator based on two given validators.
	 * 
	 * Both validators may be null. In this case, the OkValidator will
	 * be returned
	 * 
	 * @param validator1 The first validator, may be null
	 * @param validator2 The second validator, may be null
	 * 
	 * @return The resulting validator, never null
	 */
	public static <VALUE_TYPE> IValidator<VALUE_TYPE> create(
		final IValidator<VALUE_TYPE> validator1,
		final IValidator<VALUE_TYPE> validator2) {
		if (validator1 != null && validator2 != null) {
			final IValidatorCompositeBuilder<VALUE_TYPE> builder = builder();
			builder.add(validator1).add(validator2);
			return builder.build();
		}
		else if (validator1 != null) {
			return validator1;
		}
		else if (validator2 != null) {
			return validator2;
		}
		else {
			return Validator.okValidator();
		}
	}

}
