/*
 * Copyright (c) 2011, Benjamin Marstaller
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

/**
 * 
 */
package org.jowidgets.impl.test.blueprint.mock;

import org.jowidgets.api.widgets.blueprint.factory.IBluePrintProxyFactory;
import org.jowidgets.impl.widgets.composed.blueprint.BluePrintFactory;

public class DummyBluePrintFactory extends BluePrintFactory {

    public DummyBluePrintFactory(final IBluePrintProxyFactory bluePrintProxyFactory) {
        super(bluePrintProxyFactory);
    }

    public final IHierarchy1stBluePrint hierarchy1st() {
        return bluePrint(IHierarchy1stBluePrint.class);
    }

    public final IHierarchy2nd1BluePrint hierarchy2nd1() {
        return bluePrint(IHierarchy2nd1BluePrint.class);
    }

    public final IHierarchy2nd2BluePrint hierarchy2nd2() {
        return bluePrint(IHierarchy2nd2BluePrint.class);
    }

    public final IHierarchy2nd3BluePrint hierarchy2nd3() {
        return bluePrint(IHierarchy2nd3BluePrint.class);
    }

    public final IHierarchy3rdBluePrint hierarchy3rd() {
        return bluePrint(IHierarchy3rdBluePrint.class);
    }

    public final IHierarchy4thBluePrint hierarchy4th() {
        return bluePrint(IHierarchy4thBluePrint.class);
    }
}
