package com.lukeneedham.flowerpotrecycler.adapter.config

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateCreator
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureDelegateConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

class SingleTypeAdapterConfig<ItemType : Any, ItemViewType : View> :
    RecyclerAdapterConfig<ItemType, ItemViewType>,
    FeatureDelegateConfig<ItemType, ItemViewType> {

    override var items: List<ItemType> = emptyList()

    override var positionDelegateCreator:
                (adapter: RecyclerView.Adapter<*>) -> AdapterPositionDelegate<ItemType>? = { null }

    override var delegateCreators: MutableList<FeatureDelegateCreator<ItemType, ItemViewType>> =
        mutableListOf()
}
