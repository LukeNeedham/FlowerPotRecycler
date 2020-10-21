package com.lukeneedham.flowerpotrecycler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.config.SingleTypeAdapterConfig
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.config.FeatureConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.declarative.DeclarativeBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view.ViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.xml.XmlBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.config.ItemTypeConfig
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.AllMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createReflectiveBuilder

/** Creates Adapters which handle a single type of item with a single view type */
object SingleTypeRecyclerAdapterCreator {

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
    inline fun <reified ItemType> fromDeclarativeDsl(
        config: SingleTypeAdapterConfig<ItemType, View>? = null,
        noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
    ): DelegatedRecyclerAdapter<ItemType, View> {
        val builderBinder = DeclarativeBuilderBinder(builder)
        return fromBuilderBinder(builderBinder, config)
    }

    /**
     * @return a [DelegatedRecyclerAdapter] to handle a single item type, with an XML layout.
     *
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param config Optional configuration for the adapter
     * @param binder The callback for binding each item to its View
     */
    inline fun <reified ItemType> fromXml(
        @LayoutRes layoutResId: Int,
        config: SingleTypeAdapterConfig<ItemType, View>? = null,
        noinline binder: Binder<ItemType, View> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType, View> {
        val builderBinder = XmlBuilderBinder(layoutResId, binder)
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
    inline fun <reified ItemType, reified ItemViewType> fromRecyclerItemView(
        config: SingleTypeAdapterConfig<ItemType, ItemViewType>? = null,
        noinline builder: Builder<ItemViewType> = createReflectiveBuilder()
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        val builderBinder = RecyclerItemViewBuilderBinder(builder)
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
    inline fun <reified ItemType, reified ItemViewType : View> fromView(
        config: SingleTypeAdapterConfig<ItemType, ItemViewType>? = null,
        noinline builder: Builder<ItemViewType> = createReflectiveBuilder(),
        noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType> {
        val builderBinder = ViewBuilderBinder(builder, binder)
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
    inline fun <reified ItemType, ItemViewType : View> fromBuilderBinder(
        builderBinder: BuilderBinder<ItemType, ItemViewType>,
        config: SingleTypeAdapterConfig<ItemType, ItemViewType>? = null
    ): DelegatedRecyclerAdapter<ItemType, ItemViewType> {
        val matcher = AllMatcher()
        val featureConfig = config ?: FeatureConfig<ItemType, ItemViewType>()
        val builderBinderSetup = ItemTypeConfig(
            builderBinder,
            featureConfig,
            matcher
        )
        return RecyclerAdapterCreator.fromItemTypeConfigs(listOf(builderBinderSetup), config)
    }
}
