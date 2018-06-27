/**
 * Copyright 2016 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jspham.getyoulater.android;

import javax.inject.Inject;

import com.crashlytics.android.Crashlytics;
import com.jspham.getyoulater.android.data.PeopleFactory;
import com.jspham.getyoulater.android.data.PeopleService;
import com.jspham.getyoulater.android.di.component.DaggerAppComponent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.fabric.sdk.android.Fabric;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class PeopleApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    private PeopleService peopleService;
    private Scheduler scheduler;
    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private static PeopleApplication get(Context context) {
        return (PeopleApplication) context.getApplicationContext();
    }

    public static PeopleApplication create(Context context) {
        return PeopleApplication.get(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(PeopleApplication.this).build().inject(this);
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public PeopleService getPeopleService() {
        if (peopleService == null) {
            peopleService = PeopleFactory.create();
        }

        return peopleService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
