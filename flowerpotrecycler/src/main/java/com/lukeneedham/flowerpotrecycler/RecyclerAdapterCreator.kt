package com.lukeneedham.flowerpotrecycler

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig

/** Creates multi-type adapters */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object RecyclerAdapterCreator {

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param itemTypeConfigs a list of [BuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param adapterConfig Configuration for the adapter
     */
    fun <BaseItemType : Any, BaseItemViewType : View> fromItemTypeConfigList(
        itemTypeConfigs: List<ItemTypeConfig<out BaseItemType, out BaseItemViewType>>,
        adapterConfig: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType, BaseItemViewType> =
        ConfigurableRecyclerAdapter(itemTypeConfigs, adapterConfig)

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param itemTypeConfigs a list of [BuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param adapterConfig Configuration for the adapter
     */
    fun <BaseItemType : Any, BaseItemViewType : View> fromItemTypeConfigs(
        vararg itemTypeConfigs: ItemTypeConfig<out BaseItemType, out BaseItemViewType>,
        adapterConfig: RecyclerAdapterConfig<BaseItemType, BaseItemViewType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType, BaseItemViewType> =
        fromItemTypeConfigList(itemTypeConfigs.toList(), adapterConfig)
}
