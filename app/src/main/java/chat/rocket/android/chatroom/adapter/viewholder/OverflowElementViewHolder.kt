package chat.rocket.android.chatroom.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.chatroom.adapter.BlockElementOnClicklistener
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.core.model.block.elements.Element
import chat.rocket.core.model.block.elements.OverflowElement
import kotlinx.android.synthetic.main.item_block_overflow.view.*

class OverflowElementViewHolder(
        itemView: View
): RecyclerView.ViewHolder(itemView), BaseElementViewHolder {
    override fun bindViews(element: Element, listener: BlockElementOnClicklistener, data: BlockUiModel) {
        val overflowElement = element as OverflowElement
        with(itemView) {
            element_overflow.text = "More Options"
            element_overflow.setOnClickListener {
                listener.onOverflowElementClicked(it, overflowElement, data, listener)
            }
        }
    }
}