package com.lukeneedham.flowerpotrecycler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.adapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.declarative.DeclarativeBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.xml.XmlBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createBuilder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

/** Builds Adapters which handle a single type of item with a single view type */
object SingleTypeRecyclerAdapterBuilder {

    /**
     * Auto-generate an adapter.
     * To be used when creating the item View programmatically (for example, with Anko DSL Layouts)
     *
     * Use [DeclarativeBindingDsl.onItem]
     * within your builder to update the UI when an item is bound.
     * @param builder The callback for building item Views
     * @param config Optional configuration for the adapter
     */
    inline fun <reified ItemType : Any> fromDeclarativeDsl(
        config: RecyclerAdapterConfig<ItemType>? = null,
        matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
        noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = DeclarativeBuilderBinder(matcher, builder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * Auto-generate an adapter.
     * To be used when creating the item View from inflating an XML layout.
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param config Optional configuration for the adapter
     * @param binder The callback for binding each item to its View
     */
    inline fun <reified ItemType : Any> fromXml(
        @LayoutRes layoutResId: Int,
        config: RecyclerAdapterConfig<ItemType>? = null,
        matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
        noinline binder: Binder<ItemType, View> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = XmlBuilderBinder(layoutResId, matcher, binder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * Create a [DelegatedRecyclerAdapter] to handle a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class. Binding is handled with [RecyclerItemView.setItem]
     * @param config Configuration for the adapter
     * @param builder The function to instantiate your [ItemViewType] class
     */
    inline fun <reified ItemType : Any, reified ItemViewType> fromRecyclerItemView(
        config: RecyclerAdapterConfig<ItemType>? = null,
        matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
        noinline builder: Builder<ItemViewType> = createBuilder()
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        val builderBinder = RecyclerItemViewBuilderBinder(matcher, builder)
        return fromBuilderBinder(builderBinder, config)
    }

    inline fun <reified ItemType : Any, reified ItemViewType : View> fromView(
        config: RecyclerAdapterConfig<ItemType>? = null,
        matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
        noinline builder: Builder<ItemViewType> = createBuilder(),
        noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = ViewBuilderBinder(matcher, builder, binder)
        return fromBuilderBinder(builderBinder, config)
    }

    fun <ItemType : Any, ItemViewType : View> fromBuilderBinder(
        builderBinder: BuilderBinder<ItemType, ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>?
    ): DelegatedRecyclerAdapter<ItemType> {
        val registry = BuilderBinderRegistry(listOf(builderBinder))
        return ConfigurableRecyclerAdapter(registry, config)
    }
}
