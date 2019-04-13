package com.scottquach.today.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HighlightDao {
    @Query("SELECT * FROM highlights ORDER BY datetime(created) DESC")
    fun getAll(): LiveData<List<Highlight>>

    @Query("SELECT * FROM highlights WHERE id = :highlightId")
    fun getById(highlightId: Int): LiveData<Highlight>

    @Query("SELECT * FROM highlights WHERE goal_id = :goalId")
    fun getByGoal(goalId: Int): LiveData<List<Highlight>>

    @Query("SELECT * FROM highlights WHERE date(created, 'localtime') = date('now', 'localtime')")
    fun getToday(): LiveData<Highlight>

    @Query("UPDATE highlights SET status = 'COMPLETED' WHERE id = :highlightId")
    fun completeHighlight(highlightId: Int)

    @Insert
    fun insert(vararg highlights: Highlight)

    @Delete
    fun delete(vararg highlights: Highlight)
}