package com.example.myapplication.models.marks

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class AccountStates(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("favorite")
    val favorite: Boolean? = false,

    var rating: RatingValue = RatingValue(),

    @SerializedName("watchlist")
    val watchlist: Boolean? = false
) {
    class AccountStatesDeserializer : JsonDeserializer<AccountStates?> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): AccountStates {
            val accountStates: AccountStates = Gson().fromJson(json, AccountStates::class.java)
            val jsonObject = json.asJsonObject
            if (jsonObject.has("rated")) {
                val elem = jsonObject["rated"]
                if (elem != null && !elem.isJsonNull) {
                    val valuesString = elem.toString()
                    if (valuesString.isNotEmpty()) {
                        if(valuesString != "false") {
                            val gson = GsonBuilder().create()
                            val rating: RatingValue = gson.fromJson(valuesString, RatingValue::class.java)
                            accountStates.rating = rating
                        }
                    }
                }
            }
            return accountStates
        }
    }
}
