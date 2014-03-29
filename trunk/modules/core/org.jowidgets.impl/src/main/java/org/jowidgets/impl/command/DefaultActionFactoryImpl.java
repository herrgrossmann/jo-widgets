/*
 * Copyright (c) 2014, MGrossmann
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

package org.jowidgets.impl.command;

import org.jowidgets.api.command.IAction;
import org.jowidgets.api.command.IDefaultActionBuilder;
import org.jowidgets.api.command.IDefaultActionFactory;
import org.jowidgets.api.command.ITreeExpansionAction;
import org.jowidgets.api.command.ITreeExpansionActionBuilder;
import org.jowidgets.api.widgets.ITreeContainer;
import org.jowidgets.util.Assert;

public final class DefaultActionFactoryImpl implements IDefaultActionFactory {

	@Override
	public ITreeExpansionActionBuilder collapseTreeActionBuilder(final ITreeContainer tree) {
		Assert.paramNotNull(tree, "tree");
		return new CollapseTreeActionBuilder(tree);
	}

	@Override
	public ITreeExpansionAction collapseTreeAction(final ITreeContainer tree) {
		Assert.paramNotNull(tree, "tree");
		return collapseTreeActionBuilder(tree).build();
	}

	@Override
	public ITreeExpansionActionBuilder expandTreeActionBuilder(final ITreeContainer tree) {
		return new ExpandTreeActionBuilder(tree);
	}

	@Override
	public ITreeExpansionAction expandTreeAction(final ITreeContainer tree) {
		Assert.paramNotNull(tree, "tree");
		return expandTreeActionBuilder(tree).build();
	}

	@Override
	public IDefaultActionBuilder checkTreeActionBuilder(final ITreeContainer tree) {
		return new CheckTreeActionBuilder(tree);
	}

	@Override
	public IAction checkTreeAction(final ITreeContainer tree) {
		return checkTreeActionBuilder(tree).build();
	}

	@Override
	public IDefaultActionBuilder uncheckTreeActionBuilder(final ITreeContainer tree) {
		return new UncheckTreeActionBuilder(tree);
	}

	@Override
	public IAction uncheckTreeAction(final ITreeContainer tree) {
		return uncheckTreeActionBuilder(tree).build();
	}

}
