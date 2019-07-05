package chat.rocket.android.chatroom.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_block_button.view.*
import timber.log.Timber

class ButtonElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BlockViewHolder.BaseElementViewHolder {

    override fun bindElement(data: Element) {
        val buttonElement = data as ButtonElement
        with(itemView) {
            Timber.d("button viewholder")
            element_button.text = buttonElement.text.text
        }
    }

}