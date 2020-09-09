package com.lukeneedham.flowerpotrecycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/** A base RecyclerView Adapter to encourage a delegated approach */
abstract class DelegatedRecyclerAdapter<BaseItemType : Any> : RecyclerView.Adapter<ViewHolder>() {

    abstract val featureDelegates: List<AdapterFeatureDelegate<BaseItemType>>
    abstract val positionDelegate: AdapterPositionDelegate<BaseItemType>
    abstract val builderBinderRegistry: BuilderBinderRegistry<BaseItemType>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = builderBinderRegistry.build(parent, viewType)
        val viewHolder = ViewHolder(view)
        featureDelegates.forEach {
            it.onViewHolderCreated(viewHolder, parent, viewType)
        }
        return viewHolder
    }

    override fun getItemCount() = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        val itemView = holder.itemView
        builderBinderRegistry.bind(holder, position, item)
        itemView.setOnClickListener {
            featureDelegates.forEach {
                it.onItemClick(item, position)
            }
        }
        featureDelegates.forEach {
            it.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = positionDelegate.getItemAt(position)
        return builderBinderRegistry.getTypeId(item)
    }

    open fun submitList(newItems: List<BaseItemType>, onDiffDone: () -> Unit = {}) {
        builderBinderRegistry.assertItemsHandled(newItems)
        positionDelegate.submitList(newItems, onDiffDone)
    }
}
