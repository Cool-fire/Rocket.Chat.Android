package chat.rocket.android.db.typeconverters

import androidx.room.TypeConverter
import chat.rocket.core.model.block.elements.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import java.util.*

class BlockElementListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromElementListtoString(list: List<Element>?): String? {
        return if(list == null){
            null
        } else {
            gson.toJson(list)
        }
    }

    @TypeConverter
    fun fromStringToELementList(data: String?): List<Element>? {
        return if(data == null) {
            Collections.emptyList()
        } else {
            val moshi = Moshi.Builder()
                    .add(PolymorphicJsonAdapterFactory.of(Element::class.java, "type")
                            .withSubtype(ButtonElement::class.java, "button")
                            .withSubtype(OverflowElement::class.java, "overflow")
                            .withSubtype(DatePickerElement::class.java, "datepicker")
                            .withSubtype(ImageElement::class.java, "image")).build()

            val types = Types.newParameterizedType(List::class.java, Element::class.java)
            val adapter: JsonAdapter<List<Element>> = moshi.adapter(types)
            adapter.fromJson(data)
        }
    }
}