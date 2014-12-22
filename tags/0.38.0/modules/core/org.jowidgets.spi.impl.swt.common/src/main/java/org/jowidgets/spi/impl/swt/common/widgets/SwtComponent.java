/*
 * Copyright (c) 2010, Michael Grossmann
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
package org.jowidgets.spi.impl.swt.common.widgets;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.jowidgets.common.color.ColorValue;
import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Cursor;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.MouseButton;
import org.jowidgets.common.types.Position;
import org.jowidgets.common.widgets.controller.IComponentListener;
import org.jowidgets.common.widgets.controller.IFocusListener;
import org.jowidgets.common.widgets.controller.IKeyListener;
import org.jowidgets.common.widgets.controller.IMouseListener;
import org.jowidgets.common.widgets.controller.IMouseMotionListener;
import org.jowidgets.common.widgets.controller.IPopupDetectionListener;
import org.jowidgets.spi.impl.controller.ComponentObservable;
import org.jowidgets.spi.impl.controller.FocusObservable;
import org.jowidgets.spi.impl.controller.KeyObservable;
import org.jowidgets.spi.impl.controller.MouseButtonEvent;
import org.jowidgets.spi.impl.controller.MouseMotionObservable;
import org.jowidgets.spi.impl.controller.MouseObservable;
import org.jowidgets.spi.impl.controller.PopupDetectionObservable;
import org.jowidgets.spi.impl.swt.common.color.ColorCache;
import org.jowidgets.spi.impl.swt.common.cursor.CursorCache;
import org.jowidgets.spi.impl.swt.common.util.DimensionConvert;
import org.jowidgets.spi.impl.swt.common.util.MouseUtil;
import org.jowidgets.spi.impl.swt.common.util.PositionConvert;
import org.jowidgets.spi.impl.swt.common.widgets.event.LazyKeyEventContentFactory;
import org.jowidgets.spi.widgets.IComponentSpi;
import org.jowidgets.spi.widgets.IPopupMenuSpi;
import org.jowidgets.util.event.IObservableCallback;

public class SwtComponent extends SwtWidget implements IComponentSpi {

	private final PopupDetectionObservable popupDetectionObservable;
	private final FocusObservable focusObservable;
	private final KeyObservable keyObservable;
	private final MouseObservable mouseObservable;
	private final MouseMotionObservable mouseMotionObservable;
	private final ComponentObservable componentObservable;
	private final KeyListener keyListener;
	private MenuDetectListener menuDetectListener;

	public SwtComponent(final Control control) {
		super(control);
		this.popupDetectionObservable = new PopupDetectionObservable();
		this.focusObservable = new FocusObservable();
		this.mouseObservable = new MouseObservable();
		this.componentObservable = new ComponentObservable();

		this.menuDetectListener = new MenuDetectListener() {

			@Override
			public void menuDetected(final MenuDetectEvent e) {
				final Point position = getUiReference().toControl(e.x, e.y);
				popupDetectionObservable.firePopupDetected(new Position(position.x, position.y));
			}
		};

		getUiReference().addMenuDetectListener(menuDetectListener);

		getUiReference().addControlListener(new ControlListener() {
			@Override
			public void controlResized(final ControlEvent e) {
				componentObservable.fireSizeChanged(getSize());
			}

			@Override
			public void controlMoved(final ControlEvent e) {
				componentObservable.firePositionChanged(getPosition());
			}
		});

		getUiReference().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(final FocusEvent e) {
				focusObservable.focusLost();
			}

			@Override
			public void focusGained(final FocusEvent e) {
				focusObservable.focusGained();
			}
		});

		this.keyListener = new KeyListener() {
			@Override
			public void keyReleased(final KeyEvent e) {
				keyObservable.fireKeyReleased(new LazyKeyEventContentFactory(e));
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				keyObservable.fireKeyPressed(new LazyKeyEventContentFactory(e));
			}
		};

		final IObservableCallback keyObservableCallback = new IObservableCallback() {

			private boolean listenerAdded = false;

			@Override
			public void onLastUnregistered() {
				if (listenerAdded) {
					getUiReference().removeKeyListener(keyListener);
					listenerAdded = false;
				}
			}

			@Override
			public void onFirstRegistered() {
				if (!listenerAdded) {
					getUiReference().addKeyListener(keyListener);
					listenerAdded = true;
				}
			}
		};

		this.keyObservable = new KeyObservable(keyObservableCallback);

		getUiReference().addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(final MouseEvent e) {
				final MouseButtonEvent mouseEvent = getMouseEvent(e, 1);
				if (mouseEvent != null) {
					mouseObservable.fireMouseReleased(mouseEvent);
				}
			}

			@Override
			public void mouseDown(final MouseEvent e) {
				final MouseButtonEvent mouseEvent = getMouseEvent(e, 1);
				if (mouseEvent != null) {
					mouseObservable.fireMousePressed(mouseEvent);
				}
			}

			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				final MouseButtonEvent mouseEvent = getMouseEvent(e, 2);
				if (mouseEvent != null) {
					mouseObservable.fireMouseDoubleClicked(mouseEvent);
				}
			}

			private MouseButtonEvent getMouseEvent(final MouseEvent event, final int maxCount) {
				try {
					if (event.count > maxCount) {
						return null;
					}
				}
				catch (final NoSuchFieldError e) {
					//RWT doesn't support count field :-( 
					//so the mouse down and mouse up may be fired twice at double clicks :-(
				}
				final MouseButton mouseButton = MouseUtil.getMouseButton(event);
				if (mouseButton == null) {
					return null;
				}
				final Position position = new Position(event.x, event.y);
				return new MouseButtonEvent(position, mouseButton, MouseUtil.getModifier(event.stateMask));
			}
		});

		try {
			getUiReference().addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseExit(final MouseEvent e) {
					mouseObservable.fireMouseExit(new Position(e.x, e.y));
				}

				@Override
				public void mouseEnter(final MouseEvent e) {
					mouseObservable.fireMouseEnter(new Position(e.x, e.y));
				}

			});
		}
		catch (final NoSuchMethodError error) {
			//RWT doesn't support MouseTrackListener
		}

		final MouseMoveListener mouseMoveListener = new MouseMoveListener() {

			@Override
			public void mouseMove(final MouseEvent event) {
				final Position position = new Position(event.x, event.y);
				final MouseButton mouseButton = MouseUtil.getMouseButton(event);
				if (mouseButton != null) {
					mouseMotionObservable.fireMouseDragged(new MouseButtonEvent(
						position,
						mouseButton,
						MouseUtil.getModifier(event.stateMask)));
				}
				else {
					mouseMotionObservable.fireMouseMoved(position);
				}
			}

		};

		final IObservableCallback mouseMotionObservableCallback = new IObservableCallback() {

			@Override
			public void onFirstRegistered() {
				try {
					getUiReference().addMouseMoveListener(mouseMoveListener);
				}
				catch (final NoSuchMethodError error) {
					//RWT doesn't support MouseTrackListener
				}
			}

			@Override
			public void onLastUnregistered() {
				try {
					getUiReference().removeMouseMoveListener(mouseMoveListener);
				}
				catch (final NoSuchMethodError error) {
					//RWT doesn't support MouseTrackListener
				}
			}
		};

		this.mouseMotionObservable = new MouseMotionObservable(mouseMotionObservableCallback);

	}

	protected PopupDetectionObservable getPopupDetectionObservable() {
		return popupDetectionObservable;
	}

	protected void setMenuDetectListener(final MenuDetectListener menuDetectListener) {
		getUiReference().removeMenuDetectListener(this.menuDetectListener);
		this.menuDetectListener = menuDetectListener;
		getUiReference().addMenuDetectListener(menuDetectListener);
	}

	@Override
	public void setControl(final Control control) {
		getUiReference().removeMenuDetectListener(menuDetectListener);
		super.setControl(control);
		getUiReference().addMenuDetectListener(menuDetectListener);
	}

	@Override
	public void redraw() {
		final Composite parent = getUiReference().getParent();
		if (parent != null) {
			parent.layout(new Control[] {getUiReference()});
			parent.redraw();
			getUiReference().redraw();
		}
		else {
			getUiReference().redraw();
		}
	}

	@Override
	public void setRedrawEnabled(final boolean enabled) {
		getUiReference().setRedraw(enabled);
	}

	@Override
	public void setCursor(final Cursor cursor) {
		getUiReference().setCursor(CursorCache.getCursor(cursor));
	}

	@Override
	public void setForegroundColor(final IColorConstant colorValue) {
		getUiReference().setForeground(ColorCache.getInstance().getColor(colorValue));
	}

	@Override
	public void setBackgroundColor(final IColorConstant colorValue) {
		getUiReference().setBackground(ColorCache.getInstance().getColor(colorValue));
	}

	@Override
	public IColorConstant getForegroundColor() {
		return toColorConstant(getUiReference().getForeground());
	}

	@Override
	public IColorConstant getBackgroundColor() {
		return toColorConstant(getUiReference().getBackground());
	}

	private IColorConstant toColorConstant(final Color color) {
		return new ColorValue(color.getRed(), color.getGreen(), color.getBlue());
	}

	@Override
	public void setVisible(final boolean visible) {
		getUiReference().setVisible(visible);
	}

	@Override
	public boolean isVisible() {
		return getUiReference().isVisible();
	}

	@Override
	public Dimension getSize() {
		return DimensionConvert.convert(getUiReference().getSize());
	}

	@Override
	public void setSize(final Dimension size) {
		getUiReference().setSize(DimensionConvert.convert(size));
	}

	@Override
	public Position getPosition() {
		return PositionConvert.convert(getUiReference().getLocation());
	}

	@Override
	public void setPosition(final Position position) {
		getUiReference().setLocation(PositionConvert.convert(position));
	}

	@Override
	public IPopupMenuSpi createPopupMenu() {
		return new PopupMenuImpl(getUiReference());
	}

	@Override
	public boolean requestFocus() {
		return getUiReference().forceFocus();
	}

	@Override
	public void addFocusListener(final IFocusListener listener) {
		focusObservable.addFocusListener(listener);
	}

	@Override
	public void removeFocusListener(final IFocusListener listener) {
		focusObservable.removeFocusListener(listener);
	}

	@Override
	public void addKeyListener(final IKeyListener listener) {
		keyObservable.addKeyListener(listener);
	}

	@Override
	public void removeKeyListener(final IKeyListener listener) {
		keyObservable.removeKeyListener(listener);
	}

	@Override
	public void addComponentListener(final IComponentListener componentListener) {
		componentObservable.addComponentListener(componentListener);
	}

	@Override
	public void removeComponentListener(final IComponentListener componentListener) {
		componentObservable.removeComponentListener(componentListener);
	}

	@Override
	public void addMouseListener(final IMouseListener mouseListener) {
		mouseObservable.addMouseListener(mouseListener);
	}

	@Override
	public void removeMouseListener(final IMouseListener mouseListener) {
		mouseObservable.removeMouseListener(mouseListener);
	}

	@Override
	public void addMouseMotionListener(final IMouseMotionListener listener) {
		mouseMotionObservable.addMouseMotionListener(listener);
	}

	@Override
	public void removeMouseMotionListener(final IMouseMotionListener listener) {
		mouseMotionObservable.removeMouseMotionListener(listener);
	}

	@Override
	public void addPopupDetectionListener(final IPopupDetectionListener listener) {
		popupDetectionObservable.addPopupDetectionListener(listener);
	}

	@Override
	public void removePopupDetectionListener(final IPopupDetectionListener listener) {
		popupDetectionObservable.removePopupDetectionListener(listener);
	}

	public void setToolTipText(final String toolTip) {
		getUiReference().setToolTipText(toolTip);
	}

}
