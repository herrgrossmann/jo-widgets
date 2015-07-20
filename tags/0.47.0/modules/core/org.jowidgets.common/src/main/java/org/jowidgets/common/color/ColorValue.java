/*
 * Copyright (c) 2010, Manuel Woelker
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
package org.jowidgets.common.color;

import java.io.Serializable;

import org.jowidgets.util.cache.Cacheable;

/**
 * Default implementation for IColorConstant
 */
public final class ColorValue extends Cacheable implements IColorConstant, Serializable {

    private static final long serialVersionUID = 6862989508930470810L;

    private final short red;
    private final short green;
    private final short blue;

    /**
     * Creates a new ColorValue object
     * 
     * @param red The red component
     * @param green The green component
     * @param blue The blue component
     */
    public ColorValue(final int red, final int green, final int blue) {
        this.red = shorten(red);
        this.green = shorten(green);
        this.blue = shorten(blue);
    }

    private static short shorten(final int value) {
        if (value > 255) {
            throw new IllegalArgumentException("Value " + value + "outside of range [0,255]");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Value " + value + "outside of range [0,255]");
        }
        return (short) value;
    }

    /**
     * Gets the red component
     * 
     * @return The red component
     */
    public int getRed() {
        return red;
    }

    /**
     * Gets the green component
     * 
     * @return The green component
     */
    public int getGreen() {
        return green;
    }

    /**
     * Gets the blue component
     * 
     * @return The blue component
     */
    public int getBlue() {
        return blue;
    }

    @Override
    public ColorValue getDefaultValue() {
        return this;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += red;
        result += green << 8;
        result += blue << 16;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColorValue other = (ColorValue) obj;
        if (blue != other.blue) {
            return false;
        }
        if (green != other.green) {
            return false;
        }
        if (red != other.red) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ColorValue [red=" + red + ", green=" + green + ", blue=" + blue + "]";
    }

}
