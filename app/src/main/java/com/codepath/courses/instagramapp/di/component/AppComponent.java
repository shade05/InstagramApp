package com.codepath.courses.instagramapp.di.component;

import com.codepath.courses.instagramapp.InstagramPhotoListFragment;
import com.codepath.courses.instagramapp.MainActivity;
import com.codepath.courses.instagramapp.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by deepaks on 11/19/15.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(InstagramPhotoListFragment instagramPhotoListFragment);

}
