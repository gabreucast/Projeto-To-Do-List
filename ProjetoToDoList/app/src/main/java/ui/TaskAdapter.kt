package ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import model.ListEntity

class TaskAdapter(private val taskList: MutableList<ListEntity>) :
    RecyclerView.Adapter<TaskAdapter.TaskVH>() {

    var onEditClick: ((ListEntity) -> Unit)? = null
    var onDeleteClick: ((ListEntity) -> Unit)? = null
    var onTaskChecked: ((ListEntity) -> Unit)? = null

    inner class TaskVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.titleTV)
        val taskTV: TextView = itemView.findViewById(R.id.taskTV)
        val deleteTask: ImageView = itemView.findViewById(R.id.deleteTask)
        val editTask: ImageView = itemView.findViewById(R.id.editTask)
        val checkTask: CheckBox = itemView.findViewById(R.id.checkTask)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val itemView =
            LayoutInflater.from(parent.context).
            inflate(R.layout.fragment_task_list, parent, false)
        return TaskVH(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val currentTask = taskList[position]
        holder.titleTV.text = currentTask.title
        holder.taskTV.text = currentTask.task

        // Configuração do CheckBox
        holder.checkTask.setOnCheckedChangeListener(null)
        holder.checkTask.isChecked = false
        holder.checkTask.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onTaskChecked?.invoke(currentTask) // Llama a la lambda al marcar
            }
        }

        // Mejorar la interacción de los botones (editar y eliminar)
        holder.editTask.setOnClickListener {
            onEditClick?.invoke(currentTask)
        }
        holder.deleteTask.setOnClickListener {
            onDeleteClick?.invoke(currentTask)
        }

    } // llave del onBindViewHolder


    /////////////////////////////////////////////////////////////////////////////////////////
    // Move um item de uma posição para outra na lista
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val task = taskList.removeAt(fromPosition) // Remove o item da posição original
        taskList.add(toPosition, task) // Adiciona o item na nova posição
        notifyItemMoved(fromPosition, toPosition) // Notifica a mudança de posição
    }  // Fim de moveItem

    /////////////////////////////////////////////////////////////////////////////////////////

} // chave do TaskAdapter
