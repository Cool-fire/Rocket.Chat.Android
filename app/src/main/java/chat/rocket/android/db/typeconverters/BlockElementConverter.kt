package chat.rocket.android.db.typeconverters

import androidx.room.TypeConverter
import chat.rocket.core.model.block.elements.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory

class BlockElementConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromElementtoString(element: Element?): String? {
        return if(element == null){
            null
        } else {
            gson.toJson(element)
        }
    }

    @TypeConverter
    fun fromStringToElement(data: String?): Element?  {
        return if(data == null) {
            return null
        } else {
            val moshi = Moshi.Builder()
                    .add(PolymorphicJsonAdapterFactory.of(Element::class.java, "type")
                    .withSubtype(ButtonElement::class.java, "button")
                    .withSubtype(OverflowElement::class.java, "overflow")
                    .withSubtype(DatePickerElement::class.java, "datepicker")
                    .withSubtype(ImageElement::class.java, "image")).build()
            val adapter: JsonAdapter<Element> = moshi.adapter(Element::class.java)
            adapter.fromJson(data)
        }
    }
}