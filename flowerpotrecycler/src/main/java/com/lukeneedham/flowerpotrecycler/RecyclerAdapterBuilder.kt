package com.lukeneedham.flowerpotrecycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.AutoViewRecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.AutoRecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.xml.XMLBuilderBinderLabmda
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.declarative.DeclarativeBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ConfigurableRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.ConfigurableSingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.SingleTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.config.StaticViewRecyclerAdapterConfig
import kotlin.reflect.KClass

@Suppress("unused")
object RecyclerAdapterBuilder {

    /**
     * Auto-generate an adapter.
     * To be used when creating the item View programmatically (for example, with Anko DSL Layouts)
     *
     * Use [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl.onItem]
     * within your builder to update the UI when an item is bound.
     * @param builder The callback for building item Views
     * @param config Optional configuration for the adapter
     */
    inline fun <reified ItemType : Any> fromDeclarativeDsl(
        config: RecyclerAdapterConfig<ItemType>? = null,
        noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
    ): SingleTypeRecyclerAdapter<ItemType, AutoRecyclerItemView<ItemType>> {
        val builderBinder = DeclarativeBuilderBinder.fromType(builder)
        return AutoViewRecyclerAdapterBuilder.build(builderBinder, config)
    }

    /**
     * Auto-generate an adapter.
     * To be used when creating the item View from inflating an XML layout.
     * @param layoutResId The XML layout resource ID to inflate to build the View
     * @param binder The callback for binding each item to its View
     * @param config Optional configuration for the adapter
     */
    inline fun <reified ItemType : Any> fromXml(
        @LayoutRes layoutResId: Int,
        config: RecyclerAdapterConfig<ItemType>? = null,
        noinline binder: (itemView: View, position: Int, item: ItemType) -> Unit
    ): SingleTypeRecyclerAdapter<ItemType, AutoRecyclerItemView<ItemType>> {
        val builderBinder =
            XMLBuilderBinderLabmda.fromType(layoutResId) { itemView: View, position: Int, item: ItemType ->
                binder(itemView, position, item)
            }
        return AutoViewRecyclerAdapterBuilder.build(builderBinder, config)
    }

    /* From Multi Type */

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
        ConfigurableRecyclerAdapter(
            builderBinderRegistry,
            config
        )

    /**
     * Create a [DelegatedRecyclerAdapter] which can handle multiple item types.
     *
     * @param builderBinders a list of [ItemBuilderBinder]s,
     * which each handle creating the view and binding items for a single item type
     * @param config Configuration for the adapter
     */
    fun <BaseItemType : Any> fromBuilderBinders(
        vararg builderBinders: ItemBuilderBinder<out BaseItemType, *>,
        config: RecyclerAdapterConfig<BaseItemType>? = null
    ): DelegatedRecyclerAdapter<BaseItemType> =
        ConfigurableRecyclerAdapter(
            BuilderBinderRegistry(listOf(*builderBinders)),
            config
        )

    /* From Single Type */

    /**
     * Create a [SingleTypeRecyclerAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class, and you want to instantiate the View object yourself.
     * @param createView The function to instantiate your [ItemViewType] class
     * @param config Configuration for the adapter
     */
    inline fun <reified ItemType : Any, reified ItemViewType> fromViewCreator(
        config: RecyclerAdapterConfig<ItemType>? = null,
        noinline createView: (Context) -> ItemViewType
    ): SingleTypeRecyclerAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        ConfigurableSingleTypeRecyclerAdapter.fromType(config, createView)

    /**
     * Create a [SingleTypeRecyclerAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class.
     *
     * The type parameter 'ItemViewType' is the type of the View class, which will handle binding items.
     * This class is automatically instantiated using its View(Context) constructor.
     *
     * @param config Configuration for the adapter
     */
    inline fun <reified ItemType : Any, reified ItemViewType> fromView(
        config: RecyclerAdapterConfig<ItemType>? = null
    ): SingleTypeRecyclerAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        fromViewClass(ItemType::class, ItemViewType::class, config)

    /**
     * Create a [SingleTypeRecyclerAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class.
     *
     * @param itemViewClass The [ItemViewType] class which will handle binding items.
     * This class is automatically instantiated using its View(context: Context) constructor.
     * @param config Configuration for the adapter
     */
    @JvmStatic
    fun <ItemType : Any, ItemViewType> fromViewClass(
        itemTypeClass: KClass<ItemType>,
        itemViewClass: KClass<ItemViewType>,
        config: RecyclerAdapterConfig<ItemType>? = null
    ): SingleTypeRecyclerAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        ConfigurableSingleTypeRecyclerAdapter(itemTypeClass, config) {
            itemViewClass.java.getConstructor(Context::class.java).newInstance(it)
        }

    /* From Static View */

    /**
     * A simple adapter wrapper around a static view.
     * Since the view is static, no binding is required, and no items need to be provided.
     * The adapter will always show the view a single time.
     *
     * To be used when View logic is contained within its own class, and you want to instantiate the View object yourself.
     * @param config Configuration for the adapter
     * @param createView The function to instantiate your [ViewType] class
     */
    fun <ViewType : View> fromStaticViewCreator(
        config: StaticViewRecyclerAdapterConfig? = null,
        createView: (Context) -> ViewType
    ): StaticViewRecyclerAdapter =
        StaticViewRecyclerAdapter(config, createView)

    /**
     * A simple adapter wrapper around a static view.
     * Since the view is static, no binding is required, and no items need to be provided.
     * The adapter will always show the view a single time.
     *
     * To be used when View logic is contained within its own class.
     * The type parameter [ViewType] is the type of the View class.
     * This class is automatically instantiated using its View(Context) constructor.
     * @param config Configuration for the adapter
     */
    inline fun <reified ViewType : View> fromStaticView(
        config: StaticViewRecyclerAdapterConfig? = null
    ): StaticViewRecyclerAdapter =
        fromStaticViewClass(ViewType::class.java, config)

    /**
     * A simple adapter wrapper around a static view.
     * Since the view is static, no binding is required, and no items need to be provided.
     * The adapter will always show the view a single time.
     *
     * To be used when View logic is contained within its own class.
     * @param viewClass The [ViewType] class which will handle binding items.
     * This class is automatically instantiated using its View(context: Context) constructor.
     * @param config Configuration for the adapter
     */
    @JvmStatic
    fun <ViewType : View> fromStaticViewClass(
        viewClass: Class<ViewType>,
        config: StaticViewRecyclerAdapterConfig? = null
    ): StaticViewRecyclerAdapter =
        StaticViewRecyclerAdapter(config) {
            viewClass.getConstructor(Context::class.java).newInstance(it)
        }
}
