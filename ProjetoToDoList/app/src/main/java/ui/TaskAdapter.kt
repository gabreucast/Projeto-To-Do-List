package ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import model.ListEntity


class TaskAdapter(private val taskList: MutableList<ListEntity>) : RecyclerView.Adapter<TaskAdapter.TaskVH>() {

    inner class TaskVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.titleTV)
        val taskTV: TextView = itemView.findViewById(R.id.taskTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_task_list, parent, false)
        return TaskVH(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val currentTask = taskList[position]
        holder.titleTV.text = currentTask.title
        holder.taskTV.text = currentTask.task
    }

    fun updateList(newList: List<ListEntity>) {
        taskList.clear()
        taskList.addAll(newList)
        notifyDataSetChanged()
    }
}


