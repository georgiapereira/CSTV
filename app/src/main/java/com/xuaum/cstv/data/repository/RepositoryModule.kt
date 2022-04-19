package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.repository.match.MatchRepository
import com.xuaum.cstv.data.repository.match.MatchRepositoryImp
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
    fun matchRepository(matchRepositoryImp: MatchRepositoryImp): MatchRepository

    @Binds
    fun teamRepository(teamRepositoryImp: TeamRepositoryImp): TeamRepository
}