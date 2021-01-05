package com.example.apptest_kotlinandroid

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.GRAY
import android.graphics.Color.RED
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptest_kotlinandroid.Model.Local.Post
import com.example.apptest_kotlinandroid.ViewModel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class FirstFragment : Fragment(), AdapterPost.dataPass {


    private val myViewModel: AppViewModel by activityViewModels()
    lateinit var myAdapter: AdapterPost
    lateinit var mainLayout:RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter= AdapterPost(this)
        val recyclerView=view.recycler_post
        recyclerView.layoutManager= LinearLayoutManager(context)
        recyclerView.adapter=myAdapter

        mainLayout =layout_principal

        pullRefresh()

        myViewModel.viewListPosts.observe(viewLifecycleOwner, Observer {

            myAdapter.updateAdapter(it)
        })



        val trashBinIcon = resources.getDrawable(
                R.drawable.ic_delete,
                null
        )



        val itemTouchHelperCallback=object :ItemTouchHelper.Callback(){

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

                return makeMovementFlags(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),ItemTouchHelper.RIGHT)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

                val positionInitial=viewHolder.adapterPosition
                val positionFinal=target.adapterPosition
                myAdapter.changePositionItem(positionInitial,positionFinal)

                return  true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                val item=myAdapter.getPost(position)
                myAdapter.deletePost(viewHolder.adapterPosition)
                myViewModel.deleteOnePost(item)

                val snackBar = Snackbar.make(mainLayout,R.string.You_deleted,Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.Undo) {
                    myAdapter.restorePost(item, position)
                }
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()

            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }



            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                c.clipRect(0f, viewHolder.itemView.top.toFloat(),
                        dX, viewHolder.itemView.bottom.toFloat())

                val textMargin = resources.getDimension(R.dimen.text_margin).roundToInt()
                c.drawColor(RED)

                trashBinIcon.bounds = Rect(
                        textMargin,
                        viewHolder.itemView.top + textMargin,
                        textMargin + trashBinIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                                + textMargin
                )

                trashBinIcon.draw(c)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
             }


        }


        val itemTouchHelper=ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }

    override fun passItem(post: Post) {
        progressBar3.visibility=View.VISIBLE
        myViewModel.postDetailSelect(post)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)



    }


    private fun pullRefresh(){
        swipetorefresh.setOnRefreshListener {
            Toast.makeText(context, "Updating data...",Toast.LENGTH_LONG).show()

            CoroutineScope(Dispatchers.IO).launch{
                myViewModel.updateDataFromApi()
                myAdapter.notifyDataSetChanged()
                Thread.sleep(3000)
                swipetorefresh.isRefreshing = false
            }



        }

    }





}