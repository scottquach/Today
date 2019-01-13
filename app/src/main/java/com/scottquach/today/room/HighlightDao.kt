package com.scottquach.today.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighlightDao {
    @Query("SELECT * FROM highlights")
    fun getAll(): LiveData<List<Highlight>>

    @Query("SELECT * FROM highlights WHERE id = :highlightId")
    fun getById(highlightId: Int): LiveData<Highlight>

    @Query("SELECT * FROM highlights WHERE goal_id = :goalId")
    fun getByGoal(goalId: Int): LiveData<List<Highlight>>

    @Insert
    fun insertAll(vararg highlights: Highlight)

    @Delete
    fun delete(highlight: Highlight)
}