package com.colourchangedemo.flow.api

import com.colourchangedemo.util.AppConstants.AUTHORIZATION
import com.colourchangedemo.util.AppConstants.AUTHORIZATION_VALUE
import com.colourchangedemo.util.AppConstants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


object RetrofitBuilder {


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient())
            .build()
    }
    private fun httpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(provideHeaderInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build()
    }
    private fun provideHeaderInterceptor(): Interceptor {

        return Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .header("Accept", "application/json")
                .header(AUTHORIZATION, AUTHORIZATION_VALUE)
                .build()
            chain.proceed(request)
        }
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)


    private const val MESSAGE_KEY = "message"
    private const val ERROR_KEY = "error"

    fun getErrorHandling(e: Throwable):String{
         when(e){
            is HttpException -> {
                val body = e.response()?.errorBody()
                /*404 = when your logged in another device*/
                /*if(e.code()== 440){
                    return  UNAUTHORIZED
                }else{*/


                    try {
                        val jsonObject = JSONObject(body!!.string())
                        when {
                            jsonObject.has(MESSAGE_KEY) ->  return jsonObject.getString(MESSAGE_KEY)
                            jsonObject.has(ERROR_KEY) ->  return jsonObject.getJSONObject(ERROR_KEY).getString("message")

                            else -> { return "Something wrong happened"}
                        }
                    } catch (e: Exception) {
                        return e.message.toString()
                    }


              /*  }*/
            }
            is SocketTimeoutException -> return "TIMEOUT"
            is ConnectException , is IOException -> return "NETWORK CONNECTION ERROR"
            else -> return "SERVER ERROR"
        }

    }


}