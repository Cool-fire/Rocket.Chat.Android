package chat.rocket.android.chatroom.adapter

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_message_block.view.*
import ru.noties.markwon.Markwon

class BlockViewHolder(
        itemView: View,
        listener: ActionsListener,
        reactionListener: EmojiReactionListener? = null,
        var blockElementOnClicklistener: BlockElementOnClicklistener
) : BaseViewHolder<BlockUiModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            setupActionMenu(block_container)
        }
    }

    override fun bindViews(data: BlockUiModel) {
        when(data.type) {
            "section" -> bindSectionBlock(data)
            "actions" -> bindActionBlock(data)
        }
    }

    private fun bindActionBlock(data: BlockUiModel) {
        if(data.hasElements) {
            with(itemView) {
                elements_list.layoutManager = LinearLayoutManager(
                        itemView.context,
                        RecyclerView.VERTICAL,
                        false
                )
                if(data.elements != null) {
                    elements_list.isVisible = true
                    elements_list.adapter = ElementsListAdapter(data.elements, blockElementOnClicklistener, data)
                }
            }
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
                    else -> {
                        section_text.text = data.text.text
                    }
                }
            }


            //accessory
            if(data.hasAccesory){
                val accessory = data.accessory
                if (accessory != null) {
                    if(accessory.type == "button") {
                        bindButton(accessory, data)
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

    private fun bindButton(element: Element, data: BlockUiModel) {
        val buttonElement = element as ButtonElement
        with(itemView) {
            accessory_button.isVisible = true
            accessory_button.text = buttonElement.text.text
            accessory_button.setOnClickListener {
                blockElementOnClicklistener.onButtonElementClicked(it, buttonElement, data)
            }
        }

        buttonElement.style?.let { style ->
            bindButtonColor(style)
        }
    }

    private fun bindButtonColor(style: String) {
        val color: Int
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

interface BlockElementOnClicklistener {
    fun onButtonElementClicked(view: View, element: ButtonElement, data: BlockUiModel)
}