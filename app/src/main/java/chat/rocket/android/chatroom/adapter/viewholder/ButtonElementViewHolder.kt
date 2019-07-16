package chat.rocket.android.chatroom.adapter.viewholder

import android.content.res.Resources
import android.view.View
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.adapter.BlockElementOnClicklistener
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.elements.Element
import kotlinx.android.synthetic.main.item_block_button.view.*

class ButtonElementViewHolder (
        itemView: View
): RecyclerView.ViewHolder(itemView), BaseElementViewHolder {

    override fun bindViews(element: Element, listener: BlockElementOnClicklistener, data: BlockUiModel) {
        val buttonElement = element as ButtonElement
        with(itemView) {
            element_button.text = buttonElement.text.text
            element_button.setOnClickListener {
                listener.onButtonElementClicked(it, element, data)
            }

            buttonElement.style?.let { style ->
                bindButtonColor(style)
            }
        }
    }

    private fun bindButtonColor(style: String) {
        var color: Int
        when(style) {
            "primary" -> {
                color = R.color.button_primary
                bindColor(color, color)
            }
            "danger" -> {
                color = R.color.button_danger
                bindColor(color, color)
            }
            else -> {
                color = R.color.button_stroke
                bindColor(color,android.R.color.black)
            }
        }
    }

    private fun bindColor(strokeColor: Int, buttonColor: Int) {
        with(itemView) {
            element_button.strokeColor = resources.getColorStateList(strokeColor)
            element_button.setTextColor(resources.getColor(buttonColor))
            element_button.setPadding(dpTopx(18))
        }
    }

    private fun dpTopx(dp: Int): Int {
        return (dp * (Resources.getSystem().displayMetrics.densityDpi)/160f).toInt()
    }
}