package com.scottquach.today.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighlightDao {
    @Query("SELECT * FROM highlights ORDER BY created DESC")
    fun getAll(): LiveData<List<Highlight>>

    @Query("SELECT * FROM highlights WHERE id = :highlightId")
    fun getById(highlightId: Int): LiveData<Highlight>

    @Query("SELECT * FROM highlights WHERE goal_id = :goalId")
    fun getByGoal(goalId: Int): LiveData<List<Highlight>>

    @Query("SELECT * FROM highlights WHERE date(datetime(created / 1000, 'unixepoch')) = date('now')")
    fun getToday(): LiveData<Highlight>

    @Insert
    fun insert(vararg highlights: Highlight)

    @Delete
    fun delete(vararg highlights: Highlight)
}