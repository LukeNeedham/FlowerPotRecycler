package com.lukeneedham.flowerpotrecycler.adapter.config

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate


/**
 * A basic [RecyclerAdapterConfig].
 * By default, it uses no feature delegates and no items,
 * with the position delegate set to the default defined by [DefaultDelegatedRecyclerAdapter]
 */
class AdapterConfig<ItemType : Any> : RecyclerAdapterConfig<ItemType> {
    override var items: List<ItemType> = emptyList()
    override var featureDelegateCreators: MutableList<(adapter: RecyclerView.Adapter<*>) -> AdapterFeatureDelegate<ItemType>> =
        mutableListOf()
    override var positionDelegateCreator: (adapter: RecyclerView.Adapter<*>) -> AdapterPositionDelegate<ItemType>? =
        { null }
}
