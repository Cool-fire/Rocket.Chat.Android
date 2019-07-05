package chat.rocket.android.chatroom.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_message_block.view.*
import timber.log.Timber

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

            //elements
            Timber.d("elements - $data")
            Timber.d("elements - ${data.hasElements}")
            elements_list.isVisible = data.hasElements
            if(data.hasElements) {
                bindElements(data)
            }
        }
    }

    private fun bindElements(data: BlockUiModel) {
        Timber.d("elements - ${data.elements}")
        with(itemView) {
            elements_list.layoutManager = LinearLayoutManager(itemView.context)
            elements_list.adapter = data.elements?.let { ElementsListAdapter(it) }
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

    interface BaseElementViewHolder {
        fun bindElement(data : Element)
    }
}