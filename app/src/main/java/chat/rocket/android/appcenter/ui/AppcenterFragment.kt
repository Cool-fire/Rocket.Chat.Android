package chat.rocket.android.appcenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import chat.rocket.android.R
import chat.rocket.android.analytics.AnalyticsManager
import chat.rocket.android.appcenter.adapter.AppcenterAdapter
import chat.rocket.android.appcenter.presentation.AppcenterPresenter
import chat.rocket.android.appcenter.presentation.AppcenterView
import chat.rocket.android.chatroom.ui.ChatRoomActivity
import chat.rocket.android.util.extensions.clearLightStatusBar
import chat.rocket.android.util.extensions.inflate
import chat.rocket.android.util.extensions.showToast
import chat.rocket.android.util.extensions.ui
import chat.rocket.common.model.Game
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.app_bar_chat_room.*
import kotlinx.android.synthetic.main.fragment_members.*
import javax.inject.Inject

fun newInstance(chatRoomId: String): Fragment = AppcenterFragment().apply {
    arguments = Bundle(1).apply {
        putString(BUNDLE_APP_CENTER_ID, chatRoomId)
    }
}

internal const val TAG_APPCENTER_FRAGMENT = "AppCenterFragment"
private const val BUNDLE_APP_CENTER_ID = "app_center_id"

class AppcenterFragment : Fragment(), AppcenterView {

    @Inject lateinit var presenter: AppcenterPresenter
    @Inject lateinit var analayticsManager: AnalyticsManager
    private lateinit var chatRoomId: String
    private lateinit var adapter : AppcenterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        arguments?.run {
            chatRoomId = getString(BUNDLE_APP_CENTER_ID)
        } ?: requireNotNull(arguments) {"No arguments supplied when the fragment was instantiated"}
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = container?.inflate(R.layout.fragment_appcenter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun showGames(games: List<Game>) {
        ui {
            if (recycler_view.adapter == null) {
                adapter = AppcenterAdapter(games)
                recycler_view.adapter = adapter
                val linearLayoutManager = LinearLayoutManager(context)
                recycler_view.layoutManager = linearLayoutManager
                recycler_view.itemAnimator = DefaultItemAnimator()

            }
        }
        setupToolbar()
    }


    override fun onResume() {
        super.onResume()
        with(presenter) {
            loadGames(chatRoomId)
        }
    }

    override fun showLoading() {
        ui { view_loading.isVisible = true }
    }

    override fun hideLoading() {
        ui { view_loading.isVisible = false }
    }

    override fun showMessage(resId: Int) {
        ui { showToast(resId) }
    }

    override fun showMessage(message: String) {
        ui { showToast(message) }
    }

    override fun showGenericErrorMessage() = showMessage(getString(R.string.msg_generic_error))

    private fun setupToolbar(totalMembers: Long? = null) {
        with((activity as ChatRoomActivity)) {
            setupToolbarTitle("App center")
            this.clearLightStatusBar()
            toolbar.isVisible = true
        }
    }
}