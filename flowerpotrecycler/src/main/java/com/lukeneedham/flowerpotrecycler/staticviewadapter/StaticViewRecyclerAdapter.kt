package com.lukeneedham.flowerpotrecycler.staticviewadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.staticviewadapter.config.StaticViewRecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.StaticViewAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.util.getFeatureDelegates

/**
 * A simple adapter wrapper around a static view.
 * Since the view is static, no binding is required, and no items need to be provided.
 * The adapter will always show the view a single time.
 */
class StaticViewRecyclerAdapter(
    config: StaticViewRecyclerAdapterConfig?,
    private val itemViewCreator: Builder<View>
) : RecyclerView.Adapter<StaticViewRecyclerViewHolder>() {

    private val featureDelegates: List<StaticViewAdapterFeatureDelegate> =
        config?.getFeatureDelegates(this) ?: emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StaticViewRecyclerViewHolder {
        val view = itemViewCreator(parent)
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
