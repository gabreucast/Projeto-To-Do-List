package ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabreucast.projetotodolist.R
import model.Task

class TaskAdapter(private val userList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.UserVH>() {

    inner class UserVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.titleTV)
        val taskTV: TextView = itemView.findViewById(R.id.taskTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_task_list, parent, false)
        return UserVH(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val currentUser = userList[position]
        holder.titleTV.text = currentUser.title
        holder.taskTV.text = currentUser.task
    }
}
