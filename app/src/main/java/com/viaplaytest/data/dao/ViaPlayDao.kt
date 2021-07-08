package com.viaplaytest.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viaplaytest.api.ViaplaySection

@Dao
interface ViaPlayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(newsList: List<ViaplaySection>)

    @Query("SELECT * FROM ViaplaySection")
    fun getPagedNews(): DataSource.Factory<Int, ViaplaySection>
}