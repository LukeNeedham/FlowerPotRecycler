package com.lukeneedham.flowerpotrecycler

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.AllMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder
import com.lukeneedham.flowerpotrecycler.util.extensions.createBuilder
import kotlin.reflect.KClass

object SingleTypeAdapterBuilder {

    fun <ItemType : Any, ItemViewType : View> fromBuilderBinder(
        builderBinder: BuilderBinder<ItemType, ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>?
    ): DelegatedRecyclerAdapter<ItemType> {
        val registry = BuilderBinderRegistry(listOf(builderBinder))
        return ConfigurableRecyclerAdapter(registry, config)
    }

    fun <ItemType : Any, ItemViewType : View> fromBuilderAndBinder(
        config: RecyclerAdapterConfig<ItemType>?,
        builder: Builder<ItemViewType>,
        binder: Binder<ItemType, ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = ViewBuilderBinder(AllMatcher(), builder, binder)
        return fromBuilderBinder(builderBinder, config)
    }

    inline fun <ItemType : Any, reified ItemViewType : View> fromViewType(
        config: RecyclerAdapterConfig<ItemType>?,
        noinline binder: Binder<ItemType, ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType> {
        return fromViewClass(config, ItemViewType::class, binder)
    }

    inline fun <ItemType : Any, reified ItemViewType : View> fromViewClass(
        config: RecyclerAdapterConfig<ItemType>?,
        viewClass: KClass<ItemViewType>,
        noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType> {
        val builder = viewClass.createBuilder()
        return fromBuilderAndBinder(config, builder, binder)
    }

    fun <ItemType : Any, ItemViewType> fromRecyclerItemViewBuilder(
        config: RecyclerAdapterConfig<ItemType>?,
        builder: Builder<ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        val builderBinder = RecyclerItemViewBuilderBinder(AllMatcher(), builder)
        return fromBuilderBinder(builderBinder, config)
    }

    fun <ItemType : Any, ItemViewType> fromRecyclerItemViewClass(
        viewClass: KClass<ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>?
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        return fromRecyclerItemViewBuilder(config, viewClass.createBuilder())
    }

    inline fun <ItemType : Any, reified ItemViewType> fromRecyclerItemViewType(
        config: RecyclerAdapterConfig<ItemType>?
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        return fromRecyclerItemViewClass(ItemViewType::class, config)
    }
}
