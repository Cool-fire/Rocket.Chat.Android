package chat.rocket.android.chatroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.adapter.viewholder.ButtonElementViewHolder
import chat.rocket.android.chatroom.adapter.viewholder.DatePickerElementViewHolder
import chat.rocket.android.chatroom.adapter.viewholder.OverflowElementViewHolder
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.DatePickerElement
import chat.rocket.core.model.block.elements.Element
import chat.rocket.core.model.block.elements.OverflowElement
import timber.log.Timber
import java.security.InvalidParameterException

class ElementsListAdapter(
        var elements: List<Element>,
        var listener: BlockElementOnClicklistener,
        var data: BlockUiModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val BUTTON = 0
        const val OVERFLOW = 1
        const val DATEPICKER = 2
        const val NONE = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            BUTTON -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_button, parent, false)
                ButtonElementViewHolder(view)
            }
            OVERFLOW -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_overflow, parent, false)
                OverflowElementViewHolder(view)
            }
            DATEPICKER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_datepicker, parent, false)
                DatePickerElementViewHolder(view)
            }
            else -> {
                throw InvalidParameterException("TODO - implement No block Element")
            }
        }
    }

    override fun getItemCount(): Int = elements.size

    override fun getItemViewType(position: Int): Int {

        return when(elements[position].type) {
            "button" -> BUTTON
            "overflow" -> OVERFLOW
            "datepicker" -> DATEPICKER
            else -> NONE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ButtonElementViewHolder -> holder.bindViews(elements[position] as ButtonElement, listener, data)
            is OverflowElementViewHolder -> holder.bindViews(elements[position] as OverflowElement, listener, data)
            is DatePickerElementViewHolder -> holder.bindViews(elements[position] as DatePickerElement, listener, data)
        }
    }

}