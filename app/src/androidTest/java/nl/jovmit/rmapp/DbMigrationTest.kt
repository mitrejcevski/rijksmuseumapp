package nl.jovmit.rmapp

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nl.jovmit.rmapp.db.MIGRATION_1_2
import nl.jovmit.rmapp.db.NotesDatabase
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DbMigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        NotesDatabase::class.java
    )

    @Test
    fun migration1to2() {
        val dbName = "test.db"
        var db = helper.createDatabase(dbName, 1).apply {
            execSQL("INSERT INTO Note (noteId, title, content) VALUES (1, 'title_1', 'content_1')")
            close()
        }

        db = helper.runMigrationsAndValidate(dbName, 2, true, MIGRATION_1_2)

        val cursor = db.query("SELECT * FROM Note")
        assertArrayEquals(arrayOf("noteId", "title", "content", "favorite"), cursor.columnNames)
        cursor.moveToFirst()
        val columnIndexOfFavorites = cursor.getColumnIndex("favorite")
        assertEquals(0, cursor.getInt(columnIndexOfFavorites))
    }
}