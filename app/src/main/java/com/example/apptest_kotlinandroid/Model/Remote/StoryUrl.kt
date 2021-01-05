package com.example.apptest_kotlinandroid.Model.Remote


import com.google.gson.annotations.SerializedName

data class StoryUrl(
    @SerializedName("matchLevel")
    val matchLevel: String,
    @SerializedName("matchedWords")
    val matchedWords: List<Any>,
    @SerializedName("value")
    val value: String
)