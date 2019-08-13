package chat.rocket.android.appcenter.di

import chat.rocket.android.appcenter.presentation.AppcenterView
import chat.rocket.android.appcenter.ui.AppcenterFragment
import chat.rocket.android.dagger.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
class AppcenterFragmentModule {

    @Provides
    @PerFragment
    fun appcenterView(frag: AppcenterFragment): AppcenterView {
        return frag
    }
}