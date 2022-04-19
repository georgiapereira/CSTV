package com.xuaum.cstv.data.repository.match

import androidx.paging.PagingSource
import com.xuaum.cstv.data.model.response.CSMatch

interface MatchPagingSourceFactory {
    fun create(): PagingSource<Int, CSMatch>
}