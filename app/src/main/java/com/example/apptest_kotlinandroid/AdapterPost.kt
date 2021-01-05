package com.example.apptest_kotlinandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptest_kotlinandroid.Model.Local.Post
import kotlinx.android.synthetic.main.item_post.view.*

public class AdapterPost (val callback: dataPass):RecyclerView.Adapter<AdapterPost.PostViewHolder>() {


    private var listPosts: MutableList<Post> = ArrayList()

    fun updateAdapter(list:MutableList<Post>){
        listPosts=list;
        notifyDataSetChanged()
    }



    inner class PostViewHolder(itemVista: View) : RecyclerView.ViewHolder (itemVista){

        val itemTitle: TextView =itemVista.post_title
        val itemAuthor: TextView =itemVista.post_author


        val click= itemVista.setOnClickListener {
            callback.passItem(listPosts[adapterPosition])

        }
    }

    interface  dataPass{
        fun passItem(post:Post)

    }

    fun getPost(position:Int):Post{
        return listPosts[position]

    }

    fun deletePost(position:Int){
        listPosts.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restorePost(item:Post,position:Int){
        listPosts.add(position,item)
        notifyItemInserted(position)
    }


    fun changePositionItem(starPosition:Int, finalPosition:Int){
        val pop=listPosts[starPosition]
        listPosts.removeAt(starPosition)
        listPosts.add(finalPosition,pop)
        notifyItemMoved(starPosition,finalPosition)

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.itemTitle.text=listPosts[position].storyTitle
        holder.itemAuthor.text= " ${listPosts[position].author}  -   ${listPosts[position].createdAt}  "


    }

    override fun getItemCount()= listPosts.size


















}