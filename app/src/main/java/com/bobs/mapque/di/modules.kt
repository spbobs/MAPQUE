package com.bobs.mapque.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bobs.mapque.BuildConfig
import com.bobs.mapque.MyApplication
import com.bobs.mapque.map.model.searchaddress.SearchAddressData
import com.bobs.mapque.map.model.searchaddress.SearchAddressRepository
import com.bobs.mapque.searchlist.model.room.AppDatabase
import com.bobs.mapque.searchlist.model.room.SearchListRepository
import com.bobs.mapque.searchlist.model.room.SearchListData
import com.bobs.mapque.network.api.SearchAddressService
import com.bobs.mapque.map.viewmodel.MapViewModel
import com.bobs.mapque.searchlist.viewmodel.SearchListViewModel
import com.bobs.mapque.util.ext.sharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// 뷰모델 di
val viewModelModule = module {
    viewModel { MapViewModel(get(), get()) }
    viewModel { SearchListViewModel(get()) }
}

// 카카오 api di
val apiModule = module {
    single<SearchAddressService> {
        // retrofit 세팅
        Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(SearchAddressService::class.java)
    }

    single {
        // 서버통신 로그 및 카카오 서버 호출 시 필요한 rest api key를 헤더로 세팅
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "Authorization",
                    "KakaoAK ${BuildConfig.KAKAO_SDK_RESTAPIKEY}"
                )
                chain.proceed(requestBuilder.build())
            }
            .build()
    }
}

// 레포지토리 di
val dataModule = module {
    single<SearchAddressData> { SearchAddressRepository(get()) }
    single<SearchListData> { SearchListRepository(get()) }
}

val RD = "RD"

// 룸 di
val roomModule = module {
    single(named(RD)) {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, "search_list_db"
        ).build()
    }

    single { (get(named(RD)) as AppDatabase).searchItemDao() }
}

val prefsModule = module {
    single { androidApplication().sharedPreferences()}
}