package com.colourchangedemo.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colourchangedemo.R
import com.colourchangedemo.adapter.OpenAIChatAdapter
import com.colourchangedemo.base.BaseActivity
import com.colourchangedemo.databinding.ActivityOpenAisampleBinding
import com.colourchangedemo.flow.resource.Status
import com.colourchangedemo.model.ChatCompletionResponse
import com.colourchangedemo.model.CommonChatModel
import com.colourchangedemo.model.CreateImageResponse
import com.colourchangedemo.model.ModerationResponse
import com.colourchangedemo.util.AppConstants.IMAGE_MEDIA_TYPE
import com.colourchangedemo.util.AppConstants.TEXT_MEDIA_TYPE
import com.colourchangedemo.util.gone
import com.colourchangedemo.util.visible
import com.colourchangedemo.viewmodel.OpenAIViewModel
import com.colourchangedemo.viewmodelfactory.OpenAIViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch


class OpenAISampleActivity : BaseActivity() {

    lateinit var viewModel:OpenAIViewModel

    lateinit var binding:ActivityOpenAisampleBinding



    var list= ArrayList<CommonChatModel>()
    var adapter: OpenAIChatAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOpenAisampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this,OpenAIViewModelFactory(this))[OpenAIViewModel::class.java]
        binding.lifecycleOwner = this
        binding.model = viewModel

        setAdapter()
        with(binding){

            ivSend.setOnClickListener {

                if (etMessage.text.toString().trim().isEmpty()) {
                    viewModel.commonMessage("Please enter message")
                    return@setOnClickListener
                }
                list.add(CommonChatModel(message = etMessage.text.toString().trim(),
                time = System.currentTimeMillis().toString(), isSendMe = true, mediaType = TEXT_MEDIA_TYPE))


                adapter?.notifyDataSetChanged()
                hideKeyboard()
                viewModel.openAIApi(
                    etMessage.text.toString().trim(), binding.pb,
                    if (etMessage.text.toString().trim().contains("image")
                        || etMessage.text.toString().trim().contains("picture")
                        || etMessage.text.toString().trim().contains("photo") ){ IMAGE_MEDIA_TYPE}else{TEXT_MEDIA_TYPE},

                )
                etMessage.setText("")
                binding.rvChat.smoothScrollToPosition(list.size-1)
            }
            tvLetsTalk.setOnClickListener {
                hideFirstTimeView()
                firstTimeSendMessage()
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.textRequestResponse.collect {
                    when (it.status) {
                        Status.SUCCESS -> {

                            it.data?.let { response ->

                                val chatCompletionResponse: ChatCompletionResponse = Gson().fromJson(response.toString(), ChatCompletionResponse::class.java)

                                if (list.size==1){
                                    list.removeAt(0)
                                }
                                chatCompletionResponse.choices.forEach {  itChoices->
                                    list.add(CommonChatModel(message =itChoices.message.content, time = chatCompletionResponse.created.toString(), isSendMe = false, mediaType =  TEXT_MEDIA_TYPE))
                                }

                                adapter!!.notifyDataSetChanged()
                                binding.rvChat.smoothScrollToPosition(list.size-1)
                                Log.e("printResponse",response.toString())


                            }

                        }
                        else -> {

                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageRequestResponse.collect {
                    when (it.status) {
                        Status.SUCCESS -> {

                            it.data?.let { response ->

                                val chatCompletionResponse: CreateImageResponse = Gson().fromJson(response.toString(), CreateImageResponse::class.java)


//                                val imageList= arrayListOf<String>()
//
                                chatCompletionResponse.data.forEach {  itChoices->
                                  //  imageList.add(itChoices.url)
                                    list.add(CommonChatModel(message = itChoices.url, time = chatCompletionResponse.created.toString(), isSendMe = false, mediaType =  IMAGE_MEDIA_TYPE))

                                }




                                adapter!!.notifyDataSetChanged()
                                binding.rvChat.smoothScrollToPosition(list.size-1)
                                Log.e("printResponse",response.toString())

                            }

                        }
                        else -> {

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moderationResponse.collect {
                    when (it.status) {
                        Status.SUCCESS -> {

                            it.data?.let { response ->

                                val moderationResponse: ModerationResponse = Gson().fromJson(response.toString(), ModerationResponse::class.java)

                                if (moderationResponse.results?.isNotEmpty()!!){

                                    var msg=""
                                    if (moderationResponse.results?.get(0)?.categories?.hate==true){
                                        msg=getString(R.string.hate)
                                    }

                                    list.add(CommonChatModel(message = msg, time = (System.currentTimeMillis()/1000).toString(), isSendMe = false, mediaType =  TEXT_MEDIA_TYPE))


                                    adapter!!.notifyDataSetChanged()
                                    binding.rvChat.smoothScrollToPosition(list.size-1)
                                    Log.e("printResponse",response.toString())
                                }


                            }
                        }
                        else -> {

                        }
                    }
                }
            }
        }


    }



     private fun hideFirstTimeView(){

     binding.clTalk.gone()
        binding.rlBottom.visible()
        binding.rvChat.visible()
         binding.clRootLayout.visible()
    }




    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etMessage.windowToken, 0)

    }

    private fun firstTimeSendMessage() {
//        list.add(CommonChatModel(message =getTimeFromAndroid(), time = System.currentTimeMillis().toString(), isSendMe = true, mediaType = TEXT_MEDIA_TYPE))

        viewModel.openAIApi( getTimeFromAndroid(), binding.pb,TEXT_MEDIA_TYPE)

    }

    private fun setAdapter() {
        adapter=OpenAIChatAdapter(this,list)
        binding.rvChat.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvChat.adapter=adapter
    }
}