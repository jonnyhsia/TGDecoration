package com.tugou.decoration.model.base

import com.google.gson.Gson
import com.google.gson.JsonElement
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.io.IOException

inline fun <reified T> Retrofit.create(): T = create(T::class.java)

fun <T> Observable<TGResponse<T>>.scheduler(): Observable<TGResponse<T>> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<TGResponse<T>>.scheduler(): Single<TGResponse<T>> =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<TGResponse<T>>.handleResponse(): Single<T> =
        this.handleError()
                .flatMap {
                    when {
                        it.code == 8001 -> {
                            val e = ExpiredException()
                            Single.error<T>(e)
                        }
                        it.code != 0 -> Single.error<T>(ServerRespException(it.code, it.message))
                        it.data == null -> Single.error<T>(NullDataException())
                        else -> Single.just(it.data)
                    }
                }

fun <T> Single<T>.handleError(): Single<T> =
        this.onErrorResumeNext {
            Single.error(if (it is IOException) RuntimeException("网络繁忙") else it)
        }

fun <T> Single<TGResponse<T>>.handleResponseWithDefaultValue(default: T): Single<T> =
        this.handleError()
                .flatMap {
                    when {
                        it.code == 8001 -> {
                            val e = ExpiredException()
                            // postEvent(e)
                            Single.error<T>(e)
                        }
                        it.code != 0 -> Single.error<T>(ServerRespException(it.code, it.message))
                        else -> Single.just(it.data ?: default)
                    }
                }

fun <T> Single<TGResponse<T>>.toHttpSingle(): Single<T> =
        this.scheduler().handleResponse()

fun <T> Single<TGResponse<T>>.toHttpSingleWithDefaultValue(default: T): Single<T> =
        this.scheduler().handleResponseWithDefaultValue(default)

fun <T> Single<TGResponse<T>>.toHttpCompletable(): Completable =
        this.scheduler()
                .handleError()
                .flatMapCompletable {
                    when {
                        it.code == 8001 -> {
                            val e = ExpiredException()
                            Completable.error(e)
                        }
                        it.code != 0 -> Completable.error(ServerRespException(it.code, it.message))
                        else -> Completable.complete()
                    }
                }


inline fun <reified T> Gson.fromJson(json: JsonElement): T {
    return fromJson(json, T::class.java)
}