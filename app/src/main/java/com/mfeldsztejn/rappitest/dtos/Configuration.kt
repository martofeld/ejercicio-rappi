package com.mfeldsztejn.rappitest.dtos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mfeldsztejn.rappitest.Constants

@Entity(tableName = "obtainConfiguration")
data class Configuration(@Embedded val images: Images, @PrimaryKey var id: String = Constants.CONFIGURATION_ID)