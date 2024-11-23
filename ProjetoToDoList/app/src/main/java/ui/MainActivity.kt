package ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
import com.gabreucast.projetotodolist.R
import model.ListEntity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<ListEntity>
    private lateinit var viewModel: MainViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.inicializarDataBase(applicationContext)

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = TaskAdapter(itemList)
        recyclerView.adapter = adapter

        viewModel.users.observe(this) { tasks ->
            tasks?.let {
                itemList.clear()
                itemList.addAll(it)
                adapter.updateList(itemList)
                adapter.notifyDataSetChanged()
            }
        }

        // Acção do botão addButton para adicionar uma tarefa
        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        // Acção do botão editTask para editar uma tarefa
        adapter.onEditClick = { task ->
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putString("title", task.title)
            bundle.putString("task", task.task)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        // Acção do botão deleteTask para deletar uma tarefa
        adapter.onDeleteClick = { task ->
            viewModel.deleteUser(task)
        }


    }
}

