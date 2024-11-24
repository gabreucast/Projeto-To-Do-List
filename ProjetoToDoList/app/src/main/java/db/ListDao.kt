package db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.ListEntity

@Dao
interface ListDao {
    @Query("SELECT * FROM listEntity")
    fun getAll(): List<ListEntity>

    @Update
    fun update(task: ListEntity)

    @Delete
    fun delete(task: ListEntity)

    @Insert
    fun insert(tasks: ListEntity)
}
