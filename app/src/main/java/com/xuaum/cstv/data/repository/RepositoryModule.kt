package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.repository.match.MatchPagingSourceFactory
import com.xuaum.cstv.data.repository.match.MatchPagingSourceFactoryImp
import com.xuaum.cstv.data.repository.team.TeamRepository
import com.xuaum.cstv.data.repository.team.TeamRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun matchPagingSourceFactory(matchPagingSourceFactoryImp: MatchPagingSourceFactoryImp): MatchPagingSourceFactory

    @Binds
    fun teamRepository(teamRepositoryImp: TeamRepositoryImp): TeamRepository
}