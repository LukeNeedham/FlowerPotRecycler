package com.lukeneedham.flowerpotrecycler

import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig

/** For multi type adapters */
@Suppress("unused")
object RecyclerAdapterBuilder {

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param builderBinderRegistry handles creating views and binding items on behalf of the adapter
     * @param config Configuration for the adapter
     */
    fun <BaseItemType : Any> fromBuilderBinderRegistry(
        builderBinderRegistry: BuilderBinderRegistry<BaseItemType>,
        config: RecyclerAdapterConfig<BaseItemType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType> =
        ConfigurableRecyclerAdapter(builderBinderRegistry, config)

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param builderBinders a list of [BuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param config Configuration for the adapter
     */
    fun <BaseItemType : Any> fromBuilderBinders(
        vararg builderBinders: BuilderBinder<out BaseItemType, *>,
        config: RecyclerAdapterConfig<BaseItemType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType> =
        ConfigurableRecyclerAdapter(
            BuilderBinderRegistry(listOf(*builderBinders)),
            config
        )
}
