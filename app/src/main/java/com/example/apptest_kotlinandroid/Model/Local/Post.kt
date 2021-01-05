package com.example.apptest_kotlinandroid.Model.Local

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apptest_kotlinandroid.Model.Remote.HighlightResult
import com.google.gson.annotations.SerializedName


@Entity(tableName= "post")
data class  Post(@PrimaryKey
     @SerializedName("parent_id")
     val parentId: Int,
     @SerializedName("story_id")
     val storyId: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("comment_text")
    @Nullable
    val commentText: String,
    @SerializedName("created_at")
    @Nullable
    val createdAt: String,
    @SerializedName("story_title")
    val storyTitle: String,
    @SerializedName("story_url")
    val storyUrl: String
    ) {
}