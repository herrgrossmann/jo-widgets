/*
 * Copyright (c) 2004, Mikael Grev, MiG InfoCom AB. (miglayout (at) miginfocom (dot) com), 
 * modifications by Nikolaus Moll
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * Neither the name of the MiG InfoCom AB nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 *
 */
package org.jowidgets.impl.layout.miglayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Parses string constraints.
 */
final class ConstraintParserCommon {
	private ConstraintParserCommon() {}

	/**
	 * Parses the layout constraints and stores the parsed values in the transient (cache) member varables.
	 * 
	 * @param s The String to parse. Should not be <code>null</code> and <b>must be lower case and trimmed</b>.
	 * @throws RuntimeException if the constaint was not valid.
	 * @return The parsed constraint. Never <code>null</code>.
	 */
	public static LCCommon parseLayoutConstraint(final String s) {
		final LCCommon lc = new LCCommon();
		if (s.length() == 0) {
			return lc;
		}

		final String[] parts = toTrimmedTokens(s, ',');

		// First check for "ltr" or "rtl" since that will affect the interpretation of the other constraints.
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part == null) {
				continue;
			}

			final int len = part.length();
			if (len == 3 || len == 11) { // Optimization
				if (part.equals("ltr") || part.equals("rtl") || part.equals("lefttoright") || part.equals("righttoleft")) {
					lc.setLeftToRight(part.charAt(0) == 'l' ? Boolean.TRUE : Boolean.FALSE);
					parts[i] = null; // So we will not try to interpret it again
				}

				if (part.equals("ttb") || part.equals("btt") || part.equals("toptobottom") || part.equals("bottomtotop")) {
					lc.setTopToBottom(part.charAt(0) == 't');
					parts[i] = null; // So we will not try to interpret it again
				}
			}
		}

		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			if (part == null || part.length() == 0) {
				continue;
			}

			try {
				int ix = -1;
				final char c = part.charAt(0);

				if (c == 'w' || c == 'h') {

					ix = startsWithLenient(part, "wrap", -1, true);
					if (ix > -1) {
						final String num = part.substring(ix).trim();
						lc.setWrapAfter(num.length() != 0 ? Integer.parseInt(num) : 0);
						continue;
					}

					final boolean isHor = c == 'w';
					if (isHor && (part.startsWith("w ") || part.startsWith("width "))) {
						final String sz = part.substring(part.charAt(1) == ' ' ? 2 : 6).trim();
						lc.setWidth(parseBoundSize(sz, false, true));
						continue;
					}

					if (!isHor && (part.startsWith("h ") || part.startsWith("height "))) {
						final String uvStr = part.substring(part.charAt(1) == ' ' ? 2 : 7).trim();
						lc.setHeight(parseBoundSize(uvStr, false, false));
						continue;
					}

					if (part.length() > 5) {
						final String sz = part.substring(5).trim();
						if (part.startsWith("wmin ")) {
							lc.minWidth(sz);
							continue;
						}
						else if (part.startsWith("wmax ")) {
							lc.maxWidth(sz);
							continue;
						}
						else if (part.startsWith("hmin ")) {
							lc.minHeight(sz);
							continue;
						}
						else if (part.startsWith("hmax ")) {
							lc.maxHeight(sz);
							continue;
						}
					}

					if (part.startsWith("hidemode ")) {
						lc.setHideMode(Integer.parseInt(part.substring(9)));
						continue;
					}
				}

				if (c == 'g') {
					if (part.startsWith("gapx ")) {
						lc.setGridGapX(parseBoundSize(part.substring(5).trim(), true, true));
						continue;
					}

					if (part.startsWith("gapy ")) {
						lc.setGridGapY(parseBoundSize(part.substring(5).trim(), true, false));
						continue;
					}

					if (part.startsWith("gap ")) {
						final String[] gaps = toTrimmedTokens(part.substring(4).trim(), ' ');
						lc.setGridGapX(parseBoundSize(gaps[0], true, true));
						lc.setGridGapY(gaps.length > 1 ? parseBoundSize(gaps[1], true, false) : lc.getGridGapX());
						continue;
					}
				}

				if (c == 'd') {
					ix = startsWithLenient(part, "debug", 5, true);
					if (ix > -1) {
						final String millis = part.substring(ix).trim();
						lc.setDebugMillis(millis.length() > 0 ? Integer.parseInt(millis) : 1000);
						continue;
					}
				}

				if (c == 'n') {
					if (part.equals("nogrid")) {
						lc.setNoGrid(true);
						continue;
					}

					if (part.equals("nocache")) {
						lc.setNoCache(true);
						continue;
					}

					if (part.equals("novisualpadding")) {
						lc.setVisualPadding(false);
						continue;
					}
				}

				if (c == 'f') {
					if (part.equals("fill") || part.equals("fillx") || part.equals("filly")) {
						lc.setFillX(part.length() == 4 || part.charAt(4) == 'x');
						lc.setFillY(part.length() == 4 || part.charAt(4) == 'y');
						continue;
					}

					if (part.equals("flowy")) {
						lc.setFlowX(false);
						continue;
					}

					if (part.equals("flowx")) {
						lc.setFlowX(true); // This is the default but added for consistency
						continue;
					}
				}

				if (c == 'i') {
					ix = startsWithLenient(part, "insets", 3, true);
					if (ix > -1) {
						final String insStr = part.substring(ix).trim();
						final UnitValueCommon[] ins = parseInsets(insStr, true);
						MigLayoutToolkitImpl.getMigLayoutUtil().putCCString(ins, insStr);
						lc.setInsets(ins);
						continue;
					}
				}

				if (c == 'a') {
					ix = startsWithLenient(part, new String[] {"aligny", "ay"}, new int[] {6, 2}, true);
					if (ix > -1) {
						final UnitValueCommon align = parseUnitValueOrAlign(part.substring(ix).trim(), false, null);
						if (align == MigLayoutToolkitImpl.getMigUnitValueToolkit().BASELINE_IDENTITY) {
							throw new IllegalArgumentException("'baseline' can not be used to align the whole component group.");
						}
						lc.setAlignY(align);
						continue;
					}

					ix = startsWithLenient(part, new String[] {"alignx", "ax"}, new int[] {6, 2}, true);
					if (ix > -1) {
						lc.setAlignX(parseUnitValueOrAlign(part.substring(ix).trim(), true, null));
						continue;
					}

					ix = startsWithLenient(part, "align", 2, true);
					if (ix > -1) {
						final String[] gaps = toTrimmedTokens(part.substring(ix).trim(), ' ');
						lc.setAlignX(parseUnitValueOrAlign(gaps[0], true, null));
						lc.setAlignY(gaps.length > 1 ? parseUnitValueOrAlign(gaps[1], false, null) : lc.getAlignX());
						continue;
					}
				}

				if (c == 'p') {
					if (part.startsWith("packalign ")) {
						final String[] packs = toTrimmedTokens(part.substring(10).trim(), ' ');
						lc.setPackWidthAlign(packs[0].length() > 0 ? Float.parseFloat(packs[0]) : 0.5f);
						if (packs.length > 1) {
							lc.setPackHeightAlign(Float.parseFloat(packs[1]));
						}
						continue;
					}

					if (part.startsWith("pack ") || part.equals("pack")) {
						final String ps = part.substring(4).trim();
						final String[] packs = toTrimmedTokens(ps.length() > 0 ? ps : "pref pref", ' ');
						lc.setPackWidth(parseBoundSize(packs[0], false, true));
						if (packs.length > 1) {
							lc.setPackHeight(parseBoundSize(packs[1], false, false));
						}

						continue;
					}
				}

				if (lc.getAlignX() == null) {
					final UnitValueCommon alignX = parseAlignKeywords(part, true);
					if (alignX != null) {
						lc.setAlignX(alignX);
						continue;
					}
				}

				final UnitValueCommon alignY = parseAlignKeywords(part, false);
				if (alignY != null) {
					lc.setAlignY(alignY);
					continue;
				}

				throw new IllegalArgumentException("Unknown Constraint: '" + part + "'\n");

			}
			catch (final Exception ex) {
				throw new IllegalArgumentException("Illegal Constraint: '" + part + "'\n" + ex.getMessage());
			}
		}

		//		lc = (LC) serializeTest(lc);

		return lc;
	}

	/**
	 * Parses the column or rows constraints. They normally looks something like <code>"[min:pref]rel[10px][]"</code>.
	 * 
	 * @param s The string to parse. Not <code>null</code>.
	 * @return An array of {@link DimConstraintCommon}s that is as manu are there exist "[...]" sections in the string that is
	 *         parsed.
	 * @throws RuntimeException if the constaint was not valid.
	 */
	public static ACCommon parseRowConstraints(final String s) {
		return parseAxisConstraint(s, false);
	}

	/**
	 * Parses the column or rows constraints. They normally looks something like <code>"[min:pref]rel[10px][]"</code>.
	 * 
	 * @param s The string to parse. Not <code>null</code>.
	 * @return An array of {@link DimConstraintCommon}s that is as manu are there exist "[...]" sections in the string that is
	 *         parsed.
	 * @throws RuntimeException if the constaint was not valid.
	 */
	public static ACCommon parseColumnConstraints(final String s) {
		return parseAxisConstraint(s, true);
	}

	/**
	 * Parses the column or rows constraints. They normally looks something like <code>"[min:pref]rel[10px][]"</code>.
	 * 
	 * @param s The string to parse. Not <code>null</code>.
	 * @param isCols If this for columns rather than rows.
	 * @return An array of {@link DimConstraintCommon}s that is as manu are there exist "[...]" sections in the string that is
	 *         parsed.
	 * @throws RuntimeException if the constaint was not valid.
	 */
	private static ACCommon parseAxisConstraint(String s, final boolean isCols) {
		s = s.trim();

		if (s.length() == 0) {
			return new ACCommon(); // Short circuit for performance.
		}

		s = s.toLowerCase();

		final ArrayList<String> parts = getRowColAndGapsTrimmed(s);

		final BoundSizeCommon[] gaps = new BoundSizeCommon[(parts.size() >> 1) + 1];
		final int iSz = parts.size();
		int gIx = 0;
		for (int i = 0; i < iSz; i += 2, gIx++) {
			gaps[gIx] = parseBoundSize(parts.get(i), true, isCols);
		}

		final DimConstraintCommon[] colSpecs = new DimConstraintCommon[parts.size() >> 1];
		gIx = 0;
		for (int i = 0; i < colSpecs.length; i++, gIx++) {
			if (gIx >= gaps.length - 1) {
				gIx = gaps.length - 2;
			}

			colSpecs[i] = parseDimConstraint(parts.get((i << 1) + 1), gaps[gIx], gaps[gIx + 1], isCols);
		}

		final ACCommon ac = new ACCommon();
		ac.setConstaints(colSpecs);

		//		ac = (AC) serializeTest(ac);

		return ac;
	}

	/**
	 * Parses a single column or row constraint.
	 * 
	 * @param s The single constraint to parse. May look something like <code>"min:pref,fill,grow"</code>. Should not be
	 *            <code>null</code> and <b>must
	 *            be lower case and trimmed</b>.
	 * @param gapBefore The default gap "before" the column/row constraint. Can be overridden with a <code>"gap"</code> section
	 *            within <code>s</code>.
	 * @param gapAfter The default gap "after" the column/row constraint. Can be overridden with a <code>"gap"</code> section
	 *            within <code>s</code>.
	 * @param isCols If the constraints are column constraints rather than row constraints.
	 * @return A single constraint. Never <code>null</code>.
	 * @throws RuntimeException if the constraint was not valid.
	 */
	private static DimConstraintCommon parseDimConstraint(
		final String s,
		final BoundSizeCommon gapBefore,
		final BoundSizeCommon gapAfter,
		final boolean isCols) {
		final DimConstraintCommon dimConstraint = new DimConstraintCommon();

		// Default values.
		dimConstraint.setGapBefore(gapBefore);
		dimConstraint.setGapAfter(gapAfter);

		final String[] parts = toTrimmedTokens(s, ',');
		for (int i = 0; i < parts.length; i++) {
			final String part = parts[i];
			try {
				if (part.length() == 0) {
					continue;
				}

				if (part.equals("fill")) {
					dimConstraint.setFill(true);
					//					 dimConstraint.setAlign(null);   // Can not have both fill and alignment (changed for 3.5 since it can have "growy 0")
					continue;
				}

				if (part.equals("nogrid")) {
					dimConstraint.setNoGrid(true);
					continue;
				}

				int ix = -1;
				final char c = part.charAt(0);

				if (c == 's') {
					ix = startsWithLenient(part, new String[] {"sizegroup", "sg"}, new int[] {5, 2}, true);
					if (ix > -1) {
						dimConstraint.setSizeGroup(part.substring(ix).trim());
						continue;
					}

					ix = startsWithLenient(part, new String[] {"shrinkprio", "shp"}, new int[] {10, 3}, true);
					if (ix > -1) {
						dimConstraint.setShrinkPriority(Integer.parseInt(part.substring(ix).trim()));
						continue;
					}

					ix = startsWithLenient(part, "shrink", 6, true);
					if (ix > -1) {
						dimConstraint.setShrink(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}
				}

				if (c == 'g') {
					ix = startsWithLenient(part, new String[] {"growpriority", "gp"}, new int[] {5, 2}, true);
					if (ix > -1) {
						dimConstraint.setGrowPriority(Integer.parseInt(part.substring(ix).trim()));
						continue;
					}

					ix = startsWithLenient(part, "grow", 4, true);
					if (ix > -1) {
						dimConstraint.setGrow(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}
				}

				if (c == 'a') {
					ix = startsWithLenient(part, "align", 2, true);
					if (ix > -1) {
						//						if (dimConstraint.isFill() == false)    // Swallow, but ignore if fill is set. (changed for 3.5 since it can have "growy 0")
						dimConstraint.setAlign(parseUnitValueOrAlign(part.substring(ix).trim(), isCols, null));
						continue;
					}
				}

				final UnitValueCommon align = parseAlignKeywords(part, isCols);
				if (align != null) {
					//					if (dimConstraint.isFill() == false)    // Swallow, but ignore if fill is set. (changed for 3.5 since it can have "growy 0")
					dimConstraint.setAlign(align);
					continue;
				}

				// Only min:pref:max still left that is ok
				dimConstraint.setSize(parseBoundSize(part, false, isCols));

			}
			catch (final Exception ex) {
				throw new IllegalArgumentException("Illegal contraint: '" + part + "'\n" + ex.getMessage());
			}
		}
		return dimConstraint;
	}

	/**
	 * Parses all component constraints and stores the parsed values in the transient (cache) member varables.
	 * 
	 * @param constrMap The constraints as <code>String</code>s. Strings <b>must be lower case and trimmed</b>
	 * @return The parsed constraints. Never <code>null</code>.
	 */
	public static Map<IComponentWrapperCommon, CCCommon> parseComponentConstraints(
		final Map<IComponentWrapperCommon, String> constrMap) {
		final HashMap<IComponentWrapperCommon, CCCommon> flowConstrMap = new HashMap<IComponentWrapperCommon, CCCommon>();

		for (final Iterator<Map.Entry<IComponentWrapperCommon, String>> it = constrMap.entrySet().iterator(); it.hasNext();) {
			final Map.Entry<IComponentWrapperCommon, String> entry = it.next();
			flowConstrMap.put(entry.getKey(), parseComponentConstraint(entry.getValue()));
		}

		return flowConstrMap;
	}

	/**
	 * Parses one component constraint and returns the parsed value.
	 * 
	 * @param s The string to parse. Should not be <code>null</code> and <b>must be lower case and trimmed</b>.
	 * @throws RuntimeException if the constaint was not valid.
	 * @return The parsed constraint. Never <code>null</code>.
	 */
	public static CCCommon parseComponentConstraint(final String s) {
		final CCCommon cc = new CCCommon();

		if (s.length() == 0) {
			return cc;
		}

		final String[] parts = toTrimmedTokens(s, ',');

		for (final String part : parts) {
			try {
				if (part.length() == 0) {
					continue;
				}

				int ix = -1;
				final char c = part.charAt(0);

				if (c == 'n') {
					if (part.equals("north")) {
						cc.setDockSide(0);
						continue;
					}

					if (part.equals("newline")) {
						cc.setNewline(true);
						continue;
					}

					if (part.startsWith("newline ")) {
						final String gapSz = part.substring(7).trim();
						cc.setNewlineGapSize(parseBoundSize(gapSz, true, true));
						continue;
					}
				}

				if (c == 'f' && (part.equals("flowy") || part.equals("flowx"))) {
					cc.setFlowX(part.charAt(4) == 'x' ? Boolean.TRUE : Boolean.FALSE);
					continue;
				}

				if (c == 's') {
					ix = startsWithLenient(part, "skip", 4, true);
					if (ix > -1) {
						final String num = part.substring(ix).trim();
						cc.setSkip(num.length() != 0 ? Integer.parseInt(num) : 1);
						continue;
					}

					ix = startsWithLenient(part, "split", 5, true);
					if (ix > -1) {
						final String split = part.substring(ix).trim();
						cc.setSplit(split.length() > 0 ? Integer.parseInt(split) : LayoutUtilCommon.INF);
						continue;
					}

					if (part.equals("south")) {
						cc.setDockSide(2);
						continue;
					}

					ix = startsWithLenient(part, new String[] {"spany", "sy"}, new int[] {5, 2}, true);
					if (ix > -1) {
						cc.setSpanY(parseSpan(part.substring(ix).trim()));
						continue;
					}

					ix = startsWithLenient(part, new String[] {"spanx", "sx"}, new int[] {5, 2}, true);
					if (ix > -1) {
						cc.setSpanX(parseSpan(part.substring(ix).trim()));
						continue;
					}

					ix = startsWithLenient(part, "span", 4, true);
					if (ix > -1) {
						final String[] spans = toTrimmedTokens(part.substring(ix).trim(), ' ');
						cc.setSpanX(spans[0].length() > 0 ? Integer.parseInt(spans[0]) : LayoutUtilCommon.INF);
						cc.setSpanY(spans.length > 1 ? Integer.parseInt(spans[1]) : 1);
						continue;
					}

					ix = startsWithLenient(part, "shrinkx", 7, true);
					if (ix > -1) {
						cc.getHorizontal().setShrink(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "shrinky", 7, true);
					if (ix > -1) {
						cc.getVertical().setShrink(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "shrink", 6, false);
					if (ix > -1) {
						final String[] shrinks = toTrimmedTokens(part.substring(ix).trim(), ' ');
						cc.getHorizontal().setShrink(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						if (shrinks.length > 1) {
							cc.getVertical().setShrink(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						}
						continue;
					}

					ix = startsWithLenient(part, new String[] {"shrinkprio", "shp"}, new int[] {10, 3}, true);
					if (ix > -1) {
						final String sp = part.substring(ix).trim();
						if (sp.startsWith("x") || sp.startsWith("y")) { // To gandle "gpx", "gpy", "shrinkpriorityx", shrinkpriorityy"
							(sp.startsWith("x") ? cc.getHorizontal() : cc.getVertical()).setShrinkPriority(Integer.parseInt(sp.substring(2)));
						}
						else {
							final String[] shrinks = toTrimmedTokens(sp, ' ');
							cc.getHorizontal().setShrinkPriority(Integer.parseInt(shrinks[0]));
							if (shrinks.length > 1) {
								cc.getVertical().setShrinkPriority(Integer.parseInt(shrinks[1]));
							}
						}
						continue;
					}

					ix = startsWithLenient(
							part,
							new String[] {"sizegroupx", "sizegroupy", "sgx", "sgy"},
							new int[] {9, 9, 2, 2},
							true);
					if (ix > -1) {
						final String sg = part.substring(ix).trim();
						final char lc = part.charAt(ix - 1);
						if (lc != 'y') {
							cc.getHorizontal().setSizeGroup(sg);
						}
						if (lc != 'x') {
							cc.getVertical().setSizeGroup(sg);
						}
						continue;
					}
				}

				if (c == 'g') {
					ix = startsWithLenient(part, "growx", 5, true);
					if (ix > -1) {
						cc.getHorizontal().setGrow(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "growy", 5, true);
					if (ix > -1) {
						cc.getVertical().setGrow(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "grow", 4, false);
					if (ix > -1) {
						final String[] grows = toTrimmedTokens(part.substring(ix).trim(), ' ');
						cc.getHorizontal().setGrow(parseFloat(grows[0], ResizeConstraintCommon.WEIGHT_100));
						cc.getVertical().setGrow(parseFloat(grows.length > 1 ? grows[1] : "", ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, new String[] {"growprio", "gp"}, new int[] {8, 2}, true);
					if (ix > -1) {
						final String gp = part.substring(ix).trim();
						final char c0 = gp.length() > 0 ? gp.charAt(0) : ' ';
						if (c0 == 'x' || c0 == 'y') { // To gandle "gpx", "gpy", "growpriorityx", growpriorityy"
							(c0 == 'x' ? cc.getHorizontal() : cc.getVertical()).setGrowPriority(Integer.parseInt(gp.substring(2)));
						}
						else {
							final String[] grows = toTrimmedTokens(gp, ' ');
							cc.getHorizontal().setGrowPriority(Integer.parseInt(grows[0]));
							if (grows.length > 1) {
								cc.getVertical().setGrowPriority(Integer.parseInt(grows[1]));
							}
						}
						continue;
					}

					if (part.startsWith("gap")) {
						final BoundSizeCommon[] gaps = parseGaps(part); // Changes order!!
						if (gaps[0] != null) {
							cc.getVertical().setGapBefore(gaps[0]);
						}
						if (gaps[1] != null) {
							cc.getHorizontal().setGapBefore(gaps[1]);
						}
						if (gaps[2] != null) {
							cc.getVertical().setGapAfter(gaps[2]);
						}
						if (gaps[3] != null) {
							cc.getHorizontal().setGapAfter(gaps[3]);
						}
						continue;
					}
				}

				if (c == 'a') {
					ix = startsWithLenient(part, new String[] {"aligny", "ay"}, new int[] {6, 2}, true);
					if (ix > -1) {
						cc.getVertical().setAlign(parseUnitValueOrAlign(part.substring(ix).trim(), false, null));
						continue;
					}

					ix = startsWithLenient(part, new String[] {"alignx", "ax"}, new int[] {6, 2}, true);
					if (ix > -1) {
						cc.getHorizontal().setAlign(parseUnitValueOrAlign(part.substring(ix).trim(), true, null));
						continue;
					}

					ix = startsWithLenient(part, "align", 2, true);
					if (ix > -1) {
						final String[] gaps = toTrimmedTokens(part.substring(ix).trim(), ' ');
						cc.getHorizontal().setAlign(parseUnitValueOrAlign(gaps[0], true, null));
						if (gaps.length > 1) {
							cc.getVertical().setAlign(parseUnitValueOrAlign(gaps[1], false, null));
						}
						continue;
					}
				}

				if ((c == 'x' || c == 'y') && part.length() > 2) {
					final char c2 = part.charAt(1);
					if (c2 == ' ' || (c2 == '2' && part.charAt(2) == ' ')) {
						if (cc.getPos() == null) {
							cc.setPos(new UnitValueCommon[4]);
						}
						else if (cc.isBoundsInGrid() == false) {
							throw new IllegalArgumentException("Cannot combine 'position' with 'x/y/x2/y2' keywords.");
						}

						final int edge = (c == 'x' ? 0 : 1) + (c2 == '2' ? 2 : 0);
						final UnitValueCommon[] pos = cc.getPos();
						pos[edge] = parseUnitValue(part.substring(2).trim(), null, c == 'x');
						cc.setPos(pos);
						cc.setBoundsInGrid(true);
						continue;
					}
				}

				if (c == 'c') {
					ix = startsWithLenient(part, "cell", 4, true);
					if (ix > -1) {
						final String[] grs = toTrimmedTokens(part.substring(ix).trim(), ' ');
						if (grs.length < 2) {
							throw new IllegalArgumentException("At least two integers must follow " + part);
						}
						cc.setCellX(Integer.parseInt(grs[0]));
						cc.setCellY(Integer.parseInt(grs[1]));
						if (grs.length > 2) {
							cc.setSpanX(Integer.parseInt(grs[2]));
						}
						if (grs.length > 3) {
							cc.setSpanY(Integer.parseInt(grs[3]));
						}
						continue;
					}
				}

				if (c == 'p') {
					ix = startsWithLenient(part, "pos", 3, true);
					if (ix > -1) {
						if (cc.getPos() != null && cc.isBoundsInGrid()) {
							throw new IllegalArgumentException("Can not combine 'pos' with 'x/y/x2/y2' keywords.");
						}

						final String[] pos = toTrimmedTokens(part.substring(ix).trim(), ' ');
						final UnitValueCommon[] bounds = new UnitValueCommon[4];
						for (int j = 0; j < pos.length; j++) {
							bounds[j] = parseUnitValue(pos[j], null, j % 2 == 0);
						}

						if (bounds[0] == null && bounds[2] == null || bounds[1] == null && bounds[3] == null) {
							throw new IllegalArgumentException("Both x and x2 or y and y2 can not be null!");
						}

						cc.setPos(bounds);
						cc.setBoundsInGrid(false);
						continue;
					}

					ix = startsWithLenient(part, "pad", 3, true);
					if (ix > -1) {
						final UnitValueCommon[] p = parseInsets(part.substring(ix).trim(), false);
						cc.setPadding(new UnitValueCommon[] {
								p[0], p.length > 1 ? p[1] : null, p.length > 2 ? p[2] : null, p.length > 3 ? p[3] : null});
						continue;
					}

					ix = startsWithLenient(part, "pushx", 5, true);
					if (ix > -1) {
						cc.setPushX(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "pushy", 5, true);
					if (ix > -1) {
						cc.setPushY(parseFloat(part.substring(ix).trim(), ResizeConstraintCommon.WEIGHT_100));
						continue;
					}

					ix = startsWithLenient(part, "push", 4, false);
					if (ix > -1) {
						final String[] pushs = toTrimmedTokens(part.substring(ix).trim(), ' ');
						cc.setPushX(parseFloat(pushs[0], ResizeConstraintCommon.WEIGHT_100));
						cc.setPushY(parseFloat(pushs.length > 1 ? pushs[1] : "", ResizeConstraintCommon.WEIGHT_100));
						continue;
					}
				}

				if (c == 't') {
					ix = startsWithLenient(part, "tag", 3, true);
					if (ix > -1) {
						cc.setTag(part.substring(ix).trim());
						continue;
					}
				}

				if (c == 'w' || c == 'h') {
					if (part.equals("wrap")) {
						cc.setWrap(true);
						continue;
					}

					if (part.startsWith("wrap ")) {
						final String gapSz = part.substring(5).trim();
						cc.setWrapGapSize(parseBoundSize(gapSz, true, true));
						continue;
					}

					final boolean isHor = c == 'w';
					if (isHor && (part.startsWith("w ") || part.startsWith("width "))) {
						final String uvStr = part.substring(part.charAt(1) == ' ' ? 2 : 6).trim();
						cc.getHorizontal().setSize(parseBoundSize(uvStr, false, true));
						continue;
					}

					if (!isHor && (part.startsWith("h ") || part.startsWith("height "))) {
						final String uvStr = part.substring(part.charAt(1) == ' ' ? 2 : 7).trim();
						cc.getVertical().setSize(parseBoundSize(uvStr, false, false));
						continue;
					}

					if (part.startsWith("wmin ")
						|| part.startsWith("wmax ")
						|| part.startsWith("hmin ")
						|| part.startsWith("hmax ")) {
						final String uvStr = part.substring(5).trim();
						if (uvStr.length() > 0) {
							final UnitValueCommon uv = parseUnitValue(uvStr, null, isHor);
							final boolean isMin = part.charAt(3) == 'n';
							final DimConstraintCommon dc = isHor ? cc.getHorizontal() : cc.getVertical();
							dc.setSize(new BoundSizeCommon(isMin ? uv : dc.getSize().getMin(), dc.getSize().getPreferred(), isMin
									? (dc.getSize().getMax()) : uv, uvStr));
							continue;
						}
					}

					if (part.equals("west")) {
						cc.setDockSide(1);
						continue;
					}

					if (part.startsWith("hidemode ")) {
						cc.setHideMode(Integer.parseInt(part.substring(9)));
						continue;
					}
				}

				if (c == 'i' && part.startsWith("id ")) {
					cc.setId(part.substring(3).trim());
					final int dIx = cc.getId().indexOf('.');
					if (dIx == 0 || dIx == cc.getId().length() - 1) {
						throw new IllegalArgumentException("Dot must not be first or last!");
					}

					continue;
				}

				if (c == 'e') {
					if (part.equals("east")) {
						cc.setDockSide(3);
						continue;
					}

					if (part.equals("external")) {
						cc.setExternal(true);
						continue;
					}

					ix = startsWithLenient(
							part,
							new String[] {"endgroupx", "endgroupy", "egx", "egy"},
							new int[] {-1, -1, -1, -1},
							true);
					if (ix > -1) {
						final String sg = part.substring(ix).trim();
						final char lc = part.charAt(ix - 1);
						final DimConstraintCommon dc = (lc == 'x' ? cc.getHorizontal() : cc.getVertical());
						dc.setEndGroup(sg);
						continue;
					}
				}

				if (c == 'd') {
					if (part.equals("dock north")) {
						cc.setDockSide(0);
						continue;
					}
					if (part.equals("dock west")) {
						cc.setDockSide(1);
						continue;
					}
					if (part.equals("dock south")) {
						cc.setDockSide(2);
						continue;
					}
					if (part.equals("dock east")) {
						cc.setDockSide(3);
						continue;
					}

					if (part.equals("dock center")) {
						cc.getHorizontal().setGrow(100f);
						cc.getVertical().setGrow(100f);
						cc.setPushX(100f);
						cc.setPushY(100f);
						continue;
					}
				}

				final UnitValueCommon horAlign = parseAlignKeywords(part, true);
				if (horAlign != null) {
					cc.getHorizontal().setAlign(horAlign);
					continue;
				}

				final UnitValueCommon verAlign = parseAlignKeywords(part, false);
				if (verAlign != null) {
					cc.getVertical().setAlign(verAlign);
					continue;
				}

				throw new IllegalArgumentException("Unknown keyword.");

			}
			catch (final Exception ex) {
				throw new IllegalArgumentException("Illegal Constraint: '" + part + "'\n" + ex.getMessage());
			}
		}

		//		cc = (CC) serializeTest(cc);

		return cc;
	}

	/**
	 * Parses insets which consists of 1-4 <code>UnitValue</code>s.
	 * 
	 * @param s The string to parse. E.g. "10 10 10 10" or "20". If less than 4 groups the last will be used for the missing.
	 * @param acceptPanel If "panel" and "dialog" should be accepted. They are used to access platform defaults.
	 * @return An array of length 4 with the parsed insets.
	 * @throws IllegalArgumentException if the parsing could not be done.
	 */
	public static UnitValueCommon[] parseInsets(final String s, final boolean acceptPanel) {
		if (s.length() == 0 || s.equals("dialog") || s.equals("panel")) {
			if (acceptPanel == false) {
				throw new IllegalAccessError("Insets now allowed: " + s + "\n");
			}

			final boolean isPanel = s.startsWith("p");
			final UnitValueCommon[] ins = new UnitValueCommon[4];
			for (int j = 0; j < 4; j++) {
				ins[j] = isPanel
						? MigLayoutToolkitImpl.getMigPlatformDefaults().getPanelInsets(j)
						: MigLayoutToolkitImpl.getMigPlatformDefaults().getDialogInsets(j);
			}

			return ins;
		}
		else {
			final String[] insS = toTrimmedTokens(s, ' ');
			final UnitValueCommon[] ins = new UnitValueCommon[4];
			for (int j = 0; j < 4; j++) {
				final UnitValueCommon insSz = parseUnitValue(
						insS[j < insS.length ? j : insS.length - 1],
						MigLayoutToolkitImpl.getMigUnitValueToolkit().ZERO,
						j % 2 == 1);
				ins[j] = insSz != null ? insSz : MigLayoutToolkitImpl.getMigPlatformDefaults().getPanelInsets(j);
			}
			return ins;
		}
	}

	/**
	 * Parses gaps.
	 * 
	 * @param s The string that contains gap information. Should start with "gap".
	 * @return The gaps as specified in <code>s</code>. Indexed: <code>[top,left,bottom,right][min,pref,max]</code> or
	 *         [before,after][min,pref,max] if <code>oneDim</code> is true.
	 */
	private static BoundSizeCommon[] parseGaps(String s) {
		final BoundSizeCommon[] ret = new BoundSizeCommon[4];

		int ix = startsWithLenient(s, "gaptop", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[0] = parseBoundSize(s, true, false);
			return ret;
		}

		ix = startsWithLenient(s, "gapleft", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[1] = parseBoundSize(s, true, true);
			return ret;
		}

		ix = startsWithLenient(s, "gapbottom", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[2] = parseBoundSize(s, true, false);
			return ret;
		}

		ix = startsWithLenient(s, "gapright", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[3] = parseBoundSize(s, true, true);
			return ret;
		}

		ix = startsWithLenient(s, "gapbefore", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[1] = parseBoundSize(s, true, true);
			return ret;
		}

		ix = startsWithLenient(s, "gapafter", -1, true);
		if (ix > -1) {
			s = s.substring(ix).trim();
			ret[3] = parseBoundSize(s, true, true);
			return ret;
		}

		ix = startsWithLenient(s, new String[] {"gapx", "gapy"}, null, true);
		if (ix > -1) {
			final boolean x = s.charAt(3) == 'x';
			final String[] gaps = toTrimmedTokens(s.substring(ix).trim(), ' ');
			ret[x ? 1 : 0] = parseBoundSize(gaps[0], true, x);
			if (gaps.length > 1) {
				ret[x ? 3 : 2] = parseBoundSize(gaps[1], true, !x);
			}
			return ret;
		}

		ix = startsWithLenient(s, "gap ", 1, true);
		if (ix > -1) {
			final String[] gaps = toTrimmedTokens(s.substring(ix).trim(), ' ');

			ret[1] = parseBoundSize(gaps[0], true, true); // left
			if (gaps.length > 1) {
				ret[3] = parseBoundSize(gaps[1], true, false); // right
				if (gaps.length > 2) {
					ret[0] = parseBoundSize(gaps[2], true, true); // top
					if (gaps.length > 3) {
						ret[2] = parseBoundSize(gaps[3], true, false); // bottom
					}
				}
			}
			return ret;
		}

		throw new IllegalArgumentException("Unknown Gap part: '" + s + "'");
	}

	private static int parseSpan(final String s) {
		return s.length() > 0 ? Integer.parseInt(s) : LayoutUtilCommon.INF;
	}

	private static Float parseFloat(final String s, final Float nullVal) {
		return s.length() > 0 ? new Float(Float.parseFloat(s)) : nullVal;
	}

	/**
	 * Parses a single "min:pref:max" value. May look something like <code>"10px:20lp:30%"</code> or <code>"pref!"</code>.
	 * 
	 * @param s The string to parse. Not <code>null</code>.
	 * @param isGap If this bound size is a gap (different empty string handling).
	 * @param isHor If the size is for the horizontal dimension.
	 * @return A bound size that may be <code>null</code> if the string was "null", "n" or <code>null</code>.
	 */
	public static BoundSizeCommon parseBoundSize(String s, final boolean isGap, final boolean isHor) {
		if (s.length() == 0 || s.equals("null") || s.equals("n")) {
			return null;
		}

		final String cs = s;
		boolean push = false;
		if (s.endsWith("push")) {
			push = true;
			final int l = s.length();
			s = s.substring(0, l - (s.endsWith(":push") ? 5 : 4));
			if (s.length() == 0) {
				return new BoundSizeCommon(null, null, null, true, cs);
			}
		}

		final String[] sizes = toTrimmedTokens(s, ':');
		String s0 = sizes[0];

		if (sizes.length == 1) {
			final boolean hasEM = s0.endsWith("!");
			if (hasEM) {
				s0 = s0.substring(0, s0.length() - 1);
			}
			final UnitValueCommon uv = parseUnitValue(s0, null, isHor);
			return new BoundSizeCommon(((isGap || hasEM) ? uv : null), uv, (hasEM ? uv : null), push, cs);

		}
		else if (sizes.length == 2) {
			return new BoundSizeCommon(parseUnitValue(s0, null, isHor), parseUnitValue(sizes[1], null, isHor), null, push, cs);
		}
		else if (sizes.length == 3) {
			return new BoundSizeCommon(parseUnitValue(s0, null, isHor), parseUnitValue(sizes[1], null, isHor), parseUnitValue(
					sizes[2],
					null,
					isHor), push, cs);
		}
		else {
			throw new IllegalArgumentException("Min:Preferred:Max size section must contain 0, 1 or 2 colons. '" + cs + "'");
		}
	}

	/**
	 * Parses a single unit value that may also be an alignment as parsed by {@link #parseAlignKeywords(String, boolean)}.
	 * 
	 * @param s The string to parse. Not <code>null</code>. May look something like <code>"10px"</code> or <code>"5dlu"</code>.
	 * @param isHor If the value is for the horizontal dimension.
	 * @param emptyReplacement A replacement if <code>s</code> is empty. May be <code>null</code>.
	 * @return The parsed unit value. May be <code>null</code>.
	 */
	public static UnitValueCommon parseUnitValueOrAlign(
		final String s,
		final boolean isHor,
		final UnitValueCommon emptyReplacement) {
		if (s.length() == 0) {
			return emptyReplacement;
		}

		final UnitValueCommon align = parseAlignKeywords(s, isHor);
		if (align != null) {
			return align;
		}

		return parseUnitValue(s, emptyReplacement, isHor);
	}

	/**
	 * Parses a single unit value. E.g. "10px" or "5in"
	 * 
	 * @param s The string to parse. Not <code>null</code>. May look something like <code>"10px"</code> or <code>"5dlu"</code>.
	 * @param isHor If the value is for the horizontal dimension.
	 * @return The parsed unit value. <code>null</code> is empty string,
	 */
	public static UnitValueCommon parseUnitValue(final String s, final boolean isHor) {
		return parseUnitValue(s, null, isHor);
	}

	/**
	 * Parses a single unit value.
	 * 
	 * @param s The string to parse. May be <code>null</code>. May look something like <code>"10px"</code> or <code>"5dlu"</code>.
	 * @param emptyReplacement A replacement <code>s</code> is empty or <code>null</code>. May be <code>null</code>.
	 * @param isHor If the value is for the horizontal dimension.
	 * @return The parsed unit value. May be <code>null</code>.
	 */
	private static UnitValueCommon parseUnitValue(String s, final UnitValueCommon emptyReplacement, final boolean isHor) {
		if (s == null || s.length() == 0) {
			return emptyReplacement;
		}

		final String cs = s; // Save creation string.
		final char c0 = s.charAt(0);

		// Remove start and end parentheses, if there.
		if (c0 == '(' && s.charAt(s.length() - 1) == ')') {
			s = s.substring(1, s.length() - 1);
		}

		if (c0 == 'n' && (s.equals("null") || s.equals("n"))) {
			return null;
		}

		if (c0 == 'i' && s.equals("inf")) {
			return MigLayoutToolkitImpl.getMigUnitValueToolkit().INF;
		}

		final int oper = getOper(s);
		final boolean inline = oper == UnitValueToolkitCommon.ADD
			|| oper == UnitValueToolkitCommon.SUB
			|| oper == UnitValueToolkitCommon.MUL
			|| oper == UnitValueToolkitCommon.DIV;

		if (oper != UnitValueToolkitCommon.STATIC) { // It is a multi-value

			String[] uvs;
			if (inline == false) { // If the format is of type "opr(xxx,yyy)" (compared to in-line "10%+15px")
				final String sub = s.substring(4, s.length() - 1).trim();
				uvs = toTrimmedTokens(sub, ',');
				if (uvs.length == 1) {
					return parseUnitValue(sub, null, isHor);
				}
			}
			else {
				char delim;
				if (oper == UnitValueToolkitCommon.ADD) {
					delim = '+';
				}
				else if (oper == UnitValueToolkitCommon.SUB) {
					delim = '-';
				}
				else if (oper == UnitValueToolkitCommon.MUL) {
					delim = '*';
				}
				else { // div left
					delim = '/';
				}
				uvs = toTrimmedTokens(s, delim);
				if (uvs.length > 2) { // More than one +-*/.
					final String last = uvs[uvs.length - 1];
					final String first = s.substring(0, s.length() - last.length() - 1);
					uvs = new String[] {first, last};
				}
			}

			if (uvs.length != 2) {
				throw new IllegalArgumentException("Malformed UnitValue: '" + s + "'");
			}

			final UnitValueCommon sub1 = parseUnitValue(uvs[0], null, isHor);
			final UnitValueCommon sub2 = parseUnitValue(uvs[1], null, isHor);

			if (sub1 == null || sub2 == null) {
				throw new IllegalArgumentException("Malformed UnitValue. Must be two sub-values: '" + s + "'");
			}

			return new UnitValueCommon(isHor, oper, sub1, sub2, cs);
		}
		else {
			try {
				final String[] numParts = getNumTextParts(s);
				final float value = numParts[0].length() > 0 ? Float.parseFloat(numParts[0]) : 1; // e.g. "related" has no number part..

				return new UnitValueCommon(value, numParts[1], isHor, oper, cs);

			}
			catch (final Exception e) {
				throw new IllegalArgumentException("Malformed UnitValue: '" + s + "'");
			}
		}
	}

	/**
	 * Parses alignment keywords and returns the appropriate <code>UnitValue</code>.
	 * 
	 * @param s The string to parse. Not <code>null</code>.
	 * @param isHor If alignments for horizontal is checked. <code>false</code> means vertical.
	 * @return The unit value or <code>null</code> if not recognized (no exception).
	 */
	static UnitValueCommon parseAlignKeywords(final String s, final boolean isHor) {
		if (startsWithLenient(s, "center", 1, false) != -1) {
			return MigLayoutToolkitImpl.getMigUnitValueToolkit().CENTER;
		}

		if (isHor) {
			if (startsWithLenient(s, "left", 1, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().LEFT;
			}

			if (startsWithLenient(s, "right", 1, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().RIGHT;
			}

			if (startsWithLenient(s, "leading", 4, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().LEADING;
			}

			if (startsWithLenient(s, "trailing", 5, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().TRAILING;
			}

			if (startsWithLenient(s, "label", 5, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().LABEL;
			}

		}
		else {

			if (startsWithLenient(s, "baseline", 4, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().BASELINE_IDENTITY;
			}

			if (startsWithLenient(s, "top", 1, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().TOP;
			}

			if (startsWithLenient(s, "bottom", 1, false) != -1) {
				return MigLayoutToolkitImpl.getMigUnitValueToolkit().BOTTOM;
			}
		}

		return null;
	}

	/**
	 * Splits a text-number combination such as "hello 10.0" into <code>{"hello", "10.0"}</code>.
	 * 
	 * @param s The string to split. Not <code>null</code>. Needs be be reasonably formatted since the method
	 *            only finds the first 0-9 or . and cuts the string in half there.
	 * @return Always length 2 and no <code>null</code> elements. Elements are "" if no part found.
	 */
	private static String[] getNumTextParts(final String s) {
		final int iSz = s.length();
		for (int i = 0; i < iSz; i++) {
			final char c = s.charAt(i);
			if (c == ' ') {
				throw new IllegalArgumentException("Space in UnitValue: '" + s + "'");
			}

			if ((c < '0' || c > '9') && c != '.' && c != '-') {
				return new String[] {s.substring(0, i).trim(), s.substring(i).trim()};
			}
		}
		return new String[] {s, ""};
	}

	/**
	 * Returns the operation depending on the start character.
	 * 
	 * @param s The string to check. Not <code>null</code>.
	 * @return E.g. UnitValue.ADD, UnitValue.SUB or UnitValue.STATIC. Returns negative value for in-line operations.
	 */
	private static int getOper(final String s) {
		final int len = s.length();
		if (len < 3) {
			return UnitValueToolkitCommon.STATIC;
		}

		if (len > 5 && s.charAt(3) == '(' && s.charAt(len - 1) == ')') {
			if (s.startsWith("min(")) {
				return UnitValueToolkitCommon.MIN;
			}

			if (s.startsWith("max(")) {
				return UnitValueToolkitCommon.MAX;
			}

			if (s.startsWith("mid(")) {
				return UnitValueToolkitCommon.MID;
			}
		}

		// Try in-line add/sub. E.g. "pref+10px".
		for (int j = 0; j < 2; j++) { // First +-   then */   (precedence)
			int p = 0;
			for (int i = len - 1; i > 0; i--) {
				final char c = s.charAt(i);
				if (c == ')') {
					p++;
				}
				else if (c == '(') {
					p--;
				}
				else if (p == 0) {
					if (j == 0) {
						if (c == '+') {
							return UnitValueToolkitCommon.ADD;
						}
						if (c == '-') {
							return UnitValueToolkitCommon.SUB;
						}
					}
					else {
						if (c == '*') {
							return UnitValueToolkitCommon.MUL;
						}
						if (c == '/') {
							return UnitValueToolkitCommon.DIV;
						}
					}
				}
			}
		}
		return UnitValueToolkitCommon.STATIC;
	}

	/**
	 * Returns if a string shares at least a specified numbers starting characters with a number of matches.
	 * <p>
	 * This method just exercises {@link #startsWithLenient(String, String, int, boolean)} with every one of <code>matches</code>
	 * and <code>minChars</code>.
	 * 
	 * @param s The string to check. Not <code>null</code>.
	 * @param matches A number of possible starts for <code>s</code>.
	 * @param minChars The minimum number of characters to match for every element in <code>matches</code>. Needs
	 *            to be of same length as <code>matches</code>. Can be <code>null</code>.
	 * @param acceptTrailing If after the required number of characters are matched on recognized characters that are not
	 *            in one of the the <code>matches</code> string should be accepted. For instance if "abczz" should be matched with
	 *            "abcdef" and min chars 3.
	 * @return The index of the first unmatched character if <code>minChars</code> was reached or <code>-1</code> if a match was
	 *         not
	 *         found.
	 */
	private static int startsWithLenient(
		final String s,
		final String[] matches,
		final int[] minChars,
		final boolean acceptTrailing) {
		for (int i = 0; i < matches.length; i++) {
			final int minChar = minChars != null ? minChars[i] : -1;
			final int ix = startsWithLenient(s, matches[i], minChar, acceptTrailing);
			if (ix > -1) {
				return ix;
			}
		}
		return -1;
	}

	/**
	 * Returns if a string shares at least a specified numbers starting characters with a match.
	 * 
	 * @param s The string to check. Not <code>null</code> and must be trimmed.
	 * @param match The possible start for <code>s</code>. Not <code>null</code> and must be trimmed.
	 * @param minChars The mimimum number of characters to match to <code>s</code> for it this to be considered a match. -1 means
	 *            the full length of <code>match</code>.
	 * @param acceptTrailing If after the required number of charecters are matched unrecognized characters that are not
	 *            in one of the the <code>matches</code> string should be accepted. For instance if "abczz" should be matched with
	 *            "abcdef" and min chars 3.
	 * @return The index of the first unmatched character if <code>minChars</code> was reached or <code>-1</code> if a match was
	 *         not
	 *         found.
	 */
	private static int startsWithLenient(final String s, final String match, int minChars, final boolean acceptTrailing) {
		if (s.charAt(0) != match.charAt(0)) {
			return -1;
		}

		if (minChars == -1) {
			minChars = match.length();
		}

		final int sSz = s.length();
		if (sSz < minChars) {
			return -1;
		}

		final int mSz = match.length();
		int sIx = 0;
		for (int mIx = 0; mIx < mSz; sIx++, mIx++) {
			while (sIx < sSz && (s.charAt(sIx) == ' ' || s.charAt(sIx) == '_')) {
				// Disregard spaces and _
				sIx++;
			}

			if (sIx >= sSz || s.charAt(sIx) != match.charAt(mIx)) {
				return mIx >= minChars && (acceptTrailing || sIx >= sSz) && (sIx >= sSz || s.charAt(sIx - 1) == ' ') ? sIx : -1;
			}
		}
		return sIx >= sSz || acceptTrailing || s.charAt(sIx) == ' ' ? sIx : -1;
	}

	/**
	 * Parses a string and returns it in those parts of the string that are separated with a <code>sep</code> character.
	 * <p>
	 * separator characters within parentheses will not be counted or handled in any way, whatever the depth.
	 * <p>
	 * A space separator will be a hit to one or more spaces and thus not return empty strings.
	 * 
	 * @param s The string to parse. If it starts and/or ends with a <code>sep</code> the first and/or last element returned will
	 *            be "". If
	 *            two <code>sep</code> are next to each other and empty element will be "between" the periods. The
	 *            <code>sep</code> themselves will never be returned.
	 * @param sep The separator char.
	 * @return Those parts of the string that are separated with <code>sep</code>. Never null and at least of size 1
	 * @since 6.7.2 Changed so more than one space in a row works as one space.
	 */
	private static String[] toTrimmedTokens(final String s, final char sep) {
		int toks = 0;
		final int sSize = s.length();
		final boolean disregardDoubles = sep == ' ';

		// Count the sep:s
		int p = 0;
		for (int i = 0; i < sSize; i++) {
			final char c = s.charAt(i);
			if (c == '(') {
				p++;
			}
			else if (c == ')') {
				p--;
			}
			else if (p == 0 && c == sep) {
				toks++;
				while (disregardDoubles && i < sSize - 1 && s.charAt(i + 1) == ' ') {
					i++;
				}
			}
			if (p < 0) {
				throw new IllegalArgumentException("Unbalanced parentheses: '" + s + "'");
			}
		}
		if (p != 0) {
			throw new IllegalArgumentException("Unbalanced parentheses: '" + s + "'");
		}

		if (toks == 0) {
			return new String[] {s.trim()};
		}

		final String[] retArr = new String[toks + 1];

		int st = 0;
		int pNr = 0;
		p = 0;
		for (int i = 0; i < sSize; i++) {

			final char c = s.charAt(i);
			if (c == '(') {
				p++;
			}
			else if (c == ')') {
				p--;
			}
			else if (p == 0 && c == sep) {
				retArr[pNr++] = s.substring(st, i).trim();
				st = i + 1;
				while (disregardDoubles && i < sSize - 1 && s.charAt(i + 1) == ' ') {
					i++;
				}
			}
		}

		retArr[pNr++] = s.substring(st, sSize).trim();
		return retArr;
	}

	/**
	 * Parses "AAA[BBB]CCC[DDD]EEE" into {"AAA", "BBB", "CCC", "DDD", "EEE", "FFF"}. Handles empty parts. Will always start and
	 * end outside
	 * a [] block so that the number of returned elemets will always be uneven and at least of length 3.
	 * <p>
	 * "|" is interpreted as "][".
	 * 
	 * @param s The string. Might be "" but not null. Should be trimmed.
	 * @return The string divided into elements. Never <code>null</code> and at least of length 3.
	 * @throws IllegalArgumentException If a [] mismatch of some kind. (If not same [ as ] count or if the interleave.)
	 */
	private static ArrayList<String> getRowColAndGapsTrimmed(String s) {
		if (s.indexOf('|') != -1) {
			s = s.replaceAll("\\|", "][");
		}

		final ArrayList<String> retList = new ArrayList<String>(Math.max(s.length() >> 2 + 1, 3)); // Approx return length.
		int s0 = 0; // '[' and
		int s1 = 0; // ']' count.
		int st = 0; // Start of "next token to add".
		final int iSz = s.length();
		for (int i = 0; i < iSz; i++) {
			final char c = s.charAt(i);
			if (c == '[') {
				s0++;
			}
			else if (c == ']') {
				s1++;
			}
			else {
				continue;
			}

			if (s0 != s1 && (s0 - 1) != s1) {
				break; // Wrong [ or ] found. Break for throw.
			}

			retList.add(s.substring(st, i).trim());
			st = i + 1;
		}
		if (s0 != s1) {
			throw new IllegalArgumentException("'[' and ']' mismatch in row/column format string: " + s);
		}

		if (s0 == 0) {
			retList.add("");
			retList.add(s);
			retList.add("");
		}
		else if (retList.size() % 2 == 0) {
			retList.add(s.substring(st, s.length()));
		}

		return retList;
	}

	/**
	 * Makes <code>null</code> "", trmms and converts to lower case.
	 * 
	 * @param s The string
	 * @return Not null.
	 */
	public static String prepare(final String s) {
		return s != null ? s.trim().toLowerCase() : "";
	}

	//	/** Tests to serialize and deserialize the object with both XMLEncoder/Decoder and through Serializable
	//	 * @param o The object to serialize
	//	 * @return The same object after a try through the process.
	//	 */
	//	public static final Object serializeTest(Object o)
	//	{
	//		try {
	//			ByteArrayOutputStream barr = new ByteArrayOutputStream();
	//			XMLEncoder enc = new XMLEncoder(barr);
	//			enc.writeObject(o);
	//			enc.close();
	//
	//			XMLDecoder dec = new XMLDecoder(new ByteArrayInputStream(barr.toByteArray()));
	//			o = dec.readObject();
	//			dec.close();
	//		} catch (Exception e) {
	//		    //CHECKSTYLE:OFF
	//			e.printStackTrace();
	//		    //CHECKSTYLE:ON
	//		}
	//
	//		try {
	//			ByteArrayOutputStream barr = new ByteArrayOutputStream();
	//			ObjectOutputStream oos = new ObjectOutputStream(barr);
	//			oos.writeObject(o);
	//			oos.close();
	//
	//			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
	//			o = ois.readObject();
	//			ois.close();
	//		} catch (Exception e) {
	//		    //CHECKSTYLE:OFF
	//			e.printStackTrace();
	//		    //CHECKSTYLE:ON
	//		}
	//
	//		return o;
	//	}
}
