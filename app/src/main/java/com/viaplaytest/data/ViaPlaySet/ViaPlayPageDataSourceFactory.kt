package com.viaplaytest.data.ViaPlaySet

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.viaplaytest.api.ViaplaySection
import com.viaplaytest.data.dao.ViaPlayDao
import com.viaplaytest.data.newsSe.data.newsSet.ViaPlayPageDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ViaPlayPageDataSourceFactory @Inject constructor(
    private val dataSource: ViaPlayRemoteDataSource,
    private val dao: ViaPlayDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, ViaplaySection>() {

    val liveData = MutableLiveData<ViaPlayPageDataSource>()

    override fun create(): DataSource<Int, ViaplaySection> {
        val source = ViaPlayPageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 1
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}
