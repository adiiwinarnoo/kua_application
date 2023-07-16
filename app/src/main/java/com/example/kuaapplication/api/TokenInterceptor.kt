package com.example.kuaapplication.api

import android.content.Context
import android.util.Log
import com.example.kuaapplication.utils.Constant
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Constant.TOKEN_USER}")
            .build()
        return chain.proceed(request)
    }
}