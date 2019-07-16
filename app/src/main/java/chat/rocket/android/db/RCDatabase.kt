package chat.rocket.android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import chat.rocket.android.db.model.*
import chat.rocket.android.emoji.internal.db.StringListConverter

@Database(
    entities = [
        UserEntity::class,
        ChatRoomEntity::class,
        MessageEntity::class,
        MessageFavoritesRelation::class,
        MessageMentionsRelation::class,
        MessageChannels::class,
        AttachmentEntity::class,
        AttachmentFieldEntity::class,
        AttachmentActionEntity::class,
        BlockEntity::class,
        BlockButtonElementEntity::class,
        UrlEntity::class,
        ReactionEntity::class,
        MessagesSync::class
    ],
    version = 13,
    exportSchema = true
)
@TypeConverters(StringListConverter::class)
abstract class RCDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun messageDao(): MessageDao
}
