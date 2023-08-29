package com.colourchangedemo.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieAnimationView
import com.colourchangedemo.flow.api.ApiHelperImpl
import com.colourchangedemo.flow.api.RetrofitBuilder
import com.colourchangedemo.flow.api.RetrofitBuilder.getErrorHandling
import com.colourchangedemo.flow.resource.Resource
import com.colourchangedemo.model.ModerationResponse
import com.colourchangedemo.model.request.ImageRequest
import com.colourchangedemo.model.request.ModerationRequest
import com.colourchangedemo.model.request.TextRequest
import com.colourchangedemo.util.gone
import com.colourchangedemo.util.visible
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class OpenAIViewModel(private val mContext: Context) : ViewModel() {

    var apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)
    private val _textRequestResponse = MutableStateFlow<Resource<JsonObject>>(Resource.started())
    val textRequestResponse: StateFlow<Resource<JsonObject>> = _textRequestResponse

    private val _imageRequestResponse = MutableStateFlow<Resource<JsonObject>>(Resource.started())
    val imageRequestResponse: StateFlow<Resource<JsonObject>> = _imageRequestResponse


    private val _moderationResponse = MutableStateFlow<Resource<JsonObject>>(Resource.started())
    val moderationResponse: StateFlow<Resource<JsonObject>> = _moderationResponse


    fun openAIApi(
        textData: String,
        pb: LottieAnimationView,
        mediaType: String,

    ) {


        if (isOnline(mContext)) {

            viewModelScope.launch(Dispatchers.Main) {
                pb.playAnimation()

                pb.visible()

                _moderationResponse.value = Resource.loading()

                val moderationModel= ModerationRequest( input = textData)

                apiHelper.createModeration(moderationModel)
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        pb.cancelAnimation()
                        pb.gone()

                        commonMessage(getErrorHandling(e))



                    }
                    .collect { pb.cancelAnimation()
                        pb.gone()


                        val moderationResponse: ModerationResponse = Gson().fromJson(it.toString(), ModerationResponse::class.java)

                        if (moderationResponse.results?.isNotEmpty()!!){
                            if (moderationResponse.results?.get(0)?.categories?.hate==false
                                &&moderationResponse.results?.get(0)?.categories?.hateThreatening==false
                                &&moderationResponse.results?.get(0)?.categories?.selfHarm==false
                                &&moderationResponse.results?.get(0)?.categories?.sexual==false
                                &&moderationResponse.results?.get(0)?.categories?.sexualMinors==false
                                &&moderationResponse.results?.get(0)?.categories?.violence==false
                                &&moderationResponse.results?.get(0)?.categories?.violenceGraphic==false ){

                                callSendMessage(mediaType,pb,textData)
                            }else{
                                _moderationResponse.value = Resource.success(it)
                            }

                        }else{
                            callSendMessage(mediaType,pb,textData)
                        }


                    }


            }



        } else {
            commonMessage("No Internet connection")
        }
    }

    private fun callSendMessage(mediaType: String, pb: LottieAnimationView, textData: String) {
        if (mediaType=="1"){

            viewModelScope.launch(Dispatchers.Main) {
                pb.playAnimation()

                pb.visible()

                _textRequestResponse.value = Resource.loading()

                val imageModel= TextRequest("gpt-3.5-turbo", arrayListOf(TextRequest.RoleModel("assistant", textData)))

                apiHelper.createChat(imageModel)
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        pb.cancelAnimation()

                        pb.gone()

                        commonMessage(getErrorHandling(e))
                        _textRequestResponse.value = Resource.error("")
                    }
                    .collect {
                        pb.cancelAnimation()
                        pb.gone()
                        _textRequestResponse.value = Resource.success(it)
                    }


            }
        }
        else {
            viewModelScope.launch(Dispatchers.Main) {
                pb.playAnimation()
                pb.visible()

                _imageRequestResponse.value = Resource.loading()

                val imageModel= ImageRequest(textData,2,"256x256")

                apiHelper.createImage(imageModel)
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        pb.cancelAnimation()
                        pb.gone()

                        commonMessage(getErrorHandling(e))
                        _imageRequestResponse.value = Resource.error("")
                    }
                    .collect {
                        pb.cancelAnimation()
                        pb.gone()
                        _imageRequestResponse.value = Resource.success(it)
                    }



            }
        }
    }


    fun commonMessage(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}