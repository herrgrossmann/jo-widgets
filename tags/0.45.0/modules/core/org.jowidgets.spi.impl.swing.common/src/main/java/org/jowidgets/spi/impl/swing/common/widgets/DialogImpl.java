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
package org.jowidgets.spi.impl.swing.common.widgets;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import org.jowidgets.common.color.IColorConstant;
import org.jowidgets.common.types.Rectangle;
import org.jowidgets.common.widgets.IButtonCommon;
import org.jowidgets.common.widgets.IControlCommon;
import org.jowidgets.common.widgets.factory.IGenericWidgetFactory;
import org.jowidgets.spi.impl.swing.common.image.SwingImageRegistry;
import org.jowidgets.spi.impl.swing.common.util.ColorConvert;
import org.jowidgets.spi.impl.swing.common.util.DecorationCalc;
import org.jowidgets.spi.impl.swing.common.widgets.util.ChildRemover;
import org.jowidgets.spi.widgets.IFrameSpi;
import org.jowidgets.spi.widgets.IMenuBarSpi;
import org.jowidgets.spi.widgets.setup.IDialogSetupSpi;
import org.jowidgets.util.TypeCast;

public class DialogImpl extends SwingWindow implements IFrameSpi {

    private static final Border BORDER = new JTextField().getBorder();

    public DialogImpl(
        final IGenericWidgetFactory factory,
        final SwingImageRegistry imageRegistry,
        final Object parentUiReference,
        final IDialogSetupSpi setup) {
        super(factory, new JDialog((Window) parentUiReference), setup.isCloseable());

        getUiReference().setTitle(setup.getTitle());
        getUiReference().setResizable(setup.isResizable());
        getUiReference().setModal(setup.isModal());

        getUiReference().setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        if (!setup.isDecorated()) {
            getUiReference().setUndecorated(true);
            ((JComponent) getUiReference().getContentPane()).setBorder(BORDER);
        }

        final boolean closeOnEscape = setup.isCloseable() && setup.isCloseOnEscape();
        if (closeOnEscape) {
            final ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    setVisible(false);
                }
            };

            getUiReference().getRootPane().registerKeyboardAction(
                    actionListener,
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_IN_FOCUSED_WINDOW);
        }

        setIcon(setup.getIcon(), imageRegistry);
    }

    @Override
    public JDialog getUiReference() {
        return (JDialog) super.getUiReference();
    }

    @Override
    public void setDefaultButton(final IButtonCommon button) {
        if (button != null) {
            getUiReference().getRootPane().setDefaultButton(TypeCast.toType(button.getUiReference(), JButton.class));
        }
        else {
            getUiReference().getRootPane().setDefaultButton(null);
        }
    }

    @Override
    public Rectangle getClientArea() {
        return DecorationCalc.getClientArea(getUiReference().getContentPane());
    }

    @Override
    public void setTitle(final String title) {
        getUiReference().setTitle(title);
    }

    @Override
    public void setBackgroundColor(final IColorConstant colorValue) {
        getUiReference().getContentPane().setBackground(ColorConvert.convert(colorValue));
        super.setBackgroundColor(colorValue);
    }

    @Override
    public boolean remove(final IControlCommon control) {
        return ChildRemover.removeChild(getUiReference().getContentPane(), (Component) control.getUiReference());
    }

    @Override
    public void removeAll() {
        getUiReference().getContentPane().removeAll();
    }

    @Override
    public IMenuBarSpi createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        getUiReference().setJMenuBar(menuBar);
        return new MenuBarImpl(menuBar);
    }

    @Override
    public void setMaximized(final boolean maximized) {
        // NOT SUPPORTED FOR SWING
    }

    @Override
    public boolean isMaximized() {
        // NOT SUPPORTED FOR SWING
        return false;
    }

    @Override
    public void setIconfied(final boolean iconfied) {
        // NOT SUPPORTED FOR SWING
    }

    @Override
    public boolean isIconfied() {
        // NOT SUPPORTED FOR SWING
        return false;
    }

}
