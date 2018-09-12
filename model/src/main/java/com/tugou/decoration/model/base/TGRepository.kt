package com.tugou.decoration.model.base

import android.content.Context
import android.os.Build
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tugou.decoration.model.Algorithm
import com.tugou.decoration.model.BuildConfig
import com.tugou.decoration.model.base.typeadapter.BooleanSafeAdapter
import com.tugou.decoration.model.passport.entity.UserModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

internal open class TGRepository {

    companion object {

        @JvmStatic
        internal lateinit var retrofit: Retrofit

        internal lateinit var contextRef: WeakReference<Context>

        internal lateinit var typeSafeGson: Gson

        @JvmStatic
        internal fun initialize(context: Context) {
            contextRef = WeakReference(context)

            typeSafeGson = createTypeSafeGson()

            val builder = OkHttpClient.Builder()
                    .connectTimeout(8_000, TimeUnit.MILLISECONDS)
                    .readTimeout(8_000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(createQI())


            val okHttpClient = builder.build()

            retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(typeSafeGson))
                    .baseUrl("https://api.jtugou.com")
                    .build()
        }

        private fun createTypeSafeGson(): Gson {
            return GsonBuilder()
                    .registerTypeAdapter(Boolean::class.java, BooleanSafeAdapter())
                    .create()
        }

        @JvmStatic
        fun getLoginUser(): UserModel? {
            return UserModel(100, "", "高能的土豆", "http://ou4f31a1x.bkt.clouddn.com/18-9-6/61589954.jpg", "15957128262", true)
        }

        private fun createQI() = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val randomString = Algorithm.randomString(15)
            var signToken = Algorithm.hmac256("tugou-explore$randomString", "tugou-jonnyhsia")
            if (signToken == null) {
                signToken = "THIS_IS_A_NULL_TOKEN"
            }

            val httpUrlBuilder = originalHttpUrl.newBuilder()

            // Todo Start 转移到Header中，Serve端修改后，将此代码废弃
            httpUrlBuilder.addQueryParameter("random", randomString)
                    .addQueryParameter("sign_token", signToken)
                    .addQueryParameter("app_name", "tugou-explore")
            // Todo End 转移到Header中，Serve端修改后，将此代码废弃
            // 如果用户登录了，将user_id, token带上
            val userBean = getLoginUser()
            if (userBean != null) {
                httpUrlBuilder.addQueryParameter("user_id", "${userBean.userId}")
                        .addQueryParameter("token", userBean.token)
                        .addQueryParameter("skey", userBean.token)
            }

            val httpUrl = httpUrlBuilder.build()

            val requestBuilder = original.newBuilder()
                    .addHeader("TG", "android/" + BuildConfig.VERSION_NAME + "/tugou")
                    .addHeader("RANDOM", randomString)
                    .addHeader("TOKEN", signToken)
                    .addHeader("UUID", "")
                    .addHeader("CHANNEL", "")
                    .removeHeader("user-agent")
                    .addHeader("user-agent", String.format("TugouDecor/%s (Android; API%s)",
                            BuildConfig.VERSION_NAME, Build.VERSION.SDK_INT))
                    .url(httpUrl)

            val request = requestBuilder.build()

            chain.proceed(request)
        }
    }

}