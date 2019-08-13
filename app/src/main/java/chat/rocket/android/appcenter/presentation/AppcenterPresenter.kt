package chat.rocket.android.appcenter.presentation

import chat.rocket.android.core.lifecycle.CancelStrategy
import chat.rocket.android.db.DatabaseManager
import chat.rocket.android.members.uimodel.MemberUiModelMapper
import chat.rocket.android.server.domain.PermissionsInteractor
import chat.rocket.android.server.infrastructure.RocketChatClientFactory
import chat.rocket.android.util.extension.launchUI
import chat.rocket.common.RocketChatException
import chat.rocket.common.util.ifNull
import chat.rocket.core.RocketChatClient
import chat.rocket.core.internal.rest.getGames
import javax.inject.Inject
import javax.inject.Named

class AppcenterPresenter @Inject constructor(
        private val view: AppcenterView,
        private val dbManager: DatabaseManager,
        private val permissionsInteractor: PermissionsInteractor,
        @Named("currentServer") private val currentServer: String?,
        private val strategy: CancelStrategy,
        private val mapper: MemberUiModelMapper,
        val factory: RocketChatClientFactory
) {
    private val client: RocketChatClient? = currentServer?.let { factory.get(it) }

    fun loadGames(roomId: String) {
        launchUI(strategy) {
            try {
                view.showLoading()
                client?.getGames()?.let { games ->
                    view.showGames(games.result)
                }
            } catch (exception: RocketChatException) {
                exception.message?.let {
                    view.showMessage(it)
                }.ifNull {
                    view.showGenericErrorMessage()
                }
            } finally {
                view.hideLoading()
            }
        }
    }
}