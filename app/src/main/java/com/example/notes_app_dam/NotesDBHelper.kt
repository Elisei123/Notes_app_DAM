package com.example.notes_app_dam

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertNote(note: NoteModel): Boolean{
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        //values.put(DBContract.NoteEntry.COLUMN_NOTE_ID, note.noteid)
        values.put(DBContract.NoteEntry.COLUMN_DATE, note.note_date)
        values.put(DBContract.NoteEntry.COLUMN_HOUR, note.note_hour)
        values.put(DBContract.NoteEntry.COLUMN_MESSAGE, note.note_message)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.NoteEntry.TABLE_NAME, null, values)

        return true;
    }

    @Throws(SQLiteConstraintException::class)
    /*fun deleteNote(noteid: Int): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.NoteEntry.COLUMN_NOTE_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(noteid)
        // Issue SQL statement.
        db.delete(DBContract.NoteEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }*/

    fun readNote(noteid: Int): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.NoteEntry.TABLE_NAME + " WHERE " + DBContract.NoteEntry.COLUMN_NOTE_ID + "='" + noteid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var note_date: String
        var note_hour: String
        var note_message: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                note_date = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_DATE))
                note_hour = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_HOUR))
                note_message = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_MESSAGE))

                notes.add(NoteModel(noteid,note_date, note_hour, note_message))
                cursor.moveToNext()
            }
        }
        return notes
    }

    fun readAllNotes(): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.NoteEntry.TABLE_NAME + " ORDER BY "+
                    DBContract.NoteEntry.COLUMN_NOTE_ID + " DESC ", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var noteid: Int
        var note_date: String
        var note_hour: String
        var note_message: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                noteid = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_NOTE_ID))
                note_date = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_DATE))
                note_hour = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_HOUR))
                note_message = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_MESSAGE))

                notes.add(NoteModel(noteid, note_date, note_hour, note_message))
                cursor.moveToNext()
            }
        }
        return notes
    }

    fun readNotes_date(date: String): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {

            cursor = db.rawQuery("select * from " + DBContract.NoteEntry.TABLE_NAME + " WHERE "
                    + DBContract.NoteEntry.COLUMN_DATE + "='" + date + "' ORDER BY "+
                    DBContract.NoteEntry.COLUMN_NOTE_ID + " DESC ", null)

        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var noteid: Int
        var note_date: String
        var note_hour: String
        var note_message: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                noteid = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_NOTE_ID))
                note_date = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_DATE))
                note_hour = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_HOUR))
                note_message = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_MESSAGE))

                notes.add(NoteModel(noteid, note_date, note_hour, note_message))
                cursor.moveToNext()
            }
        }
        return notes
    }

    fun readNotes_hour(hour: String): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {

            cursor = db.rawQuery("select * from " + DBContract.NoteEntry.TABLE_NAME + " WHERE "
                    + DBContract.NoteEntry.COLUMN_HOUR + "='" + hour + "' ORDER BY "+
                    DBContract.NoteEntry.COLUMN_NOTE_ID + " DESC ", null)

        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var noteid: Int
        var note_date: String
        var note_hour: String
        var note_message: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                noteid = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_NOTE_ID))
                note_date = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_DATE))
                note_hour = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_HOUR))
                note_message = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_MESSAGE))

                notes.add(NoteModel(noteid, note_date, note_hour, note_message))
                cursor.moveToNext()
            }
        }
        return notes
    }

    fun readNotes_words(words: String): ArrayList<NoteModel> {
        val notes = ArrayList<NoteModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {

            cursor = db.rawQuery("select * from " + DBContract.NoteEntry.TABLE_NAME + " WHERE "
                    + DBContract.NoteEntry.COLUMN_MESSAGE + " LIKE '%" + words + "%' ORDER BY "+
                    DBContract.NoteEntry.COLUMN_NOTE_ID + " DESC ", null)

        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var noteid: Int
        var note_date: String
        var note_hour: String
        var note_message: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                noteid = cursor.getInt(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_NOTE_ID))
                note_date = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_DATE))
                note_hour = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_HOUR))
                note_message = cursor.getString(cursor.getColumnIndex(DBContract.NoteEntry.COLUMN_MESSAGE))

                notes.add(NoteModel(noteid, note_date, note_hour, note_message))
                cursor.moveToNext()
            }
        }
        return notes
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: Int) {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Issue SQL statement.
        db.delete(DBContract.NoteEntry.TABLE_NAME, DBContract.NoteEntry.COLUMN_NOTE_ID + "=" + userid, null)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.NoteEntry.TABLE_NAME + " (" +
                    DBContract.NoteEntry.COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBContract.NoteEntry.COLUMN_DATE + " TEXT," +
                    DBContract.NoteEntry.COLUMN_HOUR + " TEXT," +
                    DBContract.NoteEntry.COLUMN_MESSAGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.NoteEntry.TABLE_NAME
    }

}