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
package org.jowidgets.impl.base.blueprint.factory;

import org.jowidgets.api.widgets.IWidget;
import org.jowidgets.api.widgets.blueprint.builder.IComponentSetupBuilder;
import org.jowidgets.api.widgets.blueprint.convenience.ISetupBuilderConvenience;
import org.jowidgets.api.widgets.blueprint.convenience.ISetupBuilderConvenienceRegistry;
import org.jowidgets.api.widgets.blueprint.defaults.IDefaultInitializer;
import org.jowidgets.api.widgets.blueprint.defaults.IDefaultsInitializerRegistry;
import org.jowidgets.api.widgets.blueprint.factory.IBaseBluePrintFactory;
import org.jowidgets.common.widgets.IWidgetCommon;
import org.jowidgets.common.widgets.builder.ISetupBuilder;
import org.jowidgets.common.widgets.descriptor.IWidgetDescriptor;
import org.jowidgets.impl.base.blueprint.proxy.BluePrintProxyProvider;
import org.jowidgets.impl.spi.blueprint.IFrameBluePrintSpi;
import org.jowidgets.tools.widgets.blueprint.defaults.DefaultsInitializerRegistry;

public abstract class AbstractBluePrintFactory implements IBaseBluePrintFactory {

	private final ISetupBuilderConvenienceRegistry setupBuilderConvenienceRegistry;
	private IDefaultsInitializerRegistry defaultInitializerRegistry;

	public AbstractBluePrintFactory(
		final ISetupBuilderConvenienceRegistry setupBuilderConvenienceRegistry,
		final IDefaultsInitializerRegistry defaultInitializerRegistry) {
		super();
		this.setupBuilderConvenienceRegistry = setupBuilderConvenienceRegistry;
		this.defaultInitializerRegistry = defaultInitializerRegistry;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <WIDGET_TYPE extends IWidget, BLUE_PRINT_TYPE extends IComponentSetupBuilder<BLUE_PRINT_TYPE> & IWidgetDescriptor<WIDGET_TYPE>> BLUE_PRINT_TYPE bluePrint(
		final Class<BLUE_PRINT_TYPE> bluePrintType) {
		//This cast is necessary for compilation with sun compiler. 
		//It compiles in eclipse Version: Helios Service Release 1 Build id: 20100917-0705
		//without the cast, so don't remove the cast
		return (BLUE_PRINT_TYPE) createProxy(bluePrintType);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected <BLUE_PRINT_TYPE extends IWidgetDescriptor<? extends IWidgetCommon>> BLUE_PRINT_TYPE createProxy(
		final Class<? extends IWidgetDescriptor> bluePrintType) {

		return (BLUE_PRINT_TYPE) new BluePrintProxyProvider<IFrameBluePrintSpi>(
			bluePrintType,
			setupBuilderConvenienceRegistry,
			defaultInitializerRegistry).getBluePrint();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setSetupBuilderConvenience(
		final Class<? extends ISetupBuilder> setupBuilder,
		final ISetupBuilderConvenience<?> setupBuilderConvenience) {
		getSetupBuilderConvenienceRegistry().register(setupBuilder, setupBuilderConvenience);
	}

	@Override
	public void setDefaultsInitializerRegistry(final IDefaultsInitializerRegistry defaultInitializerRegistry) {
		if (defaultInitializerRegistry == null) {
			this.defaultInitializerRegistry = new DefaultsInitializerRegistry();
		}
		else {
			this.defaultInitializerRegistry = defaultInitializerRegistry;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addDefaultsInitializer(
		final Class<? extends ISetupBuilder> setupBuilder,
		final IDefaultInitializer<?> defaultsImpl) {
		defaultInitializerRegistry.register(setupBuilder, defaultsImpl);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setDefaultsInitializer(
		final Class<? extends ISetupBuilder> setupBuilder,
		final IDefaultInitializer<?> defaultsImpl) {
		defaultInitializerRegistry.setDefaultsInitializer(setupBuilder, defaultsImpl);
	}

	@Override
	public ISetupBuilderConvenienceRegistry getSetupBuilderConvenienceRegistry() {
		return setupBuilderConvenienceRegistry;
	}

	@Override
	public IDefaultsInitializerRegistry getDefaultsInitializerRegistry() {
		return defaultInitializerRegistry;
	}
}
