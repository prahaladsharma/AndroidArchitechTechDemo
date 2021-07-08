package com.viaplaytest.data.ViaPlaySet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.viaplaytest.api.Data
import com.viaplaytest.api.NetworkState
import com.viaplaytest.api.ViaplaySection
import com.viaplaytest.data.dao.ViaPlayDao
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//@OpenForTesting
class ViaPlayRepository @Inject constructor(
    private val newsDao: ViaPlayDao,
    private val newsRemoteDataSource: ViaPlayRemoteDataSource) {

    fun observePagedNews(connectivityAvailable : Boolean, coroutineScope: CoroutineScope)
            : Data<ViaplaySection> {

        return if (connectivityAvailable)
            observeRemotePagedNews(coroutineScope)
        else observeLocalPagedNews()
    }
    private fun observeLocalPagedNews(): Data<ViaplaySection> {

        val dataSourceFactory = newsDao.getPagedNews()

        val createLD = MutableLiveData<NetworkState>()
        createLD.postValue(NetworkState.LOADED)

        return Data(LivePagedListBuilder(dataSourceFactory,
            ViaPlayPageDataSourceFactory.pagedListConfig()).build(),createLD)
    }

    private fun observeRemotePagedNews(ioCoroutineScope: CoroutineScope): Data<ViaplaySection> {
        val dataSourceFactory = ViaPlayPageDataSourceFactory(newsRemoteDataSource,
            newsDao, ioCoroutineScope)

        val networkState = Transformations.switchMap(dataSourceFactory.liveData) {
            it.networkState
        }
        return Data(LivePagedListBuilder(dataSourceFactory,
            ViaPlayPageDataSourceFactory.pagedListConfig()).build(),networkState)
    }
}
