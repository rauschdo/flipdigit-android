package de.rauschdo.flipdigit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DemosAdapter(private val dataset: List<Demo>) :
    RecyclerView.Adapter<DemosAdapter.ViewHolder>() {

    data class Demo(val title: String, val description: String, val activity: Class<*>)

    class ViewHolder(layout: LinearLayout) : RecyclerView.ViewHolder(layout) {
        var title = layout.findViewById(R.id.title) as TextView
        var description = layout.findViewById(R.id.description) as TextView
        var activity: Class<*>? = null

        init {
            layout.setOnClickListener {
                val context = it?.context as MainActivity
                activity?.let { itClass ->
                    context.start(itClass)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.row, parent, false) as LinearLayout
        return ViewHolder(row)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataset[position].title
        holder.description.text = dataset[position].description
        holder.activity = dataset[position].activity
    }

    override fun getItemCount() = dataset.size
}