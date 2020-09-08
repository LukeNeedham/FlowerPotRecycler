package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.AdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.DefaultPositionDelegate

/** A [SingleTypeRecyclerItemViewAdapter] with default setup values */
abstract class DefaultSingleTypeRecyclerItemViewAdapter<ItemType : Any, ItemViewType>(
) : SingleTypeRecyclerItemViewAdapter<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val featureDelegates: List<AdapterFeatureDelegate<ItemType>> = emptyList()

    override val positionDelegate: AdapterPositionDelegate<ItemType> by lazy {
        DefaultPositionDelegate.create<ItemType>(this)
    }
}
