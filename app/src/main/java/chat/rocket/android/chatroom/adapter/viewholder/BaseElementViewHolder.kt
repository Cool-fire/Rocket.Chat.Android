package chat.rocket.android.chatroom.adapter.viewholder

import chat.rocket.android.chatroom.adapter.BlockElementOnClicklistener
import chat.rocket.android.chatroom.uimodel.BlockUiModel
import chat.rocket.core.model.block.elements.Element

interface BaseElementViewHolder {
    fun bindViews(element: Element, listener: BlockElementOnClicklistener, data: BlockUiModel)
}