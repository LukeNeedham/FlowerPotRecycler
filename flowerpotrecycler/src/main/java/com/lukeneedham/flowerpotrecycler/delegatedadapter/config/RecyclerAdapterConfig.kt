package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate

/**
 * Config for the [DelegatedRecyclerAdapter].
 * Allows for the customisation of initial [items],
 * [delegateCreators] to create each [AdapterFeatureDelegate] to be added to the Adapter,
 * and the [positionDelegateCreator] for creating the [AdapterPositionDelegate] used by the Adapter
 */
interface RecyclerAdapterConfig<ItemType> {
    var items: List<ItemType>
    var delegateCreators:
            MutableList<(adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>>
    var positionDelegateCreator:
                (adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterPositionDelegate<ItemType>
}
