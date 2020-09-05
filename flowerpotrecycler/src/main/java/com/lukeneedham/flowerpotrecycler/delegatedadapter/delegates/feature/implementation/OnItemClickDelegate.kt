package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation

import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate

class OnItemClickDelegate<ItemType>(
    private val onItemClickListener: (item: ItemType, position: Int) -> Unit
) : BaseAdapterFeatureDelegate<ItemType>() {
    override fun onItemClick(item: ItemType, position: Int) {
        onItemClickListener(item, position)
    }
}
