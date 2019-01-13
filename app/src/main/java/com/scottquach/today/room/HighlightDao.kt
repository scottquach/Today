package com.scottquach.today.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighlightDao {
    @Query("SELECT * FROM highlight")
    fun getAll(): LiveData<Highlight>

    @Query("SELECT * FROM highlight WHERE id = :highlightId")
    fun getById(highlightId: Int): LiveData<Highlight>

    @Query("SELECT * FROM highlight WHERE goalId = :goalId")
    fun getByGoal(goalId: Int): LiveData<List<Highlight>>

    @Insert
    fun insertAll(vararg highlights: Highlight)

    @Delete
    fun delete(highlight: Highlight)
}