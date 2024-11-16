package com.gabreucast.projetotodolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyItemAdapter
    private lateinit var itemList: MutableList<MyItens>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = ArrayList<MyItens>()
        initData()

        recyclerView = findViewById(R.id.taskRecyclerView)

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = MyItemAdapter(itemList)
        recyclerView.setAdapter(adapter)
    }

    private fun initData() {
        for (i in 1..10) {
            itemList.add(MyItens("Title $i", "Task $i"))
        }
    }

}