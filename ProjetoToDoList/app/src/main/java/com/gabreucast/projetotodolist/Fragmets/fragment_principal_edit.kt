package com.gabreucast.projetotodolist.Fragmets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.gabreucast.projetotodolist.R
import db.AppDatabase
import model.ListEntity

class FragmentPrincipalEdit : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal_edit, container, false)

        // Referencias dos botões
        val cancelTextView = view.findViewById<TextView>(R.id.cancel)
        val editNoteFab = view.findViewById<View>(R.id.editNoteFab)
        val titleET = view.findViewById<EditText>(R.id.titleET)
        val taskET = view.findViewById<EditText>(R.id.taskET)
        val database = AppDatabase.getDatabase(requireContext())

        // Fecha o dialogo
        cancelTextView.setOnClickListener {
            dismiss()
        }

        // Verificar os campos quando clicado no Floating Button
        editNoteFab.setOnClickListener {
            val title = titleET.text.toString().trim()
            val task = taskET.text.toString().trim()

            if (title.isEmpty() || task.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "por favor insira todos os campos.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val newTask = ListEntity(title = title, task = task)
                database.listDao().insert(newTask)

                // Mostrar mensagem de sucesso
                Toast.makeText(requireContext(), "Tarefa salva com sucesso!", Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            }
        }

        return view
    }
}
