package com.viaplaytest.data.ViaPlaySet

import com.viaplaytest.api.BaseDataSource
import com.viaplaytest.api.ViaPlayService
import com.viaplaytest.api.RootJson
import com.viaplaytest.data.Result
import javax.inject.Inject

/* Works with the New API to get data. */
class ViaPlayRemoteDataSource @Inject constructor(private val service: ViaPlayService) : BaseDataSource() {

    suspend fun fetchNewsList() : Result<RootJson> {
        return getResult { service.getTopNewsList() }
    }
}
