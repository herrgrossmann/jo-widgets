/*
 * Copyright (c) 2011, Nikolaus Moll
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
package org.jowidgets.impl.convert;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import org.jowidgets.api.convert.IConverter;
import org.jowidgets.tools.converter.AbstractConverter;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.IValidator;
import org.jowidgets.validation.ValidationResult;

abstract class AbstractFloatingPointNumberConverter<NUMBER_TYPE extends Number> extends AbstractConverter<NUMBER_TYPE> implements
        IConverter<NUMBER_TYPE> {

    private final DecimalFormat decimalFormat;
    private final String formatHint;

    AbstractFloatingPointNumberConverter(final DecimalFormat decimalFormat, final String formatHint) {
        this.decimalFormat = decimalFormat;
        this.formatHint = formatHint;
    }

    abstract NUMBER_TYPE convert(Number number);

    @Override
    public NUMBER_TYPE convertToObject(final String string) {
        try {
            final ParsePosition pos = new ParsePosition(0);
            final Number result = decimalFormat.parse(string, pos);
            if (result == null || pos.getIndex() < string.length()) {
                return null;
            }
            return convert(result);
        }
        catch (final NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String convertToString(final NUMBER_TYPE value) {
        if (value != null) {
            return decimalFormat.format(value);
        }
        return null;
    }

    @Override
    public IValidator<String> getStringValidator() {
        return new IValidator<String>() {
            @Override
            public IValidationResult validate(final String input) {
                if (input != null && !input.trim().isEmpty()) {
                    final ParsePosition pos = new ParsePosition(0);
                    decimalFormat.parse(input, pos);
                    if (pos.getIndex() < input.length()) {
                        if (formatHint != null) {
                            return ValidationResult.error(formatHint);
                        }
                        else {
                            //TODO i18n
                            return ValidationResult.error("Is not a valid decimal");
                        }
                    }
                }
                return ValidationResult.ok();
            }
        };
    }

    @Override
    public String getAcceptingRegExp() {
        final String decimalSeparatorRegEx;
        final String groupingSeparatorRegEx;
        int groupingSizeRegEx;
        final int groupingSizeRegExMinusOne;

        // Check, if the decimal separator is a special Char, add \\ to the char
        if (isSpecialChar(decimalFormat.getDecimalFormatSymbols().getDecimalSeparator())) {
            decimalSeparatorRegEx = "\\" + decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
        }
        else {
            decimalSeparatorRegEx = String.valueOf(decimalFormat.getDecimalFormatSymbols().getDecimalSeparator());
        }

        // Check, if the grouping separator is a special Char, add \\ to the char
        if (isSpecialChar(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator())) {
            groupingSeparatorRegEx = "\\" + decimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
        }
        else {
            groupingSeparatorRegEx = String.valueOf(decimalFormat.getDecimalFormatSymbols().getGroupingSeparator());
        }

        // Set the grouping size for the regular expression, if it is set in the decimal Format
        if (decimalFormat.isGroupingUsed()) {
            groupingSizeRegEx = decimalFormat.getGroupingSize();
        }
        else {
            groupingSizeRegEx = 3;
        }

        // Set the grouping size minus one. Is needed for the regular expression
        groupingSizeRegExMinusOne = groupingSizeRegEx - 1;

        // Accept double numbers and a grouping separator if it is set
        return "-?(([0-9]?[0-9]"
            + groupingSeparatorRegEx
            + "?)?"
            + "([0-9]{"
            + groupingSizeRegEx
            + "}"
            + groupingSeparatorRegEx
            + "?)*"
            + "((([0-9]{0,"
            + groupingSizeRegExMinusOne
            + "})?)|"
            + "([0-9]{"
            + groupingSizeRegEx
            + "})?((?<=[0-9])"
            + decimalSeparatorRegEx
            + "[0-9]*)))";

    }

    private boolean isSpecialChar(final char c) {
        // 160 = ' ' (france) ( alt 255) , 46 = '.' 
        if (c == 160 | c == 46) {
            return true;
        }
        return false;
    }
}
