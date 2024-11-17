package ui

import model.Task
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = ArrayList<Task>()
        initData()

        recyclerView = findViewById(R.id.taskRecyclerView)

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = TaskAdapter(itemList)
        recyclerView.setAdapter(adapter)
    }

    private fun initData() {
        for (i in 1..10) {
            itemList.add(Task("Title $i", "Task $i"))
        }
    }

}