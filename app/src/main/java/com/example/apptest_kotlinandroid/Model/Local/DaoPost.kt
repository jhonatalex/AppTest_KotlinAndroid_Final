package com.example.apptest_kotlinandroid.Model.Local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DaoPost {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPost(list: MutableList<Post>)

    @Query("SELECT * FROM post")
    fun getAllPost(): LiveData<MutableList<Post>>

    @Delete
    suspend fun deleteOnePost(post: Post)

}