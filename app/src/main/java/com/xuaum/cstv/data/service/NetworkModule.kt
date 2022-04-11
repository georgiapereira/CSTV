package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.service.match.MatchService
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
    fun apiBuilder(): ApiBuilder = RetrofitAPIBuilder

    @Provides
    fun matchService(apiBuilder: ApiBuilder): MatchService =
        apiBuilder.buildApi(MatchService::class.java)

}