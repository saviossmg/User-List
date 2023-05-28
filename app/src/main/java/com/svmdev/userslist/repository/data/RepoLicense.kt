package com.svmdev.userslist.repository.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepoLicense(
    @SerializedName("key")
    val key: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("spdx_id")
    val spdxID: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("node_id")
    val nodeID: String
) : Serializable
