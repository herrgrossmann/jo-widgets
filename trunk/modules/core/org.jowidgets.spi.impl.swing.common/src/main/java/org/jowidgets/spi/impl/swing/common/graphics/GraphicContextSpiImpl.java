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

package org.jowidgets.spi.impl.swing.common.graphics;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.graphics.AntiAliasing;
import org.jowidgets.common.graphics.LineCap;
import org.jowidgets.common.graphics.LineJoin;
import org.jowidgets.common.graphics.Point;
import org.jowidgets.common.image.IImageConstant;
import org.jowidgets.common.text.IFontMetrics;
import org.jowidgets.common.types.Markup;
import org.jowidgets.common.types.Rectangle;
import org.jowidgets.spi.graphics.IGraphicContextSpi;
import org.jowidgets.spi.impl.swing.common.image.SwingImageRegistry;
import org.jowidgets.spi.impl.swing.common.util.ColorConvert;
import org.jowidgets.spi.impl.swing.common.util.FontMetricsConvert;
import org.jowidgets.spi.impl.swing.common.util.FontProvider;
import org.jowidgets.util.Assert;

public final class GraphicContextSpiImpl implements IGraphicContextSpi {

	private final Graphics2D graphics;
	private final Rectangle bounds;

	private int lineWidth;
	private int lineCap;
	private int lineJoin;
	private float[] dashPattern;
	private float dashPatternOffset;

	public GraphicContextSpiImpl(final Graphics2D graphics, final Rectangle bounds) {
		Assert.paramNotNull(graphics, "graphics");
		Assert.paramNotNull(bounds, "bounds");
		this.graphics = graphics;
		this.bounds = bounds;

		this.lineWidth = 1;
		this.lineCap = BasicStroke.CAP_SQUARE;
		this.lineJoin = BasicStroke.JOIN_MITER;

		this.dashPattern = null;
		this.dashPatternOffset = 0.0f;

		setStroke();
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void setAntiAliasing(final AntiAliasing antiAliasing) {
		Assert.paramNotNull(antiAliasing, "antiAliasing");
		if (AntiAliasing.ON.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		else if (AntiAliasing.OFF.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		else if (AntiAliasing.DEFAULT.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		}
		else {
			throw new IllegalArgumentException("AntiAliasing '" + antiAliasing + "' is not known.");
		}
	}

	@Override
	public void setTextAntiAliasing(final AntiAliasing antiAliasing) {
		Assert.paramNotNull(antiAliasing, "antiAliasing");
		if (AntiAliasing.ON.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		else if (AntiAliasing.OFF.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
		else if (AntiAliasing.DEFAULT.equals(antiAliasing)) {
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		}
		else {
			throw new IllegalArgumentException("AntiAliasing '" + antiAliasing + "' is not known.");
		}
	}

	@Override
	public void setLineWidth(final int width) {
		this.lineWidth = width;
		setStroke();
	}

	@Override
	public void setDashedLine(final float[] pattern, final float offset) {
		this.dashPattern = pattern;
		this.dashPatternOffset = offset;
		setStroke();
	}

	@Override
	public void setLineCap(final LineCap lineCap) {
		Assert.paramNotNull(lineCap, "lineCap");
		if (LineCap.FLAT.equals(lineCap)) {
			this.lineCap = BasicStroke.CAP_BUTT;
		}
		else if (LineCap.ROUND.equals(lineCap)) {
			this.lineCap = BasicStroke.CAP_ROUND;
		}
		else if (LineCap.SQUARE.equals(lineCap)) {
			this.lineCap = BasicStroke.CAP_SQUARE;
		}
		else {
			throw new IllegalArgumentException("LineCap '" + lineCap + "' is not known.");
		}
		setStroke();
	}

	@Override
	public void setLineJoin(final LineJoin lineJoin) {
		Assert.paramNotNull(lineJoin, "lineJoin");
		if (LineJoin.BEVEL.equals(lineJoin)) {
			this.lineJoin = BasicStroke.JOIN_BEVEL;
		}
		else if (LineJoin.MITER.equals(lineJoin)) {
			this.lineJoin = BasicStroke.JOIN_MITER;
		}
		else if (LineJoin.ROUND.equals(lineJoin)) {
			this.lineJoin = BasicStroke.JOIN_ROUND;
		}
		else {
			throw new IllegalArgumentException("LineJoin '" + lineJoin + "' is not known.");
		}
		setStroke();
	}

	private void setStroke() {
		graphics.setStroke(new BasicStroke(lineWidth, lineCap, lineJoin, 10.0f, dashPattern, dashPatternOffset));
	}

	@Override
	public void setTextMarkup(final Markup markup) {
		graphics.setFont(FontProvider.deriveFont(graphics.getFont(), markup));
	}

	@Override
	public void setFontSize(final int size) {
		graphics.setFont(FontProvider.deriveFont(graphics.getFont(), size));
	}

	@Override
	public void setFontName(final String fontName) {
		graphics.setFont(FontProvider.deriveFont(graphics.getFont(), fontName));
	}

	@Override
	public void setForegroundColor(final IColorConstant color) {
		graphics.setColor(ColorConvert.convert(color));
	}

	@Override
	public void setBackgroundColor(final IColorConstant color) {
		graphics.setBackground(ColorConvert.convert(color));
	}

	@Override
	public void clearRectangle(final int x, final int y, final int width, final int height) {
		graphics.clearRect(x, y, width, height);
	}

	@Override
	public void drawPoint(final int x, final int y) {
		final Stroke currentStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(1));
		graphics.drawLine(x, y, x, y);
		graphics.setStroke(currentStroke);
	}

	@Override
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		graphics.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void drawRectangle(final int x, final int y, final int width, final int height) {
		graphics.drawRect(x, y, width, height);
	}

	@Override
	public void drawPolygon(final Point[] points) {
		graphics.drawPolygon(getPolygon(points));
	}

	@Override
	public void drawPolyline(final Point[] points) {
		final Polygon polygon = getPolygon(points);
		graphics.drawPolyline(polygon.xpoints, polygon.ypoints, polygon.npoints);
	}

	@Override
	public void drawOval(final int x, final int y, final int width, final int height) {
		graphics.drawOval(x, y, width, height);
	}

	@Override
	public void drawArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
		graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void fillRectangle(final int x, final int y, final int width, final int height) {
		graphics.fillRect(x, y, width, height);
	}

	@Override
	public void fillPolygon(final Point[] points) {
		graphics.fillPolygon(getPolygon(points));
	}

	@Override
	public void fillOval(final int x, final int y, final int width, final int height) {
		graphics.fillOval(x, y, width, height);
	}

	@Override
	public void fillArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
		graphics.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void drawText(final String text, final int x, final int y) {
		final FontMetrics fontMetrics = graphics.getFontMetrics();
		final int offset = fontMetrics.getAscent() + fontMetrics.getLeading() + 1;
		graphics.drawString(text, x + 1, y + offset);
	}

	@Override
	public void copyArea(
		final int sourceX,
		final int sourceY,
		final int sourceWidth,
		final int sourceHeight,
		final int destinationX,
		final int destinationY) {

		final int dx = destinationX - sourceX;
		final int dy = destinationY - sourceY;

		graphics.copyArea(sourceX, sourceY, sourceWidth, sourceHeight, dx, dy);
	}

	@Override
	public void drawImage(
		final IImageConstant imageKey,
		final int sourceX,
		final int sourceY,
		final int sourceWidth,
		final int sourceHeight,
		final int destinationX,
		final int destinationY,
		final int destinationWidth,
		final int destinationHeight) {

		final Image image = SwingImageRegistry.getInstance().getImage(imageKey);

		final int dx1 = destinationX;
		final int dy1 = destinationY;
		final int dx2 = dx1 + destinationWidth;
		final int dy2 = dy1 + destinationHeight;

		final int sx1 = sourceX;
		final int sy1 = sourceY;
		final int sx2 = sx1 + sourceWidth;
		final int sy2 = sy1 + sourceHeight;

		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	@Override
	public void drawImage(final IImageConstant imageKey, final int x, final int y) {
		final Image image = SwingImageRegistry.getInstance().getImage(imageKey);
		graphics.drawImage(image, x, y, null);
	}

	@Override
	public IColorConstant getForegroundColor() {
		return ColorConvert.convert(graphics.getColor());
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return ColorConvert.convert(graphics.getBackground());
	}

	@Override
	public IFontMetrics getFontMetrics() {
		return FontMetricsConvert.convert(graphics.getFontMetrics());
	}

	@Override
	public int getTextWidth(final String text) {
		if (text != null) {
			final char[] charArray = text.toCharArray();
			return graphics.getFontMetrics().charsWidth(charArray, 0, charArray.length);
		}
		else {
			return 0;
		}
	}

	private static Polygon getPolygon(final Point[] points) {
		Assert.paramNotNull(points, "points");
		final int[] xPoints = new int[points.length];
		final int[] yPoints = new int[points.length];
		int index = 0;
		for (final Point point : points) {
			xPoints[index] = point.getX();
			yPoints[index] = point.getY();
			index++;
		}
		return new Polygon(xPoints, yPoints, points.length);
	}

}
