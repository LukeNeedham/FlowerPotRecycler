package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate

/**
 * Config for the [ConfigurableRecyclerAdapter].
 * Allows for the customisation of initial [items],
 * [featureDelegateCreators] to create each [AdapterFeatureDelegate] to be added to the Adapter,
 * and the [positionDelegateCreator] for creating the [AdapterPositionDelegate] used by the Adapter
 */
interface RecyclerAdapterConfig<ItemType> {
    /** A list of items to show as the initial contents of the RecyclerView */
    var items: List<ItemType>

    /** A list of functions to create [AdapterFeatureDelegate]s */
    var featureDelegateCreators:
            MutableList<(adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterFeatureDelegate<ItemType>>

    /**
     * A function to create the [AdapterPositionDelegate],
     * or null to use the default as defined in [ConfigurableRecyclerAdapter]
     */
    var positionDelegateCreator:
                (adapter: DelegatedRecyclerAdapter<ItemType, *>) -> AdapterPositionDelegate<ItemType>?
}
