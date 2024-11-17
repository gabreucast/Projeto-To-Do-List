package db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.User

@Dao
interface ListDao {
    @Insert
    fun insertAll(vararg tasks: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Update
    fun update(task: User)

    @Delete
    fun delete(task: User)

}