package com.viaplaytest.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RootJson(
    @SerializedName("_links")
    var _links: LinksXX,
    var description: String,
    var pageType: String,
    var responseCode: ResponseCode,
    var styles: List<Any>,
    var title: String,
    var type: String
) : Serializable

data class LinksXX(
    @SerializedName("viaplay:sections")
    internal var viaplaySections: List<ViaplaySection>
    ) : Serializable


@Entity
data class ViaplaySection(

    @PrimaryKey
    @Expose
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("title")
    var title: String,

    @Expose
    @SerializedName("href")
    var href: String,

    @Expose
    @SerializedName("type")
    var type: String,

    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("templated")
    var templated: Boolean
) : Serializable

data class ResponseCode(
    var code: Int,
    var httpStatus: Int,
    var statusCode: Int
) : Serializable