package chat.rocket.android.chatroom.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.uimodel.toViewType
import chat.rocket.android.util.extensions.inflate
import chat.rocket.core.model.Message
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_element_button.view.*
import timber.log.Timber

class ElementsListAdapter(
        elements: List<Element>,
        elementBlockOnClicklistener: ElementBlockOnClicklistener,
        message: Message
): RecyclerView.Adapter<ElementViewHolder>() {

    private var elements: List<Element> = elements

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val view = parent.inflate(R.layout.item_element_button)
        return ElementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = elements[position]
        holder.bindElement(element)
    }

}

class ElementViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
    fun bindElement(element: Element) {
        val buttonElement = element as ButtonElement
        with(itemView){
            element_button_text.text = "This is a Block Element"
            element_button.text = buttonElement.text
        }
    }

}
