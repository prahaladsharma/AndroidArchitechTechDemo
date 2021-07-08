package com.viaplaytest.data.newsSe.data.newsSet

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.viaplaytest.api.NetworkState
import com.viaplaytest.api.ViaplaySection
import com.viaplaytest.data.dao.ViaPlayDao
import com.viaplaytest.data.ViaPlaySet.ViaPlayRemoteDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ViaPlayPageDataSource @Inject constructor(
    private val remoteDataSource: ViaPlayRemoteDataSource,
    private val newsDao: ViaPlayDao,
    private val coroutineScope: CoroutineScope
) : PageKeyedDataSource<Int, ViaplaySection>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ViaplaySection>
    ) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(0, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ViaplaySection>) {
        /*networkState.postValue(NetworkState.LOADING)
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }*/
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ViaplaySection>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<ViaplaySection>) -> Unit) {
        coroutineScope.launch(getJobErrorHandler()) {
            val response = remoteDataSource.fetchNewsList()
            if (response.status == com.viaplaytest.data.Result.Status.SUCCESS) {
                val results = response.data?._links?.viaplaySections ?: emptyList()
                newsDao.insertAll(results)
                callback(results)
                networkState.postValue(NetworkState.LOADED)
            } else if (response.status == com.viaplaytest.data.Result.Status.ERROR) {
                networkState.postValue(NetworkState.error(response.message ?: "Unknown error"))
                postError(response.message ?: "Unknown error")
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
    }
}
