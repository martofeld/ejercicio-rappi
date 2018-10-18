package com.mfeldsztejn.rappitest.dtos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mfeldsztejn.rappitest.dtos.Configuration

@Dao
interface ConfigurationDao {
    @Query("SELECT * FROM obtainConfiguration where id=:id")
    fun find(id: String): LiveData<Configuration>

    @Query("SELECT count(*) FROM obtainConfiguration")
    fun count(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(configuration: Configuration)
}