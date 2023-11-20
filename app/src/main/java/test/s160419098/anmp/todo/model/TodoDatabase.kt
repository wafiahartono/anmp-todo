package test.s160419098.anmp.todo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Todo::class], version = 3)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var instance: TodoDatabase? = null
        private val LOCK = Any()

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 2 NOT NULL")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // SQLite does not have a separate Boolean storage class. Instead, Boolean values
                // are stored as integers 0 (false) and 1 (true).
                // from SQLite documentation (https://www.sqlite.org/datatype3.html)
                db.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, TodoDatabase::class.java, "todo")
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build()

        operator fun invoke(context: Context): TodoDatabase {
            return synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
    }
}