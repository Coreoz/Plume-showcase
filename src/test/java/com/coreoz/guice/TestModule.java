package com.coreoz.guice;

import com.coreoz.plume.db.guice.GuiceDbTestModule;
import com.coreoz.plume.services.time.TimeProvider;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;

public class TestModule extends AbstractModule {
	@Override
	protected void configure() {
		install(Modules.override(new ApplicationModule()).with(new AbstractModule() {
			@Override
			protected void configure() {
				bind(TimeProvider.class).to(TimeProviderForTest.class);
			}
		}));
        install(new GuiceDbTestModule());
	}
}
