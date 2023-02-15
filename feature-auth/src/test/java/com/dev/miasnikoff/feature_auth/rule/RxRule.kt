package com.dev.miasnikoff.feature_auth.rule

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.ExternalResource

class RxRule : ExternalResource() {

    override fun before() {
        super.before()
        replaceSchedulers()
    }

    override fun after() {
        super.after()
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    private fun replaceSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler  { Schedulers.trampoline() }
    }
}