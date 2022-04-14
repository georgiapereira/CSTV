package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.service.match.MatchApi
import com.xuaum.cstv.data.service.team.TeamApi
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
    fun provideMatchApi(apiBuilder: ApiBuilder): MatchApi = apiBuilder.buildApi(MatchApi::class.java)

    @Provides
    fun provideTeamApi(apiBuilder: ApiBuilder): TeamApi = apiBuilder.buildApi(TeamApi::class.java)

}