package ui


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.ListEntity


class MainViewModel : ViewModel() {   /// chave do MainViewModel

    private lateinit var database: AppDatabase

    val task: MutableLiveData<List<ListEntity>> = MutableLiveData()

    fun inicializarDataBase(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            database = AppDatabase.getDatabase(context)
            listTask()
        }
    }

    private fun listTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val listTask = database.listDao().getAll()
            task.postValue(listTask)
        }
    }

    fun addTask(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().insert(task)
            listTask()
        }
    }

    fun updateTask(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().update(task)
            listTask()
        }
    }

    fun deleteTask(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().delete(task)
            listTask()
        }
    }


}