package com.example.notes_app_dam

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class NoteEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "notes"
            val COLUMN_NOTE_ID = "noteid"
            val COLUMN_DATE = "note_date"
            val COLUMN_HOUR = "note_hour"
            val COLUMN_MESSAGE = "note_message"
        }
    }
}