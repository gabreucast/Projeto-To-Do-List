package ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
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
    private lateinit var emptyImageView: AppCompatImageView
    private lateinit var createTaskTextView: TextView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.inicializarDataBase(applicationContext)

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        createTaskTextView = findViewById(R.id.createTaskTextView)
        emptyImageView = findViewById(R.id.emptyImageView)

        // Configura a RecyclerView com LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Configura o adapter para a RecyclerView
        adapter = TaskAdapter(itemList)
        recyclerView.adapter = adapter

        // Observa as mudanças nos dados da ViewModel
        viewModel.task.observe(this) { tasks ->
            tasks?.let {
                itemList.clear()
                itemList.addAll(it)
                adapter.notifyDataSetChanged()
                updateUI() // Atualiza a interface do usuário
            }
        }

        // Ação para o botão de adicionar tarefa
        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val fragment = FragmentPrincipalEdit()
            val bundle = Bundle()
            bundle.putBoolean("isEditing", false) // Indica que é para adicionar uma tarefa
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "FragmentPrincipalEdit")
        }

        // Ação para o botão de editar tarefa
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

        // Ação para o botão de excluir tarefa
        adapter.onDeleteClick = { task ->
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_title)) // Título de confirmação
                .setMessage(getString(R.string.confirm_message)) // Mensagem de confirmação
                .setPositiveButton(getString(R.string.confirm_positive)) { _, _ -> // Confirmação positiva
                    Toast.makeText(this, getString(R.string.confirm_taskdeleted), Toast.LENGTH_SHORT).show()
                    viewModel.deleteTask(task) // Exclui a tarefa
                }
                .setNegativeButton(getString(R.string.confirm_negative)) { dialog, _ -> // Confirmação negativa
                    dialog.dismiss() // Fecha o diálogo
                }
                .create()
            alertDialog.show()
        }
    }  // Fim do onCreate

    // Função para atualizar a interface quando a lista estiver vazia ou não
    private fun updateUI() {
        if (itemList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyImageView.visibility = View.VISIBLE
            createTaskTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyImageView.visibility = View.GONE
            createTaskTextView.visibility = View.GONE
        }// Chave do else
    } // Chave do updateUI

}  // Chave da MainActivity
