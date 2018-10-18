package com.mfeldsztejn.rappitest.dtos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mfeldsztejn.rappitest.dtos.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres where id=:id")
    fun find(id: Int): LiveData<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(type: List<Genre>)

    @Query("SELECT count(*) FROM genres")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM genres")
    fun findAll(): LiveData<List<Genre>>
}