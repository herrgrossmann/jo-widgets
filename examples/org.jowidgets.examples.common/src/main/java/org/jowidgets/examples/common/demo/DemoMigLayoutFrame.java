/*
 * Copyright (c) 2011, Nikolaus Moll
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

package org.jowidgets.examples.common.demo;

import org.jowidgets.api.layout.ILayoutFactoryProvider;
import org.jowidgets.api.toolkit.Toolkit;
import org.jowidgets.api.widgets.blueprint.factory.IBluePrintFactory;
import org.jowidgets.impl.layout.miglayout.common.AC;
import org.jowidgets.impl.layout.miglayout.common.CC;
import org.jowidgets.impl.layout.miglayout.common.LC;
import org.jowidgets.tools.powo.JoFrame;

public class DemoMigLayoutFrame extends JoFrame {

	private static final IBluePrintFactory BPF = Toolkit.getBluePrintFactory();

	public DemoMigLayoutFrame() {
		super("Null layout demo");

		final ILayoutFactoryProvider lfp = Toolkit.getLayoutFactoryProvider();

		final LC layC = new LC().fill().wrap();
		final AC colC = new AC().align("right", 0).fill(1, 3).grow(100, 1, 3).align("right", 2).gap("15", 1);
		final AC rowC = new AC().index(6).gap("15!").align("top").grow(100, 8);

		setLayout(lfp.migLayoutBuilder().constraints(layC).columnConstraints(colC).rowConstraints(rowC).build());

		add(BPF.textLabel("Last Name"), "");
		add(BPF.textField(), "");
		add(BPF.textLabel("First Name"), "");
		add(BPF.textField(), "wrap");

		add(BPF.textLabel("Phone"), "");
		add(BPF.textField(), "");

		add(BPF.textLabel("Email"), "");
		add(BPF.textField(), "");
		add(BPF.textLabel("Address 1"), "");
		add(BPF.textField(), new CC().spanX().growX());
		add(BPF.textLabel("Address 2"), "");
		add(BPF.textField(), new CC().spanX().growX());
		add(BPF.textLabel("City"), "");
		add(BPF.textField(), new CC().wrap());
		add(BPF.textLabel("State"), "");
		add(BPF.textField(), "");
		add(BPF.textLabel("Postal Code"), "");
		add(BPF.textField(), new CC().spanX(2).growX(0));
		add(BPF.textLabel("Country"), "");
		add(BPF.textField(), new CC().wrap());

		add(BPF.button("New"), new CC().spanX(5).split(5).tag("other"));
		add(BPF.button("Delete"), new CC().tag("other"));
		add(BPF.button("Edit"), new CC().tag("other"));
		add(BPF.button("Save"), new CC().tag("other"));
		add(BPF.button("Cancel"), new CC().tag("cancel"));

		setSize(800, 600);
	}
}
