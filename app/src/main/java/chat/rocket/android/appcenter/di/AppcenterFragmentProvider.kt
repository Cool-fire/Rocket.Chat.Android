package chat.rocket.android.appcenter.di

import chat.rocket.android.appcenter.ui.AppcenterFragment
import chat.rocket.android.dagger.scope.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppcenterFragmentProvider  {

    @ContributesAndroidInjector(modules = [AppcenterFragmentModule::class])
    @PerFragment
    abstract fun provideAppcenterFragment(): AppcenterFragment
}