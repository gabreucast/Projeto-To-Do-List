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

    private lateinit var titleET: TextInputEditText
    private lateinit var taskET: TextInputEditText
    private lateinit var viewModel: MainViewModel
    private var isEditing: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_principal_edit, null)

        // Verifica se o fragmento está em modo de edição
        isEditing = arguments?.getBoolean("isEditing", false) ?: false

        titleET = view.findViewById(R.id.titleET)
        taskET = view.findViewById(R.id.taskET)

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // Se for edição, preenche os campos com os dados existentes
        if (isEditing) {
            val title = arguments?.getString("title")
            val task = arguments?.getString("task")

            titleET.setText(title)
            taskET.setText(task)
        }

        // Cria o dialog com a interface apropriada
        return MaterialAlertDialogBuilder(requireActivity(), theme).setView(view)
            .setTitle(if (isEditing) getString(R.string.editTask) else getString(R.string.addTask))
                             // string: Editar Tarefa ou Adicionar Tarefa
            .setPositiveButton("OK") { dialog, _ ->
                val title = titleET.text.toString().trim()
                val task = taskET.text.toString().trim()

                // Verifica se os campos estão preenchidos
                if (title.isEmpty() || task.isEmpty()) {
                    // Exibe uma mensagem de erro se os campos estiverem vazios
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.inserir_campos), // string: Por favor, preencha todos os campos.
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isEditing) {
                        // Edita a tarefa existente
                        val taskId = arguments?.getLong("taskId") ?: 0
                        val updatedTask = ListEntity(id = taskId, title = title, task = task)
                        viewModel.updateTask(updatedTask)
                        // Exibe mensagem de sucesso
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.taskUpdated), // string: Tarefa atualizada
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Adiciona uma nova tarefa
                        val newTask = ListEntity(title = title, task = task)
                        viewModel.addTask(newTask)
                        // Exibe mensagem de sucesso
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.tarefa_salva), // string: Tarefa salva com sucesso!
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dismiss()
                }
            }
                // Cancela e fecha o diálogo
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    } // chave do onCreateDialog
} // chave do FragmentPrincipalEdit
