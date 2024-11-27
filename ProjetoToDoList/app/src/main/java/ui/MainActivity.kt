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
    private lateinit var emptyTextView: TextView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.inicializarDataBase(applicationContext)

        itemList = ArrayList()
        recyclerView = findViewById(R.id.taskRecyclerView)
        emptyTextView = findViewById(R.id.emptyTextView)

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

        // Ação do checkbox (marcar tarefa como concluída)
        adapter.onTaskChecked = { task ->
            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_title)) // Título de confirmação
                .setMessage(getString(R.string.confirm_message)) // Mensagem de confirmação
                .setPositiveButton(getString(R.string.confirm_positive)) { _, _ -> // Confirmação positiva
                    Toast.makeText(this, getString(R.string.confirm_taskdeleted), Toast.LENGTH_SHORT).show()
                    viewModel.deleteTask(task) // Exclui a tarefa
                }
                .setNegativeButton(getString(R.string.confirm_negative)) { dialog, _ -> // Confirmação negativa
                    dialog.dismiss() // Fecha o diálogo
                    recyclerView.findViewHolderForAdapterPosition(itemList.indexOf(task))
                        ?.let { holder ->
                            val checkBox = holder.itemView.findViewById<CheckBox>(R.id.checkTask) // Referência ao CheckBox
                            checkBox.isChecked = false // Desmarca o CheckBox
                        }
                }
                .create()
            alertDialog.show()
        }

        //////////////////////////////////////////////////////////////////////////////////////////
        // Configuração do ItemTouchHelper (para permitir arrastar itens na lista)
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                adapter.moveItem(fromPosition, toPosition) // Move o item na lista
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Não faz nada ao deslizar o item
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }  // Fim do onCreate
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Função para atualizar a interface quando a lista estiver vazia ou não
    private fun updateUI() {
        if (itemList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
    } // Fim do updateUI

}  // Fim da MainActivity
