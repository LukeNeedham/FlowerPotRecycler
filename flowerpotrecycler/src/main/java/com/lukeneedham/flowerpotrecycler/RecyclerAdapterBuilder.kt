package com.lukeneedham.flowerpotrecycler

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.declarative.DeclarativeBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.xml.XmlLabmdaBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.AllMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder
import kotlin.reflect.KClass

/** For multi type adapters */
@Suppress("unused")
object RecyclerAdapterBuilder {


    /*** Multi-type ***/


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
        ConfigurableRecyclerAdapter(BuilderBinderRegistry.from(*builderBinders), config)


    /*** From DSL ***/


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
        noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = DeclarativeBuilderBinder.fromType(builder)
        return SingleTypeAdapterBuilder.fromBuilderBinder(builderBinder, config)
    }


    /*** From XML ***/


    /**
     * Auto-generate an adapter.
     * To be used when creating the item View from inflating an XML layout.
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param binder The callback for binding each item to its View
     * @param config Optional configuration for the adapter
     */
    fun <ItemType : Any> fromXml(
        @LayoutRes layoutResId: Int,
        config: RecyclerAdapterConfig<ItemType>? = null,
        binder: Binder<ItemType, View>
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = XmlLabmdaBuilderBinder(
            layoutResId,
            AllMatcher()
        ) { itemView: View, position: Int, item: ItemType ->
            binder(itemView, position, item)
        }
        return SingleTypeAdapterBuilder.fromBuilderBinder(builderBinder, config)
    }

    /**
     * Auto-generate an adapter.
     * To be used when creating the item view from inflating an XML layout,
     * and the view doesn't require any binding.
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param config Optional configuration for the adapter
     */
    fun <ItemType : Any> fromStaticXml(
        @LayoutRes layoutResId: Int,
        config: RecyclerAdapterConfig<ItemType>? = null
    ): DelegatedRecyclerAdapter<ItemType> {
        val builderBinder = XmlLabmdaBuilderBinder.fromStatic<ItemType>(layoutResId, AllMatcher())
        return SingleTypeAdapterBuilder.fromBuilderBinder(builderBinder, config)
    }


    /*** From RecyclerItemView ***/


    /**
     * Create a [DelegatedRecyclerAdapter] to handle a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class, and you want to instantiate the View object yourself.
     * @param builder The function to instantiate your [ItemViewType] class
     * @param config Configuration for the adapter
     */
    fun <ItemType : Any, ItemViewType> fromRecyclerItemViewCreator(
        config: RecyclerAdapterConfig<ItemType>? = null,
        builder: Builder<ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        return SingleTypeAdapterBuilder.fromRecyclerItemViewBuilder(config, builder)
    }

    /**
     * Create a [SingleTypeRecyclerItemViewAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class.
     *
     * The type parameter 'ItemViewType' is the type of the View class, which will handle binding items.
     * This class is automatically instantiated using its View(Context) constructor.
     *
     * @param config Configuration for the adapter
     */
    inline fun <ItemType : Any, reified ItemViewType> fromRecyclerItemView(
        config: RecyclerAdapterConfig<ItemType>? = null
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        return SingleTypeAdapterBuilder.fromRecyclerItemViewType<ItemType, ItemViewType>(config)
    }

    /**
     * Create a [SingleTypeRecyclerItemViewAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class.
     *
     * @param itemViewClass The [ItemViewType] class which will handle binding items.
     * This class is automatically instantiated using its View(context: Context) constructor.
     * @param config Configuration for the adapter
     */
    @JvmStatic
    fun <ItemType : Any, ItemViewType> fromRecyclerItemViewClass(
        itemViewClass: KClass<ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>? = null
    ): DelegatedRecyclerAdapter<ItemType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {
        return SingleTypeAdapterBuilder.fromRecyclerItemViewClass(itemViewClass, config)
    }


    /*** From View ***/


    fun <ItemType : Any, ItemViewType : View> fromView(
        config: RecyclerAdapterConfig<ItemType>? = null,
        builder: Builder<ItemViewType>,
        binder: Binder<ItemType, ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType> =
        SingleTypeAdapterBuilder.fromBuilderAndBinder(config, builder, binder)

    inline fun <ItemType : Any, reified ItemViewType : View> fromViewBinder(
        config: RecyclerAdapterConfig<ItemType>? = null,
        noinline binder: Binder<ItemType, ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType> {
        return SingleTypeAdapterBuilder.fromViewType(config, binder)
    }

    inline fun <ItemType : Any, reified ItemViewType : View> fromStaticView(
        config: RecyclerAdapterConfig<ItemType>? = null
    ): DelegatedRecyclerAdapter<ItemType> {
        return SingleTypeAdapterBuilder.fromViewType<ItemType, ItemViewType>(
            config,
            createEmptyBinder()
        )
    }

    inline fun <ItemType : Any, reified ItemViewType : View> fromStaticViewClass(
        viewClass: KClass<ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>? = null
    ): DelegatedRecyclerAdapter<ItemType> {
        return SingleTypeAdapterBuilder.fromViewClass(config, viewClass, createEmptyBinder())
    }

    fun <ItemType : Any, ItemViewType : View> fromStaticViewCreator(
        config: RecyclerAdapterConfig<ItemType>? = null,
        builder: Builder<ItemViewType>
    ): DelegatedRecyclerAdapter<ItemType> {
        return SingleTypeAdapterBuilder.fromBuilderAndBinder(config, builder, createEmptyBinder())
    }
}
