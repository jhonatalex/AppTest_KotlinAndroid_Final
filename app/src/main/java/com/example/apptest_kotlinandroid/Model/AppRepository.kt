package com.example.apptest_kotlinandroid.Model

import android.util.Log
import com.example.apptest_kotlinandroid.Model.Local.DaoPost
import com.example.apptest_kotlinandroid.Model.Local.Post
import com.example.apptest_kotlinandroid.Model.Remote.Hit
import com.example.apptest_kotlinandroid.Model.Remote.RetrofitApiClient
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class AppRepository  (private val myDao:DaoPost){


    private val myRetrofit=RetrofitApiClient.retrofitInstance()
    val listPost=myDao.getAllPost()


    suspend fun deleteOnePost(post:Post){
        myDao.deleteOnePost(post)
    }


    fun getDataFromApi()= CoroutineScope(Dispatchers.IO).launch{


        val service= kotlin.runCatching { myRetrofit.fetchAllPost()}
        service.onSuccess { it ->
            when(it.code()) {
                in 200..299 -> it.body()?.let {

                   val postData= convertHitsToEntityPost(it.hits)
                   myDao.insertAllPost(postData)

                }

                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())
            }
        }

        service.onFailure {
            Log.e("ERROR", it.message.toString())

        }

    }

    private fun convertHitsToEntityPost(hits: List<Hit>): MutableList<Post>  {

        val  listMutable: MutableList<Post> = ArrayList()
        hits.map {
            var storyNotNull = if( it.storyUrl!= null) { it.storyUrl } else{ "https://www.google.com/" }

            var storyNTitle = if( it.storyTitle!= null) { it.storyTitle } else{ "Title no Available" }

            var storyID = if( it.storyId!= null) { it.storyId } else{ Random.nextInt(0, 1000)  }

            var author = if( it.author!= null) { it.author } else{ "Unknown" }

            var id = if( it.parentId!= null) { it.parentId } else{ Random.nextInt(0, 100)  }

            var comentTextNoNull = if( it.commentText!= null) { it.commentText } else{ "No text " }

            var createdAtNotNull = if( it.createdAt!= null) { it.createdAt } else{ "Date No Available" }

            listMutable.add(Post( id, storyID, author, comentTextNoNull ,createdAtNotNull,
            storyNTitle ,storyNotNull ))
        }
        return  listMutable

    }


}




