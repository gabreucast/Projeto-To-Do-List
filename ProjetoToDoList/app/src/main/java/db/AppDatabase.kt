package db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import model.ListEntity

@Database(entities = [ListEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun listDao(): ListDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Função que retorna a instância única da base de dados
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries() // Permite consulta no thread principa
                    .build()
                INSTANCE = instance // Inicializa a instância da base de dados
                instance // Retorna a instância da base de dados
            }
        }
    }
}
