/*
 * Copyright (c) 2014, Michael Grossmann
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
package org.jowidgets.validation.tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.util.Assert;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidationResultBuilder;
import org.jowidgets.validation.IValidator;
import org.jowidgets.validation.ValidationResult;

public final class CompoundValidator<VALIDATION_INPUT_TYPE> implements IValidator<VALIDATION_INPUT_TYPE>, Serializable {

	private static final long serialVersionUID = -6857961928993086297L;

	private final List<IValidator<VALIDATION_INPUT_TYPE>> validators;

	@SuppressWarnings("unchecked")
	public CompoundValidator() {
		this(new IValidator[] {});
	}

	public CompoundValidator(final IValidator<VALIDATION_INPUT_TYPE>... validators) {
		Assert.paramNotNull(validators, "validators");
		this.validators = new LinkedList<IValidator<VALIDATION_INPUT_TYPE>>(Arrays.asList(validators));
	}

	@Override
	public IValidationResult validate(final VALIDATION_INPUT_TYPE validationInput) {
		final IValidationResultBuilder builder = ValidationResult.builder();
		for (final IValidator<VALIDATION_INPUT_TYPE> validator : validators) {
			final IValidationResult subResult = validator.validate(validationInput);
			builder.addResult(subResult);
		}
		return builder.build();
	}

	public void addValidator(final IValidator<VALIDATION_INPUT_TYPE> validator) {
		Assert.paramNotNull(validator, "validator");
		this.validators.add(validator);
	}

	public void removeValidator(final IValidator<VALIDATION_INPUT_TYPE> validator) {
		Assert.paramNotNull(validator, "validator");
		if (validators.contains(validator)) {
			validators.remove(validator);
		}
		else {
			throw new IllegalArgumentException("Validator '" + validator + "' is not member of this compound validator");
		}
	}

}
