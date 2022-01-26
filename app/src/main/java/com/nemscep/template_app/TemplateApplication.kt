package com.nemscep.template_app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TemplateApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(modules)
            androidContext(this@TemplateApplication)
        }
    }
}
