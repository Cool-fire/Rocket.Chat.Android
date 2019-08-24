package chat.rocket.android.db.model

import android.content.Context
import androidx.room.*
import chat.rocket.core.model.block.ActionBlock
import chat.rocket.core.model.block.SectionBlock
import chat.rocket.core.model.block.elements.Element
import chat.rocket.core.model.block.objects.TextObject

@Entity(tableName = "blocks",
        foreignKeys = [
            ForeignKey(entity = MessageEntity::class, parentColumns = ["id"],
                    childColumns = ["message_id"], onDelete = ForeignKey.CASCADE)])
data class BlockEntity(
        @PrimaryKey
        var _id: String,
        @ColumnInfo(name = "message_id")
        val messageId: String,
        @ColumnInfo(name = "block_type")
        val type: String? = null,
        @ColumnInfo(name = "block_id")
        val blockId: String? = null,
        @ColumnInfo(name= "has_accessory")
        val hasAccessory: Boolean = false,
        @ColumnInfo(name= "has_elements")
        val hasElements: Boolean = false,
        @Embedded val text: TextObject? = null,
        val accessory: Element?,
        val elements: List<Element>?
): BaseMessageEntity

fun SectionBlock.asEntity(msgId: String, context: Context): List<BaseMessageEntity> {
    val blockid = "${msgId}_${hashCode()}"
    val list = mutableListOf<BaseMessageEntity>()

    list.add(BlockEntity(
            _id = blockid,
            messageId = msgId,
            type = type,
            text = text,
            blockId = blockId,
            hasAccessory = accessory != null,
            hasElements = false,
            accessory = accessory,
            elements = null
    ))
    return list
}

fun ActionBlock.asEntity(msgId: String, context: Context): List<BaseMessageEntity> {
    val blockid = "${msgId}_${hashCode()}"

    val list = mutableListOf<BaseMessageEntity>()

    list.add(BlockEntity(
            _id = blockid,
            messageId = msgId,
            type = type,
            text = null,
            blockId = blockId,
            hasAccessory = false,
            hasElements = elements?.isNotEmpty(),
            elements = elements,
            accessory = null
    ))
    return list
}


