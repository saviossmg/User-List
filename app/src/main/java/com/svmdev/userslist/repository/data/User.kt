package com.svmdev.userslist.repository.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeID: String,
    @SerializedName("avatar_url") val avatarURL: String,
    @SerializedName("gravatar_id") val gravatarID: String,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlURL: String,
    @SerializedName("followers_url") val followersURL: String,
    @SerializedName("following_url") val followingURL: String,
    @SerializedName("gists_url") val gistsURL: String,
    @SerializedName("starred_url") val starredURL: String,
    @SerializedName("subscriptions_url") val subscriptionsURL: String,
    @SerializedName("organizations_url") val organizationsURL: String,
    @SerializedName("repos_url") val reposURL: String,
    @SerializedName("events_url") val eventsURL: String,
    @SerializedName("received_events_url") val receivedEventsURL: String,
    @SerializedName("type") val type: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company: String,
    @SerializedName("blog") val blog: String,
    @SerializedName("location") val location: String,
    @SerializedName("public_repos") val publicRepos: Long,
    @SerializedName("public_gists") val publicGists: Long,
    @SerializedName("followers") val followers: Long,
    @SerializedName("following") val following: Long,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
) : Serializable
