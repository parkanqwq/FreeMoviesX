package com.kalabukhov.app.freemoviesx.model.database_note

import androidx.room.Room
import androidx.room.RoomDatabase
import com.kalabukhov.app.freemoviesx.framework.App

@androidx.room.Database(
    entities = [
        NoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "add_note_database.db"
        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }
}