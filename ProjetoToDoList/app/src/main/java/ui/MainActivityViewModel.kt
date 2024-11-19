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

    private val users: MutableLiveData<List<ListEntity>> = MutableLiveData()

    fun inicializarDataBase(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            database = AppDatabase.getDatabase(context)
            listUser()
        }
    }

    private fun listUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val listUsers = database.listDao().getAll()
            users.postValue(listUsers)
        }
    }

    fun addUser(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().insert(task)
            listUser()
        }
    }

    fun updateUser(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().update(task)
            listUser()
        }
    }

    fun deleteUser(task: ListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            database.listDao().delete(task)
            listUser()
        }
    }


}