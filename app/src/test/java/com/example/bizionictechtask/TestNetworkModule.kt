package com.example.bizionictechtask

import com.example.data.remote.ApiService
import com.example.data.remote.PostApiServiceTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [NetworkModule::class]
//)

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(mockWebServer: MockWebServer): PostApiServiceTest {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Point to MockWebServer
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiServiceTest::class.java)
    }

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer {
        return MockWebServer()
    }
}
