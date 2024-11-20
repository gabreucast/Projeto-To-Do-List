package ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
import model.ListEntity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<ListEntity>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(itemList)
        recyclerView.adapter = adapter

        viewModel.users.observe(this) { tasks ->
            tasks?.let {
                itemList.clear()
                itemList.addAll(it)
                adapter.updateList(itemList)
            }
        }

        viewModel.inicializarDataBase(applicationContext)

        adapter.onEditClick = { task ->
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putString("title", task.title)
            bundle.putString("task", task.task)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        adapter.onDeleteClick = { task ->
            viewModel.deleteUser(task)
        }

        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }
    }
}


//package ui
//
//import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
//import android.os.Bundle
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.room.Room
//import com.gabreucast.projetotodolist.R
//import db.AppDatabase
//import model.ListEntity
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: TaskAdapter
//    private lateinit var itemList: MutableList<ListEntity>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        itemList = ArrayList()
//        recyclerView = findViewById(R.id.taskRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        adapter = TaskAdapter(itemList)
//        recyclerView.adapter = adapter
//
//
//        val database = Room.databaseBuilder(
//            this, AppDatabase::class.java, "app_database"
//            ).allowMainThreadQueries()
//            .build()
//
//        database.listDao().insert(ListEntity(title = "Estudiar", task = "Examen de Física"))
//        database.listDao().insert(ListEntity(title = "Mercado", task = "Comprar papa y cebolla"))
//
//        val tasks = database.listDao().getAll()
//        itemList.addAll(tasks)
//        adapter.updateList(itemList)
//
//
//        adapter.onEditClick = { task ->
//            val fragment = FragmentPrincipalEdit()
//            val bundle = Bundle()
//            bundle.putString("title", task.title)
//            bundle.putString("task", task.task)
//            fragment.arguments = bundle
//            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
//        }
//
//        adapter.onDeleteClick = { task ->
//            itemList.remove(task)
//            adapter.updateList(itemList)
//        }
//
//        val button = findViewById<Button>(R.id.addButton)
//        button.setOnClickListener {
//            val fragment = FragmentPrincipalEdit()
//            fragment.show(supportFragmentManager, "com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit")
//        }
//    }
//}
