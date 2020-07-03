package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

/** A [DelegatedRecyclerAdapter] with default setup values */
abstract class DefaultDelegatedRecyclerAdapter<ItemType, ItemViewType>
    : DelegatedRecyclerAdapter<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<ItemType> by lazy {
        LinearPositionDelegate<ItemType>(this, DefaultDiffCallback())
    }
}