package chat.rocket.android.chatroom.adapter

import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.android.emoji.EmojiReactionListener
import chat.rocket.android.util.extensions.isVisible
import chat.rocket.core.model.block.elements.*
import chat.rocket.core.model.block.objects.OptionObject
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.item_message_attachment.view.*
import kotlinx.android.synthetic.main.item_message_block.view.*
import ru.noties.markwon.Markwon
import timber.log.Timber

class BlockViewHolder(
        itemView: View,
        listener: ActionsListener,
        reactionListener: EmojiReactionListener? = null,
        var blockElementOnClicklistener: BlockElementOnClicklistener
) : BaseViewHolder<BlockUiModel>(itemView, listener, reactionListener) {

    private val sectionViews = listOf<View>(
            itemView.section_text,
            itemView.accessory_button,
            itemView.accessory_overflow,
            itemView.accessory_datepicker,
            itemView.accessory_image
    )

    private val actionViews = listOf<View>(
            itemView.elements_list
    )

    init {
        with(itemView) {
            setupActionMenu(block_container)
        }
    }

    override fun bindViews(data: BlockUiModel) {
        when(data.type) {
            "section" -> {
                actionViews.isVisible = false
                bindSectionBlock(data)
            }
            "actions" -> {
                sectionViews.isVisible = false
                bindActionBlock(data)
            }
        }
    }

    private fun bindActionBlock(data: BlockUiModel) {
        if(data.hasElements) {
            with(itemView) {
                elements_list.layoutManager = LinearLayoutManager(
                        itemView.context,
                        RecyclerView.VERTICAL,
                        false
                )
                if(data.elements != null) {
                    elements_list.isVisible = true
                    elements_list.adapter = ElementsListAdapter(data.elements, blockElementOnClicklistener, data)
                }
            }
        }
    }

    private fun bindSectionBlock(data: BlockUiModel) {
        with(itemView) {
            //Text
            section_text.isVisible = data.hasText
            if(data.text != null) {
                when(data.text.type) {
                    "mrkdwn" -> {
                        val text = data.text.text
                        mapMarkdown(section_text, text)
                    }
                    "plain_text" -> {
                        section_text.text = data.text.text
                    }
                    else -> {
                        section_text.text = data.text.text
                    }
                }
            }


            //accessory
            if(data.hasAccesory){
                val accessory = data.accessory
                if (accessory != null) {
                    when {
                        accessory.type == "button" -> bindButton(accessory, data)
                        accessory.type == "overflow" -> bindOverflowMenu(accessory, data)
                        accessory.type == "datepicker" -> bindDatePicker(accessory, data)
                        accessory.type == "image" -> bindImage(accessory, data)
                    }
                }
            }
        }
    }

    private fun bindImage(accessory: Element, data: BlockUiModel) {
        val imageElement = accessory as ImageElement
        with(itemView) {
            accessory_image.isVisible = true
            val controller = Fresco.newDraweeControllerBuilder().apply {
                setUri(imageElement.imageUrl)
                autoPlayAnimations = true
                oldController = accessory_image.controller
            }.build()
            accessory_image.controller = controller
        }
    }

    private fun bindDatePicker(accessory: Element, data: BlockUiModel) {
        val datePickerElement = accessory as DatePickerElement
        val placeholder = datePickerElement.placeholder
        with(itemView) {
            accessory_datepicker.isVisible = true
            if(placeholder != null) {
                accessory_datepicker.text = placeholder.text
            }

            accessory_datepicker.setOnClickListener {
                blockElementOnClicklistener.onDatePickerElementClicked(it, datePickerElement, data, blockElementOnClicklistener)
            }
        }
    }

    private fun bindOverflowMenu(element: Element, data: BlockUiModel) {
        val overflowElement = element as OverflowElement
        with(itemView) {
            accessory_overflow.isVisible = true
            accessory_overflow.setOnClickListener {
                blockElementOnClicklistener.onOverflowElementClicked(it, overflowElement, data, blockElementOnClicklistener)
            }
        }
    }

    private fun mapMarkdown(textView: TextView?, text: String) {
            if(textView != null) {
                Markwon.setMarkdown(textView, text);
            }
    }

    private fun bindButton(element: Element, data: BlockUiModel) {
        val buttonElement = element as ButtonElement
        with(itemView) {
            accessory_button.isVisible = true
            accessory_button.text = buttonElement.text.text
            accessory_button.setOnClickListener {
                blockElementOnClicklistener.onButtonElementClicked(it, buttonElement, data)
            }
        }

        buttonElement.style?.let { style ->
            bindButtonColor(style)
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
                bindColor(color, android.R.color.black)
            }
        }
    }

    private fun bindColor(strokeColor: Int, buttonColor: Int) {
        with(itemView) {
            accessory_button.strokeColor = resources.getColorStateList(strokeColor)
            accessory_button.setTextColor(resources.getColor(buttonColor))
            accessory_button.setPadding(dpTopx(18))
        }
    }

    private fun dpTopx(dp: Int): Int {
        return (dp * (Resources.getSystem().displayMetrics.densityDpi)/160f).toInt()
    }
}

interface BlockElementOnClicklistener {

    fun onButtonElementClicked(view: View, element: ButtonElement, data: BlockUiModel)

    fun onOverflowElementClicked(view: View, element: OverflowElement, data: BlockUiModel, blockElementOnClicklistener: BlockElementOnClicklistener)

    fun onDatePickerElementClicked(view: View, datePickerElement: DatePickerElement, data: BlockUiModel, blockElementOnClicklistener: BlockElementOnClicklistener)

    fun onOverFlowOptionClicked(option: OptionObject, overflowElement: OverflowElement, data: BlockUiModel, view: View)

    fun onDateSelected(selectedDate: String, datePickerElement: DatePickerElement, data: BlockUiModel, listener: BlockElementOnClicklistener)
}