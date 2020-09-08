package com.lukeneedham.flowerpotrecycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.AutoRecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.AutoRecyclerItemViewAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.AllMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.declarative.DeclarativeBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.xml.StaticXmlBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.xml.XmlLabmdaBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.RecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.ConfigurableSingleTypeAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.SingleTypeAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview.ConfigurableSingleTypeRecyclerItemViewAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview.SingleTypeRecyclerItemViewAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.StaticViewRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.staticviewadapter.config.StaticViewRecyclerAdapterConfig
import com.lukeneedham.flowerpotrecycler.util.build
import kotlin.reflect.KClass

object SingleTypeRecyclerAdapterBuilder {

    /* From DSL */

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
    ): SingleTypeRecyclerItemViewAdapter<ItemType, AutoRecyclerItemView<ItemType>> {
        val builderBinder = DeclarativeBuilderBinder.fromType(builder)
        return AutoRecyclerItemViewAdapterBuilder.build(builderBinder, config)
    }

    /* From XML */

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
    ): SingleTypeRecyclerItemViewAdapter<ItemType, AutoRecyclerItemView<ItemType>> {
        val builderBinder =
            XmlLabmdaBuilderBinder(layoutResId, AllMatcher()) { itemView: View, position: Int, item: ItemType ->
                binder(itemView, position, item)
            }
        return AutoRecyclerItemViewAdapterBuilder.build(builderBinder, config)
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
    ): SingleTypeRecyclerItemViewAdapter<ItemType, AutoRecyclerItemView<ItemType>> {
        val builderBinder = StaticXmlBuilderBinder<ItemType>(layoutResId, AllMatcher())
        return AutoRecyclerItemViewAdapterBuilder.build(builderBinder, config)
    }


    /* RecyclerItemView */

    /**
     * Create a [SingleTypeRecyclerItemViewAdapter], which handles a single type of item ([ItemType]) with a single view ([ItemViewType]).
     *
     * To be used when View logic is contained within its own class, and you want to instantiate the View object yourself.
     * @param createView The function to instantiate your [ItemViewType] class
     * @param config Configuration for the adapter
     */
    fun <ItemType : Any, ItemViewType> fromRecyclerItemViewCreator(
        config: RecyclerAdapterConfig<ItemType>? = null,
        createView: Builder<ItemViewType>
    ): SingleTypeRecyclerItemViewAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        ConfigurableSingleTypeRecyclerItemViewAdapter(
            config,
            createView
        )

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
    ): SingleTypeRecyclerItemViewAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        fromRecyclerItemViewClass(ItemViewType::class, config)

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
    ): SingleTypeRecyclerItemViewAdapter<ItemType, ItemViewType>
            where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
        ConfigurableSingleTypeRecyclerItemViewAdapter(config) {
            itemViewClass.build(it.context)
        }

    /* View */

    inline fun <reified ItemType : Any, reified ItemViewType : View> fromView(
        config: RecyclerAdapterConfig<ItemType>? = null,
        noinline builder: Builder<ItemViewType>,
        noinline binder: Binder<ItemType, ItemViewType>
    ): SingleTypeAdapter<ItemType, ItemViewType> =
        ConfigurableSingleTypeAdapter(config, builder, binder)

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
        createView: Builder<ViewType>
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
