package chat.rocket.android.chatroom.uimodel

import chat.rocket.android.R
import chat.rocket.android.util.extensions.isNotNullNorEmpty
import chat.rocket.core.model.Message
import chat.rocket.core.model.block.Block
import chat.rocket.core.model.block.elements.Element
import chat.rocket.core.model.block.objects.TextObject

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
        val id: Long,
        val type: String,
        val text: TextObject?,
        val blockId: String?,
        val accessory: Element?,
        val elements: List<Element>?
) : BaseUiModel<Block> {
    override val viewType: Int
        get() = BaseUiModel.ViewType.BLOCK.viewType
    override val layoutId: Int
        get() = R.layout.item_message_block
    val hasText: Boolean
        get() = text != null
    val hasAccesory: Boolean
        get() = accessory != null
    val hasElements: Boolean
        get() = elements != null && elements.isNotEmpty()
}