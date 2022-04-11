package com.xuaum.cstv.data.service

interface ApiBuilder {
    fun <Api> buildApi(
        api: Class<Api>
    ): Api
}