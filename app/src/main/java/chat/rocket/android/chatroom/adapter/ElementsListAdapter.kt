package chat.rocket.android.chatroom.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.util.extensions.inflate
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import timber.log.Timber
import java.security.InvalidParameterException

class ElementsListAdapter (
        var elements: List<Element>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val BUTTON = 0
        const val NONE = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            BUTTON -> {
                Timber.d("Element button")
                val view = parent.inflate(R.layout.item_block_button)
                ButtonElementViewHolder(view)
            }
            else -> {
                throw InvalidParameterException("TODO - implement No block Element")
            }
        }
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
           is ButtonElementViewHolder -> {
               Timber.d("element - position-$position")
               holder.bindElement(elements[position] as ButtonElement)
           }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(elements[position].type) {
            "button" -> BUTTON
            else -> NONE
        }
    }

}