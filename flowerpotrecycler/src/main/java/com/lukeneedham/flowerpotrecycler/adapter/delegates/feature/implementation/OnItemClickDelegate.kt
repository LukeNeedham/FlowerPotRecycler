package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

class OnItemClickDelegate<ItemType : Any, ItemViewType : View>(
    private val onItemClickListener: (item: ItemType, position: Int, itemView: ItemViewType) -> Unit
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {
    override fun onItemClick(item: ItemType, position: Int, itemView: ItemViewType) {
        onItemClickListener(item, position, itemView)
    }
}
