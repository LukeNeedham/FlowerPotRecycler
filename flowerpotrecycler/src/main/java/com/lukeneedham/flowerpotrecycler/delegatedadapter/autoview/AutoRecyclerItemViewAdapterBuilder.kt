package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview.ConfigurableSingleTypeRecyclerItemViewAdapter

object AutoRecyclerItemViewAdapterBuilder {
    fun <ItemType : Any> build(
        builderBinder: BuilderBinder<ItemType, View>,
        config: RecyclerAdapterConfig<ItemType>?
    ) = ConfigurableSingleTypeRecyclerItemViewAdapter(config) {
        AutoRecyclerItemView(it.context, builderBinder)
    }
}
