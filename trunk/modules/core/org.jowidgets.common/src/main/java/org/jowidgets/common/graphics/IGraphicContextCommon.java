/*
 * Copyright (c) 2013, grossmann
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

package org.jowidgets.common.graphics;

import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.text.IFontMetrics;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.types.Rectangle;

public interface IGraphicContextCommon {

	void setAntiAliasing(AntiAliasing antiAliasing);

	void setTextAntiAliasing(AntiAliasing antiAliasing);

	void setLineWidth(int width);

	void setDashedLine(float[] pattern, float offset);

	void setLineCap(LineCap lineCap);

	void setLineJoin(LineJoin lineJoin);

	void setFontSize(int size);

	void setFontName(String fontName);

	void setTextMarkup(Markup markup);

	void setForegroundColor(IColorConstant color);

	void setBackgroundColor(IColorConstant color);

	void clearRectangle(int x, int y, int width, int height);

	void drawPoint(int x, int y);

	void drawLine(int x1, int y1, int x2, int y2);

	void drawRectangle(int x, int y, int width, int height);

	void drawPolygon(Point[] points);

	void drawPolyline(Point[] points);

	void drawOval(int x, int y, int width, int height);

	void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);

	void fillRectangle(int x, int y, int width, int height);

	void fillPolygon(Point[] points);

	void fillOval(int x, int y, int width, int height);

	void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle);

	void drawText(String text, int x, int y);

	void copyArea(
		final int sourceX,
		final int sourceY,
		final int sourceWidth,
		final int sourceHeight,
		final int destinationX,
		final int destinationY);

	void drawImage(
		IImageConstant image,
		final int sourceX,
		final int sourceY,
		final int sourceWidth,
		final int sourceHeight,
		final int destinationX,
		final int destinationY,
		final int destinationWidth,
		final int destinationHeight);

	void drawImage(final IImageConstant image, final int x, final int y);

	Rectangle getBounds();

	IColorConstant getForegroundColor();

	IColorConstant getBackgroundColor();

	IFontMetrics getFontMetrics();

	int getTextWidth(String text);

}
