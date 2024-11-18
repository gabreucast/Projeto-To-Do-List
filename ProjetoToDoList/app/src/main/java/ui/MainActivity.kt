package ui

import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import model.ListEntity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<ListEntity>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewModel.inicializarDataBase(applicationContext)


        itemList = ArrayList<ListEntity>()
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = TaskAdapter(itemList)
        recyclerView.setAdapter(adapter)



        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            fragment.show(supportFragmentManager, "com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit")
        }


    }


}  //chave do MainActivity