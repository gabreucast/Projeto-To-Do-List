package com.gabreucast.projetotodolist.Fragmets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import com.gabreucast.projetotodolist.R
import db.AppDatabase
import model.ListEntity

class FragmentPrincipalEdit : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_principal_edit, container, false)

        // Referência do botões
        val cancelTextView = view.findViewById<TextView>(R.id.cancel)
        val editNoteFab = view.findViewById<View>(R.id.editNoteFab)
        val titleET = view.findViewById<EditText>(R.id.titleET)
        val taskET = view.findViewById<EditText>(R.id.taskET)

        // Fecha o dialogo
        cancelTextView.setOnClickListener {
            dismiss()
        }

        // Verificar os campos do quando clicado Floating Button
        editNoteFab.setOnClickListener {
            val title = titleET.text.toString().trim()
            val task = taskET.text.toString().trim()

            if (title.isEmpty() || task.isEmpty()) {
                Toast.makeText(requireContext(), "Insira todos os campos, por favor", Toast.LENGTH_SHORT).show()
            } else {
                // Salva no bancoo de dados se os 2 campos estiverem preenchidos
                val database = Room.databaseBuilder(
                    requireContext().applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).allowMainThreadQueries().build()

                val newTask = ListEntity(title = title, task = task)
                database.listDao().insert(newTask)

                // Mostrar mensagem de sucesso
                Toast.makeText(requireContext(), "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        return view
    }
}
