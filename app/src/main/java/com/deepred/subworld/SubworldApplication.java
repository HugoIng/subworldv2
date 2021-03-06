package com.deepred.subworld;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.deepred.subworld.injection.component.ApplicationComponent;
import com.deepred.subworld.injection.module.ApplicationModule;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import com.deepred.subworld.BuildConfig;
import com.deepred.subworld.injection.component.DaggerApplicationComponent;

public class SubworldApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        }
    }

    public static SubworldApplication get(Context context) {
        return (SubworldApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
