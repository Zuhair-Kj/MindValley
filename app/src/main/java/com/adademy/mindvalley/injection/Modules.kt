package com.adademy.mindvalley.injection

import androidx.room.Room
import com.adademy.core.util.NetworkHelper
import com.adademy.discovery.network.DiscoveryService
import com.adademy.discovery.repository.CategoriesRepository
import com.adademy.discovery.repository.ChannelsRepository
import com.adademy.discovery.repository.LatestEpisodesRepository
import com.adademy.discovery.viewmodel.DiscoveryViewModel
import com.adademy.mindvalley.cache.DATABASE_NAME
import com.adademy.mindvalley.cache.MindValleyDB
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val SCOPE_DISCOVERY = "discovery"
const val BASE_URL = "https://pastebin.com"

@JvmField
val coreModule = module {
    single { NetworkHelper(androidContext()) }
}

@JvmField
val networkModule = module {
    single<DiscoveryService> {
        get<Retrofit>(named(SCOPE_DISCOVERY)).create(DiscoveryService::class.java)
    }

    single(named(SCOPE_DISCOVERY)) { Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(get())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BASIC }
    }
}

@JvmField
val discoveryModule = module {

    viewModel {
        DiscoveryViewModel(channelsRepository = get(), categoriesRepository = get(), latestEpisodesRepository = get())
    }

    single {
        LatestEpisodesRepository(discoveryService = get(), latestEpisodesDao = get())
    }

    single {
        ChannelsRepository(discoveryService = get(), channelsDao = get())
    }

    single {
        CategoriesRepository(discoveryService = get(), categoriesDao = get())
    }

    single { get<MindValleyDB>().categoriesDao() }

    single { get<MindValleyDB>().channelsDao() }

    single { get<MindValleyDB>().latestEpisodesDao() }
}

@JvmField
val cacheModule = module {
    single {
        Room.databaseBuilder(androidContext(), MindValleyDB::class.java, DATABASE_NAME).build()
    }

    single { GsonBuilder().create() }
}