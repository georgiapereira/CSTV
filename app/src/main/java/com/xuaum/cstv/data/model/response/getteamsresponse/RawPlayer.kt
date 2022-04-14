package com.xuaum.cstv.data.model.response.getteamsresponse

import com.google.gson.annotations.SerializedName

data class RawPlayer(
    val age: Any,
    val birth_year: Any,
    val birthday: Any,
    val first_name: String?,
    val hometown: Any,
    val id: Int,
    val image_url: String?,
    val last_name: String?,
    @SerializedName("name") val nickname: String,
    val nationality: String,
    val role: Any,
    val slug: String
)