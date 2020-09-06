package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import com.lukeneedham.flowerpotrecycler.delegatedadapter.SingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate


/**
 * A basic [RecyclerAdapterConfig].
 * By default, it uses no feature delegates and no items,
 * with the position delegate set to the default defined by [ConfigurableRecyclerAdapter]
 */
class AdapterConfig<ItemType : Any> : RecyclerAdapterConfig<ItemType> {
    override var items: List<ItemType> = emptyList()
    override var featureDelegateCreators: MutableList<(adapter: SingleTypeRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>> =
        mutableListOf()
    override var positionDelegateCreator: (adapter: SingleTypeRecyclerAdapter<ItemType, *>) -> AdapterPositionDelegate<ItemType>? =
        { null }
}
