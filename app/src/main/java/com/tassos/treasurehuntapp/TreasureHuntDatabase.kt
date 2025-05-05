package com.tassos.treasurehuntapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tassos.treasurehuntapp.LocationModel

class TreasureHuntDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TreasureHunt.db"

        // Table and column names
        private const val TABLE_LOCATIONS = "locations"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_VISITED = "visited"
        private const val COLUMN_ORDER = "location_order"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_LOCATIONS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_VISITED INTEGER DEFAULT 0,
                $COLUMN_ORDER INTEGER NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTableQuery)

        // Pre-populate with initial data
        populateInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        onCreate(db)
    }

    private fun populateInitialData(db: SQLiteDatabase) {
        val locations = listOf(
            LocationModel(0, "City Hall", "Find the main entrance", false, 1),
            LocationModel(0, "Albion Falls", "Look for the viewing platform", false, 2),
            LocationModel(0, "Book Store", "Check near the bestsellers section", false, 3),
            LocationModel(0, "Gage Park", "Find the fountain", false, 4),
            LocationModel(0, "Pizza Place", "Ask for the special treasure hunt clue", false, 5),
            LocationModel(0, "Tech World", "Look for the latest gadgets", false, 6),
            LocationModel(0, "Dundurn Castle", "Find the main entrance", false, 7),
            LocationModel(0, "Library Lane", "Check the history section", false, 8),
            LocationModel(0, "Bel Canto Strings Academy", "Look near the reception", false, 9),
            LocationModel(0, "Hamilton Scavenger Hunt", "Find the registration desk", false, 10),
            LocationModel(0, "Canadian Warplane Heritage Museum", "Look for the biggest plane", false, 11),
            LocationModel(0, "Craft Corner", "Find the workshop area", false, 12),
            LocationModel(0, "Hamilton Indoor Go Karts", "Check near the finish line", false, 13),
            LocationModel(0, "Coffee Works", "Ask for the treasure hunt special", false, 14),
            LocationModel(0, "Art Gallery of Hamilton", "Find the main exhibition", false, 15),
            LocationModel(0, "Landmark Cinemas 6 Jackson Square", "Look near theater 3", false, 16),
            LocationModel(0, "Flying Squirrel Trampoline Park", "Jump to the center area", false, 17),
            LocationModel(0, "Bayfront Park", "Find the lake viewpoint", false, 18),
            LocationModel(0, "Limeridge Mall", "Check the central fountain", false, 19),
            LocationModel(0, "Bubble Tea Bar", "Ask for the treasure hunt special", false, 20),
            LocationModel(0, "Flower Shop", "Look for the special arrangement", false, 21),
            LocationModel(0, "Treasure Chest - Final Stop", "Congratulations! You made it!", false, 22)
        )

        for (location in locations) {
            val values = ContentValues().apply {
                put(COLUMN_NAME, location.name)
                put(COLUMN_DESCRIPTION, location.description)
                put(COLUMN_VISITED, if (location.visited) 1 else 0)
                put(COLUMN_ORDER, location.order)
            }
            db.insert(TABLE_LOCATIONS, null, values)
        }
    }

    // Add, update, retrieve locations
    fun getAllLocations(): List<LocationModel> {
        val locations = mutableListOf<LocationModel>()
        val query = "SELECT * FROM $TABLE_LOCATIONS ORDER BY $COLUMN_ORDER ASC"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val visited = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VISITED)) == 1
                val order = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER))

                locations.add(LocationModel(id, name, description, visited, order))
            } while (cursor.moveToNext())
        }
        cursor.close()

        return locations
    }

    fun markLocationAsVisited(locationId: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_VISITED, 1)
        }

        val result = db.update(
            TABLE_LOCATIONS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(locationId.toString())
        )

        return result > 0
    }

    fun resetAllLocations() {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_VISITED, 0)
        }

        db.update(TABLE_LOCATIONS, values, null, null)
    }
}