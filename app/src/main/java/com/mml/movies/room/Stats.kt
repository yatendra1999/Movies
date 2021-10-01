package com.mml.movies.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stats(
    @PrimaryKey val id: Int,
    val bookmarked: Boolean,
    val trending: Boolean,
    val active: Boolean
)