package com.example.skysync.dashBoard.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey val uid:Int

)