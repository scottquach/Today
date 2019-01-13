package com.scottquach.today.helpers

import android.content.Context
import androidx.lifecycle.LiveData
import com.scottquach.today.room.AppDatabase
import com.scottquach.today.room.Highlight

class HighlightDbHelper(val context: Context) {
    var db: AppDatabase = AppDatabase.getDatabase(context)

    fun getAllHighlights(): LiveData<List<Highlight>> {
        return db.highlightDao().getAll()
    }

    fun getById(id: Int): LiveData<Highlight> {
        return db.highlightDao().getById(id)
    }

    fun insertHighlight(highlight: Highlight) {
        db.highlightDao().insertAll(highlight)
    }

    fun delete(highlight: Highlight) {
        db.highlightDao().insertAll(highlight)
    }
}