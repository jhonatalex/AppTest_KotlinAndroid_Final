package com.example.apptest_kotlinandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.apptest_kotlinandroid.ViewModel.AppViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private val myViewModel: AppViewModel by activityViewModels()
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myViewModel.election.observe(viewLifecycleOwner, Observer {
            url=it.storyUrl

            webView.webChromeClient = object : WebChromeClient() {

            }

            webView.webViewClient=object :WebViewClient(){

            }

            detail_title.text=it.storyTitle
            val settings=webView.settings
            settings.javaScriptEnabled=true


            webView.loadUrl(url)


        })




        view.button_second.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }


    }
}