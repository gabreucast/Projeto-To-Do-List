package com.gabreucast.projetotodolist.Fragmets

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.gabreucast.projetotodolist.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import model.ListEntity
import ui.MainViewModel

class FragmentPrincipalEdit : DialogFragment() {

    // Inicialização das variáveis para os campos de texto
    private lateinit var titleET: TextInputEditText
    private lateinit var taskET: TextInputEditText
    private lateinit var viewModel: MainViewModel
    private var isEditing: Boolean = false

    // Método que cria o diálogo do fragmento
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_principal_edit, null)

        // Verifica se estamos em modo de edição ou criação de tarefa
        isEditing = arguments?.getBoolean("isEditing", false) ?: false

        // Inicializa os campos de entrada de texto
        titleET = view.findViewById(R.id.titleET)
        taskET = view.findViewById(R.id.taskET)

        // Inicializa o ViewModel para interagir com os dados
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // Se estiver em modo de edição, carrega os dados da tarefa
        if (isEditing) {
            val title = arguments?.getString("title")
            val task = arguments?.getString("task")

            titleET.setText(title)
            taskET.setText(task)
        }

        // Criação do diálogo com título e botões
        return MaterialAlertDialogBuilder(requireActivity(), theme).setView(view)
            .setTitle(if (isEditing) getString(R.string.editTask) else getString(R.string.addTask))
            .setPositiveButton("OK") { dialog, _ ->
                // Pega os valores digitados nos campos de texto
                val title = titleET.text.toString().trim()
                val task = taskET.text.toString().trim()

                // Verifica se os campos estão preenchidos
                if (title.isEmpty() || task.isEmpty()) {
                    // Exibe um toast de erro caso algum campo esteja vazio
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.inserir_campos),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val newTask = ListEntity(title = title, task = task)

                    if (isEditing) {
                        // Se estamos editando, atualiza a tarefa existente
                        viewModel.updateTask(newTask)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.taskUpdated),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Se não estamos editando, cria uma nova tarefa
                        viewModel.addTask(newTask)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.tarefa_salva),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dismiss()
                }
            }
            // Caso o botão negativo seja pressionado, apenas fecha o diálogo
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
}
