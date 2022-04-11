package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.service.match.MatchAPI
import com.xuaum.cstv.data.service.match.MatchService
import com.xuaum.cstv.data.service.match.MatchServiceImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiBuilder(): ApiBuilder = RetrofitAPIBuilder

    @Provides
    fun provideMatchAPI(apiBuilder: ApiBuilder): MatchAPI = apiBuilder.buildApi(MatchAPI::class.java)

}