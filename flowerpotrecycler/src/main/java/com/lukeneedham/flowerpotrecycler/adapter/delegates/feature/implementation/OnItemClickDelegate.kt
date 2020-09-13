package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

class OnItemClickDelegate<ItemType : Any, ItemViewType : View>(
    private val onItemClickListener: (item: ItemType, position: Int) -> Unit
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {
    override fun onItemClick(item: ItemType, position: Int) {
        onItemClickListener(item, position)
    }
}
