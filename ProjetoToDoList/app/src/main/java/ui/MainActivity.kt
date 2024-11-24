package ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.Fragmets.FragmentPrincipalEdit
import com.gabreucast.projetotodolist.R
import model.ListEntity

class MainActivity : AppCompatActivity() {

    // Inicializa as variáveis
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var itemList: MutableList<ListEntity>
    private lateinit var viewModel: MainViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.inicializarDataBase(applicationContext)

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Configura o adapter da RecyclerView
        adapter = TaskAdapter(itemList)
        recyclerView.adapter = adapter

        // Observa as mudanças nos dados
        viewModel.task.observe(this) { tasks ->
            tasks?.let {
                itemList.clear()
                itemList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }

        // Ação do botão de adicionar tarefa
        val addbutton = findViewById<Button>(R.id.addButton)
        addbutton.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putBoolean("isEditing", false) // Indica que é para adicionar uma tarefa
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        // Ação do botão de editar tarefa
        adapter.onEditClick = { task ->
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putBoolean("isEditing", true) // Indica que é para editar uma tarefa
            bundle.putString("title", task.title)
            bundle.putString("task", task.task)
            bundle.putLong("taskId", task.id)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        // Ação do botão de excluir tarefa
        adapter.onDeleteClick = { task ->
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_title)) // string: Confirmação
                .setMessage(getString(R.string.confirm_message)) // string: Você tem certeza de que deseja continuar?
                .setPositiveButton(getString(R.string.confirm_positive)) { dialog, which -> // string: Sim
                    Toast.makeText(
                        this, getString(R.string.confirm_taskdeleted), //Tarefa eliminada!
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.deleteTask(task)
                }
                .setNegativeButton(getString(R.string.confirm_negative)) { dialog, which -> //string: Não
                    dialog.dismiss()
                }.create()
            alertDialog.show()
        }

        // Ação do checkbox (marcar tarefa como concluída)
        adapter.onTaskChecked = { task ->
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_title)) //string: Confirmação
                .setMessage(getString(R.string.confirm_message)) //string: Você tem certeza de que deseja continuar?
                .setPositiveButton(getString(R.string.confirm_positive)) { _, _ -> // string: Sim
                    Toast.makeText(
                        this, getString(R.string.confirm_taskdeleted), // string: Tarefa eliminada!
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.deleteTask(task)
                }
                .setNegativeButton(getString(R.string.confirm_negative)) { dialog, _ -> // string: Não
                    dialog.dismiss()
                    recyclerView.findViewHolderForAdapterPosition(itemList.indexOf(task))
                        ?.let { holder ->
                            val checkBox =
                                holder.itemView.findViewById<CheckBox>(R.id.checkTask) // string: Marcar tarefa como concluída
                            checkBox.isChecked = false // Desmarca o CheckBox
                        }
                }.create()
            alertDialog.show()
        }  // chave do adapter.onTaskChecked
    }  // chave do onCreate
}  // chave da classe MainActivity
