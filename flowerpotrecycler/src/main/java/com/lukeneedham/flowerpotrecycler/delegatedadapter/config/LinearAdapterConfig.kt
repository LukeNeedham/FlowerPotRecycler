package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

/** A basic [RecyclerAdapterConfig] using the [LinearPositionDelegate] */
class LinearAdapterConfig<ItemType> : RecyclerAdapterConfig<ItemType> {

    override var items: List<ItemType> = emptyList()
    override var delegateCreators: MutableList<(adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>> =
        mutableListOf()
    override var positionDelegateCreator: (adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterPositionDelegate<ItemType> =
        { LinearPositionDelegate(it, DefaultDiffCallback()) }
}