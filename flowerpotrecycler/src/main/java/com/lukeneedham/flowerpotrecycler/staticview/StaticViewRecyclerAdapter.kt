package com.lukeneedham.flowerpotrecycler.staticview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.staticview.config.StaticViewRecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.staticview.delegates.StaticViewAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates

/** A simple adapter to show a single static view, which does no binding */
class StaticViewRecyclerAdapter(
    config: StaticViewRecyclerAdapterConfig?,
    private val itemViewCreator: (Context) -> View
) : RecyclerView.Adapter<StaticViewRecyclerViewHolder>() {

    private val featureDelegates: List<StaticViewAdapterFeatureDelegate> =
        config?.getFeatureDelegates(this) ?: emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StaticViewRecyclerViewHolder {
        val view = itemViewCreator(parent.context)
        view.setOnClickListener {
            featureDelegates.forEach {
                it.onClick()
            }
        }
        val viewHolder = StaticViewRecyclerViewHolder(view)
        featureDelegates.forEach {
            it.onViewHolderCreated(viewHolder, parent, viewType)
        }
        return viewHolder
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: StaticViewRecyclerViewHolder, position: Int) {
        // NOOP: It's a static view, so no binding needs to happen
    }
}
