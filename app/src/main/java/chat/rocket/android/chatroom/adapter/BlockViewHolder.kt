package chat.rocket.android.chatroom.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.core.model.Message
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_message_block.view.*
import kotlinx.android.synthetic.main.item_message_block.view.quote_bar
import timber.log.Timber

class BlockViewHolder (itemView: View,
                       listener: ActionsListener,
                       reactionListener: EmojiReactionListener? = null,
                       var elementBlockOnClicklistener: ElementBlockOnClicklistener ): BaseViewHolder<BlockUiModel>(itemView, listener, reactionListener) {

    init {
        with(itemView) {
            setupActionMenu(block_container)
        }
    }

    private val quoteBarColor = ContextCompat.getColor(itemView.context, R.color.quoteBar)

    override fun bindViews(data: BlockUiModel) {
        with(itemView){
            Timber.d("block ${data.message}")
            block_type.text = "Block Message"
            bindAccessory(data)

            quote_bar.isVisible = shouldShowQuoteBar(data)
            quote_bar.setColorFilter(quoteBarColor)
        }
    }

    private fun shouldShowQuoteBar(data: BlockUiModel): Boolean {
        return data.hasElements || data.hasElements
    }

    private fun bindAccessory(data: BlockUiModel) {
        Timber.d("bind Accessory to recyclerview")
        val message = data.message
        val accessory = data.accessory
        with(itemView) {
            blocks_list.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            accessory?.let {
                blocks_list.adapter = ElementsListAdapter(listOf(accessory), elementBlockOnClicklistener, message)
            }
        }
    }
}

interface ElementBlockOnClicklistener {
    fun onElementClicked(view: View, element: Element,message: Message)
}
