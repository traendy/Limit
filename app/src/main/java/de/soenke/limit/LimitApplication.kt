package de.soenke.limit

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import de.soenke.limit.di.DaggerApplicationComponent

class LimitApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}