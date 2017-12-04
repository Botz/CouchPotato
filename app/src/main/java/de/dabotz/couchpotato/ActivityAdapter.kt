package de.dabotz.couchpotato

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import de.dabotz.couchpotato.databinding.ActivityListItemBinding
import de.dabotz.couchpotato.db.Activity

/**
 * Created by Botz on 03.12.17.
 */
class ActivityAdapter: RecyclerView.Adapter<ActivityViewHolder>() {
    val items: MutableList<Activity> = mutableListOf()

    override fun getItemViewType(position: Int) = R.layout.activity_list_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ActivityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

class ActivityViewHolder(var dataBinding: ActivityListItemBinding): RecyclerView.ViewHolder(dataBinding.root) {
    fun bind(activity: Activity) {
        dataBinding.activity = activity
        dataBinding.executePendingBindings()
    }
}