package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.service.match.MatchService
import com.xuaum.cstv.data.service.match.MatchServiceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ServicesModule {
    @Binds
    abstract fun matchService(matchServiceImp: MatchServiceImp): MatchService
}