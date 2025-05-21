package nl.jovmit.rmapp.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Note ADD COLUMN favorite INTEGER NOT NULL DEFAULT 0")
    }
}