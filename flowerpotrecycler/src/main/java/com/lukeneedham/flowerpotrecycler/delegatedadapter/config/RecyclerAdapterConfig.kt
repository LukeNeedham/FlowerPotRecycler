package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate

/**
 * Config for the [DelegatedRecyclerAdapter].
 * Allows for the customisation of initial [items],
 * [delegateCreators] for the [AdapterFeatureDelegate] to use,
 * and the [positionDelegateCreator] for creating the [AdapterPositionDelegate]
 */
interface RecyclerAdapterConfig<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    var items: List<ItemType>
    var delegateCreators:
            MutableList<(adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>) -> AdapterFeatureDelegate<ItemType, ItemViewType>>
    var positionDelegateCreator:
                (adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>) -> AdapterPositionDelegate<ItemType>
}
