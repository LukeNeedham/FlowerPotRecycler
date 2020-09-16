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
import com.lukeneedham.flowerpotrecycler.adapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createBuilder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

/** Builds Adapters which handle a single type of item with a single view type */
object SingleTypeRecyclerAdapterBuilder {

    /**
     * @return a [DelegatedRecyclerAdapter] to handle a single item type,
     * with an item View built programmatically (for example, with Anko DSL Layouts).
     *
     * Use [DeclarativeBindingDsl.onItem]
     * within your builder to update the UI when an item is bound.
     *
     * @param config Optional configuration for the adapter
     * @param builder The callback for building item Views
     */
    inline fun <reified ItemType : Any> fromDeclarativeDsl(
        config: RecyclerAdapterConfig<ItemType, View>? = null,
        noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
    ): DelegatedRecyclerAdapter<ItemType, View> {
        val matcher = ClassMatcher(ItemType::class)
        val builderBinder = DeclarativeBuilderBinder(matcher, builder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * @return a [DelegatedRecyclerAdapter] to handle a single item type, with an XML layout.
     *
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param config Optional configuration for the adapter
     * @param binder The callback for binding each item to its View
     */
    inline fun <reified ItemType : Any> fromXml(
        @LayoutRes layoutResId: Int,
        config: RecyclerAdapterConfig<ItemType, View>? = null,
        noinline binder: Binder<ItemType, View> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType, View> {
        val matcher = ClassMatcher(ItemType::class)
        val builderBinder = XmlBuilderBinder(layoutResId, matcher, binder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * @return a [DelegatedRecyclerAdapter] to handle a single item type,
     * with a [RecyclerItemView] of type [ItemViewType].
     *
     * Binding is handled with [RecyclerItemView.setItem].
     *
     * @param config Configuration for the adapter
     * @param builder The callback to instantiate your [ItemViewType] class
     */
    inline fun <reified ItemType : Any, reified ItemViewType> fromRecyclerItemView(
        config: RecyclerAdapterConfig<ItemType, ItemViewType>? = null,
        noinline builder: Builder<ItemViewType> = createBuilder()
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        val matcher = ClassMatcher(ItemType::class)
        val builderBinder = RecyclerItemViewBuilderBinder(matcher, builder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * If your [ItemViewType] is a [RecyclerItemView], prefer to use [fromRecyclerItemView] instead.
     *
     * @return a [DelegatedRecyclerAdapter] to handle a single item type,
     * with a [View] of type [ItemViewType].
     *
     * @param config Configuration for the adapter
     * @param builder The callback to instantiate your [ItemViewType] class
     * @param binder The callback for binding each item to its View
     */
    inline fun <reified ItemType : Any, reified ItemViewType : View> fromView(
        config: RecyclerAdapterConfig<ItemType, ItemViewType>? = null,
        noinline builder: Builder<ItemViewType> = createBuilder(),
        noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType> {
        val matcher = ClassMatcher(ItemType::class)
        val builderBinder = ViewBuilderBinder(matcher, builder, binder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * @return a [DelegatedRecyclerAdapter] to handle a single item type,
     * with a single [BuilderBinder].
     *
     * @param builderBinder The [BuilderBinder] used to instantiate your views of type [ItemViewType],
     * and to bind items of type [ItemType] to them.
     * @param config Configuration for the adapter
     */
    fun <ItemType : Any, ItemViewType : View> fromBuilderBinder(
        builderBinder: BuilderBinder<ItemType, ItemViewType>,
        config: RecyclerAdapterConfig<ItemType, ItemViewType>?
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType> {
        val registry = BuilderBinderRegistry(listOf(builderBinder))
        return ConfigurableRecyclerAdapter(registry, config)
    }
}
