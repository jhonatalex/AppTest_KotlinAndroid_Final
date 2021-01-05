package com.example.apptest_kotlinandroid.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apptest_kotlinandroid.Model.AppRepository
import com.example.apptest_kotlinandroid.Model.Local.DataBase
import com.example.apptest_kotlinandroid.Model.Local.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel (application: Application): AndroidViewModel(application) {

    private val myRepository: AppRepository

    val election= MutableLiveData<Post>()
    val viewListPosts: LiveData<MutableList<Post>>


    init {
        val nDao = DataBase.getDatabase(application).daoPosts()
        myRepository = AppRepository(nDao)

        viewListPosts = myRepository.listPost
        myRepository.getDataFromApi()

    }


    fun  postDetailSelect  (post: Post){
        election.value=post
    }


    fun  updateDataFromApi (){
        myRepository.getDataFromApi()
    }

    fun deleteOnePost(post:Post)= CoroutineScope(Dispatchers.IO).launch{
        myRepository.deleteOnePost(post)
    }



}