package chat.rocket.android.chatroom.adapter

import android.view.View
import androidx.core.view.isVisible
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_message_block.view.*

class BlockViewHolder(
        itemView: View,
        listener: ActionsListener,
        reactionListener: EmojiReactionListener? = null,
        var accessoryElementOnClicklistener: AccessoryElementOnClicklistener
) : BaseViewHolder<BlockUiModel>(itemView, listener, reactionListener) {

    override fun bindViews(data: BlockUiModel) {
        with(itemView) {

            //Text
            section_text.isVisible = data.hasText
            data.text?.let {
                section_text.text = it.text
            }

            //accessory
            if(data.hasAccesory) {
                data.accessory?.let { element ->
                    when(element.type) {
                        "button" -> {
                            accessoryButton.isVisible = true
                            bindButton(element)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun bindButton(element: Element) {

        val buttonElement = element as ButtonElement
        with(itemView) {
            accessoryButton.text = buttonElement.text.text
            accessoryButton.setOnClickListener {
                accessoryElementOnClicklistener.onButtonElementClicked(it, buttonElement)
            }
        }
    }

    init {
        with(itemView) {
            setupActionMenu(block_container)
        }
    }

    interface AccessoryElementOnClicklistener {
        fun onButtonElementClicked(view: View, element: Element)
    }

}