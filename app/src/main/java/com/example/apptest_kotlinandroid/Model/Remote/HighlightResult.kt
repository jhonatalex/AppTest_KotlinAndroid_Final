package com.example.apptest_kotlinandroid.Model.Remote


import com.google.gson.annotations.SerializedName

data class HighlightResult(
    @SerializedName("author")
    val author: Author,
    @SerializedName("comment_text")
    val commentText: CommentText,
    @SerializedName("story_title")
    val storyTitle: StoryTitle,
    @SerializedName("story_url")
    val storyUrl: StoryUrl
)