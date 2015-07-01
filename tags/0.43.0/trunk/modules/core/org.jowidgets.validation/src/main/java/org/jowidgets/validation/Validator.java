/*
 * Copyright (c) 2011, grossmann
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

import java.io.Serializable;

public final class Validator {

    @SuppressWarnings("rawtypes")
    private static final IValidator OK_VALIDATOR = createOkValidator();

    private Validator() {}

    @SuppressWarnings("rawtypes")
    private static IValidator createOkValidator() {
        return new OkValidatorImpl();
    }

    @SuppressWarnings("unchecked")
    public static <VALUE_TYPE> IValidator<VALUE_TYPE> okValidator() {
        return OK_VALIDATOR;
    }

    private static final class OkValidatorImpl<VALUE_TYPE> implements IValidator<VALUE_TYPE>, Serializable {

        private static final long serialVersionUID = -654830472836975532L;

        private final String className = OkValidatorImpl.class.getName();

        @Override
        public IValidationResult validate(final VALUE_TYPE value) {
            return ValidationResult.ok();
        }

        @Override
        public String toString() {
            return OkValidatorImpl.class.getName();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((className == null) ? 0 : className.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof IValidator)) {
                return false;
            }
            final IValidator<?> other = (IValidator<?>) obj;
            if (className == null) {
                if (other.getClass().getName() != null) {
                    return false;
                }
            }
            else if (!className.equals(other.getClass().getName())) {
                return false;
            }
            return true;
        }

    }
}
