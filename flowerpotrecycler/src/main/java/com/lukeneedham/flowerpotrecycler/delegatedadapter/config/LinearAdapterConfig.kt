package com.lukeneedham.flowerpotrecycler.delegatedadapter.config

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

/** A basic [RecyclerAdapterConfig] using the [LinearPositionDelegate] */
class LinearAdapterConfig<ItemType, ItemViewType> :
    RecyclerAdapterConfig<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override var items: List<ItemType> = emptyList()
    override var delegateCreators: MutableList<(adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>) -> AdapterFeatureDelegate<ItemType, ItemViewType>> =
        mutableListOf()
    override var positionDelegateCreator: (adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>) -> AdapterPositionDelegate<ItemType> =
        { LinearPositionDelegate(it, DefaultDiffCallback()) }
}