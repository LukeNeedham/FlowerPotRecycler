package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.ConfigurableSingleTypeRecyclerAdapter

object AutoViewRecyclerAdapterBuilder {
    inline fun <reified ItemType : Any> build(
        builderBinder: ItemBuilderBinder<ItemType, View>,
        config: RecyclerAdapterConfig<ItemType>?
    ) = ConfigurableSingleTypeRecyclerAdapter(ItemType::class, config) {
        AutoRecyclerItemView(it, builderBinder)
    }
}
