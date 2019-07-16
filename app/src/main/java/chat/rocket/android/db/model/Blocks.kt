package chat.rocket.android.db.model

import android.content.Context
import androidx.room.*
import chat.rocket.core.model.block.ActionBlock
import chat.rocket.core.model.block.SectionBlock
import chat.rocket.core.model.block.elements.ButtonElement
import chat.rocket.core.model.block.objects.TextObject
import timber.log.Timber

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
        @Embedded val text: TextObject? = null
): BaseMessageEntity

@Entity(tableName = "block_button_element",
        foreignKeys = [
                ForeignKey(entity = BlockEntity::class, parentColumns = ["_id"],
                       childColumns = ["blockId"], onDelete = ForeignKey.CASCADE )
        ],
        indices = [
        Index(value = ["blockId"])
        ])
data class BlockButtonElementEntity(
        val blockId: String,
        val type: String,
        val actionId: String,
        @ColumnInfo(name = "block_type")
        val blockType: String? = null,
        @Embedded(prefix = "button_") val text: TextObject,
        val url: String? = null,
        val value: String? = null,
        val style: String? = null,
        val confirm: String? = null
): BaseMessageEntity {
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}

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
                hasElements = false
        ))

        when(accessory) {
                is ButtonElement -> {
                        val buttonAccessory = accessory as ButtonElement
                        BlockButtonElementEntity(
                                blockId = blockid,
                                type = buttonAccessory.type,
                                actionId = buttonAccessory.actionId,
                                text = buttonAccessory.text,
                                url = buttonAccessory.url,
                                value = buttonAccessory.value,
                                style = buttonAccessory.style,
                                blockType = "section",
                                confirm = null
                        )
                }
                else -> null
        }?.let { list.add(it) }
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
                hasElements = elements?.isNotEmpty()
        ))

        elements?.forEach {element ->
                when(element) {
                        is ButtonElement -> {
                                BlockButtonElementEntity(
                                        blockId = blockid,
                                        type = element.type,
                                        actionId = element.actionId,
                                        text = element.text,
                                        url = element.url,
                                        value = element.value,
                                        style = element.style,
                                        confirm = null,
                                        blockType = "actions"
                                )
                        }
                        else -> null
                }?.let { list.add(it) }
        }

        return list
}


