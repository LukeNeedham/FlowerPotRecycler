package com.lukeneedham.flowerpotrecycler

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtypedelegate.ItemTypeBuilder
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig

/** Builds multi-type adapters */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object RecyclerAdapterBuilder {

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param itemTypeBuilders a list of [BuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param config Configuration for the adapter
     */
    fun <BaseItemType : Any, BaseItemViewType : View> fromItemTypeBuilderList(
        itemTypeBuilders: List<ItemTypeBuilder<out BaseItemType, out BaseItemViewType>>,
        config: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType, BaseItemViewType> =
        ConfigurableRecyclerAdapter(itemTypeBuilders, config)

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param itemTypeBuilders a list of [BuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param config Configuration for the adapter
     */
    fun <BaseItemType : Any, BaseItemViewType : View> fromItemTypeBuilders(
        vararg itemTypeBuilders: ItemTypeBuilder<out BaseItemType, out BaseItemViewType>,
        config: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType, BaseItemViewType> =
        fromItemTypeBuilderList(itemTypeBuilders.toList(), config)
}
