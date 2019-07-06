package chat.rocket.android.chatroom.adapter

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import chat.rocket.android.R
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_message_block.view.*
import ru.noties.markwon.Markwon
import timber.log.Timber

class BlockViewHolder(
        itemView: View,
        listener: ActionsListener,
        reactionListener: EmojiReactionListener? = null,
        var accessoryElementOnClicklistener: AccessoryElementOnClicklistener
) : BaseViewHolder<BlockUiModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            setupActionMenu(block_container)
        }
    }

    override fun bindViews(data: BlockUiModel) {
        Timber.d("BlockUimodel - ${data.type}")
        when(data.type) {
            "section" -> bindSectionBlock(data)
        }
    }

    private fun bindSectionBlock(data: BlockUiModel) {
        with(itemView) {
            //Text
            section_text.isVisible = data.hasText
            if(data.text != null) {
                when(data.text.type) {
                    "mrkdwn" -> {
                        val text = data.text.text
                        mapMarkdown(section_text, text)
                    }
                    "plain_text" -> {
                        section_text.text = data.text.text
                    }
                }
            }


            //accessory
            if(data.hasAccesory){
                Timber.d("BlockUimodel - ${data.accessory}")
                val accessory = data.accessory
                if (accessory != null) {
                    if(accessory.type == "button") {
                        bindButton(accessory)
                    }
                    else {
                        accessory_button.isVisible = false
                    }
                }
            }
        }
    }

    private fun mapMarkdown(textView: TextView?, text: String) {
            if(textView != null) {
                Markwon.setMarkdown(textView, text);
            }
    }

    private fun bindButton(element: Element) {
        val buttonElement = element as ButtonElement
        with(itemView) {
            accessory_button.isVisible = true
            accessory_button.text = buttonElement.text.text
            accessory_button.setOnClickListener {
                accessoryElementOnClicklistener.onButtonElementClicked(it, buttonElement)
            }
        }

        buttonElement.style?.let { style ->
            bindButtonColor(style)
        }
    }

    private fun bindButtonColor(style: String) {
        var color: Int
        when(style) {
            "primary" -> {
                color = R.color.button_primary
                bindColor(color)
            }
            "danger" -> {
                color = R.color.button_danger
                bindColor(color)
            }
        }
    }

    private fun bindColor(color: Int) {
        with(itemView) {
            accessory_button.strokeColor = resources.getColorStateList(color)
            accessory_button.setTextColor(resources.getColor(color))
            accessory_button.setPadding(dpTopx(18))
        }
    }

    private fun dpTopx(dp: Int): Int {
        return (dp * (Resources.getSystem().displayMetrics.densityDpi)/160f).toInt()
    }
}

interface AccessoryElementOnClicklistener {
    fun onButtonElementClicked(view: View, element: ButtonElement)
}