package com.codepath.courses.instagramapp.di.modules;

import android.app.Application;
import android.content.res.Resources;

import com.codepath.courses.instagramapp.service.InstagramClient;
import com.codepath.courses.instagramapp.service.InstagramService;
import com.codepath.courses.instagramapp.service.impl.InstagramClientApiImpl;
import com.codepath.courses.instagramapp.service.impl.InstagramClientImpl;
import com.codepath.courses.instagramapp.service.impl.InstagramServiceImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    Resources mResources;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return mApplication.getResources();
    }

    @Provides
    @Singleton
    InstagramService providesInstagramService(@Named("api") InstagramClient instagramClient) {
        InstagramServiceImpl instagramService = new InstagramServiceImpl();
        instagramService.setInstagramClient(instagramClient);
        return instagramService;
    }

    @Provides
    @Singleton
    @Named("vanila")
    InstagramClient providesInstagramClient() {
        final InstagramClientImpl instagramClient = new InstagramClientImpl();
        instagramClient.setResources(mApplication.getResources());
        return instagramClient;
    }

    @Provides
    @Singleton
    @Named("api")
    InstagramClient providesInstagramApiClient() {
        final InstagramClientApiImpl instagramClient = new InstagramClientApiImpl();
        return instagramClient;
    }
}