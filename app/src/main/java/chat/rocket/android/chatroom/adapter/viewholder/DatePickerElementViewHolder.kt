package chat.rocket.android.chatroom.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.chatroom.adapter.BlockElementOnClicklistener
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.core.model.block.elements.DatePickerElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_block_datepicker.view.*
import timber.log.Timber

class DatePickerElementViewHolder(
        itemView: View
): RecyclerView.ViewHolder(itemView), BaseElementViewHolder {
    override fun bindViews(element: Element, listener: BlockElementOnClicklistener, data: BlockUiModel) {
        val datePickerElement = element as DatePickerElement
        Timber.d("$datePickerElement")
        val placeholder = datePickerElement.placeholder
        val initialDate = datePickerElement.initialDate
        with(itemView) {
            element_datepicker.isVisible = true
            if(placeholder != null) {
                element_datepicker.text = placeholder.text
            }

            element_datepicker.setOnClickListener {
                listener.onDatePickerElementClicked(it, datePickerElement, data, listener)
            }
        }
    }
}