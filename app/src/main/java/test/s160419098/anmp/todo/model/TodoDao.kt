package test.s160419098.anmp.todo.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg todo: Todo)

    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC")
    fun all(): List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun find(id: Int): Todo

    @Query("DELETE FROM todo")
    fun deleteAll()

    @Delete
    fun delete(todo: Todo)

    @Query("UPDATE todo SET is_done = 1 WHERE id = :id")
    fun setDone(id: Int)
}