package com.example.apptest_kotlinandroid.Model.Remote


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("fullyHighlighted")
    val fullyHighlighted: Boolean,
    @SerializedName("matchLevel")
    val matchLevel: String,
    @SerializedName("matchedWords")
    val matchedWords: List<String>,
    @SerializedName("value")
    val value: String
)