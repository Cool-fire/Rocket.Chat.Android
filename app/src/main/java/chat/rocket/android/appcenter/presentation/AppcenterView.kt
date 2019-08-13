package chat.rocket.android.appcenter.presentation

import chat.rocket.android.core.behaviours.LoadingView
import chat.rocket.android.core.behaviours.MessageView
import chat.rocket.common.model.Game

interface AppcenterView : LoadingView, MessageView {

    fun showGames(games: List<Game>)
}