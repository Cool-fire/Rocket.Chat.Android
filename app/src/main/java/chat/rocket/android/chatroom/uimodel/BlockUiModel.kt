package chat.rocket.android.chatroom.uimodel

import chat.rocket.core.model.Message
import chat.rocket.core.model.block.Block
import chat.rocket.core.model.block.elements.Element

data class BlockUiModel(
        override val message: Message,
        override val rawData: Block,
        override val messageId: String,
        override var reactions: List<ReactionUiModel>,
        override var nextDownStreamMessage: BaseUiModel<*>? = null,
        override var preview: Message?,
        override var isTemporary: Boolean,
        override var unread: Boolean?,
        override var currentDayMarkerText: String,
        override var showDayMarker: Boolean,
        override var menuItemsToHide: MutableList<Int> = mutableListOf(),
        override var permalink: String,
        val type: String?,
        val text: String?,
        val blockId: String?,
        val accessory: Element?,
        val elements: List<Element>?

) : BaseUiModel<Block> {
    override val viewType: Int
        get() = BaseUiModel.ViewType.BLOCK.viewType
    override val layoutId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}