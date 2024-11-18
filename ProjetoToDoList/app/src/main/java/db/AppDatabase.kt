import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import db.ListDao
import model.ListEntity

@Database(entities = [ListEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun listDao(): ListDao  // Cambié el nombre del método a listDao()

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries()
                    .build()  //
                INSTANCE = instance
                instance
            }
        }
    }
}
