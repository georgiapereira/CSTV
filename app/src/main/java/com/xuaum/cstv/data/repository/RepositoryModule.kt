package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.repository.match.MatchRemoteDataSource
import com.xuaum.cstv.data.repository.match.MatchRemoteDataSourceImp
import com.xuaum.cstv.data.repository.match.MatchRepository
import com.xuaum.cstv.data.repository.match.MatchRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun matchRemoteDataSource(matchRemoteDataSourceImp: MatchRemoteDataSourceImp): MatchRemoteDataSource

    @Binds
    fun matchRepository(matchRepositoryImp: MatchRepositoryImp): MatchRepository
}