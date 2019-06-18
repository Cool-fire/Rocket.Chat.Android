package chat.rocket.android.webview.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import chat.rocket.android.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

fun Context.webViewIntent(webPageUrl: String, toolbarTitle: String? = null): Intent {
    return Intent(this, WebViewActivity::class.java).apply {
        putExtra(INTENT_WEB_PAGE_URL, webPageUrl)
        putExtra(TOOLBAR_TITLE, toolbarTitle)
    }
}

private const val INTENT_WEB_PAGE_URL = "web_page_url"
private const val TOOLBAR_TITLE = "toolbar_title"

// Simple WebView to load URL.
class WebViewActivity : AppCompatActivity() {
    private lateinit var webPageUrl: String
    private var toolbarTitle: String? = ""
    private val listenerScript: String
        get() = """(function(){window.addEventListener('message',function(event) {  MyJSInterface.handleMyEvent(event.data);});},false)();"""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webPageUrl = intent.getStringExtra(INTENT_WEB_PAGE_URL)
        requireNotNull(webPageUrl) { "no web_page_url provided in Intent extras" }
        toolbarTitle = intent.getStringExtra(TOOLBAR_TITLE)

        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        setupWebView()
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            finishActivity()
        }
    }

    private fun setupToolbar() {
        toolbar.title = if(toolbarTitle != null) toolbarTitle else webPageUrl.replace("https://","")
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp)
        toolbar.setNavigationOnClickListener {
            finishActivity()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
        web_view.settings.loadWithOverviewMode = true
        web_view.settings.useWideViewPort = true
        web_view.webChromeClient = WebChromeClient()
        web_view.addJavascriptInterface(WebAppInterface(), "MyJSInterface")

        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(listenerScript, ValueCallback {
                    Log.d("mess", "it")
                })
                view_loading.hide()
            }
        }
        web_view.loadUrl(webPageUrl)
    }

    private fun finishActivity() {
        super.onBackPressed()
        overridePendingTransition(R.anim.hold, R.anim.slide_down)
    }

    class WebAppInterface {
        @JavascriptInterface
        fun handleMyEvent(jsonString: String) {
            val data: JSONObject = JSONObject(jsonString)
            Log.d("mess", data.toString())
        }
    }
}