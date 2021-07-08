package com.viaplaytest.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.viaplaytest.api.Data
import com.viaplaytest.api.ViaplaySection
import com.viaplaytest.data.ViaPlaySet.ViaPlayRepository
import com.viaplaytest.di.CoroutineScopeIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class ViaPlayViewModel @Inject constructor(
    private val repository: ViaPlayRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {
    private var newsList: Data<ViaplaySection>? = null

    fun newsList(connectivityAvailable: Boolean): Data<ViaplaySection>? {

        if (newsList == null) {
            newsList = repository.observePagedNews(connectivityAvailable, ioCoroutineScope)
        }
        return newsList
    }

    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
