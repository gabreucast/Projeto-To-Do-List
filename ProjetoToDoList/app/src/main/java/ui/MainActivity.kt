package ui

import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import model.ListEntity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<ListEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(itemList)
        recyclerView.adapter = adapter

        adapter.onEditClick = { task ->
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putString("title", task.title)
            bundle.putString("task", task.task)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        adapter.onDeleteClick = { task ->
            itemList.remove(task)
            adapter.updateList(itemList)
        }

        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            fragment.show(supportFragmentManager, "com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit")
        }
    }
}
