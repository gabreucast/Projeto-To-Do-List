package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Cambiar Int a Long
    @ColumnInfo(name = "first_name") val title: String,
    @ColumnInfo(name = "last_name") val task: String
)


