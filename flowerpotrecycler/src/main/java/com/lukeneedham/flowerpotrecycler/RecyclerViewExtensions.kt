package com.lukeneedham.flowerpotrecycler

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.autoadapter.AutoGeneratedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerItemView

/**
 * Setup without writing an adapter.
 * To be used when supplying the [BuilderBinder] yourself.
 * Most of the time you may prefer to use a more specific 'setupWith' function.
 * @param items The data to be shown in the [RecyclerView]
 * @param builderBinder The [BuilderBinder] used to build the View for each item of the [RecyclerView], then bind the item to it
 */
fun <ItemType> RecyclerView.setupWithBuilderBinder(
    items: List<ItemType>,
    builderBinder: BuilderBinder<ItemType>
) {
    adapter = AutoGeneratedRecyclerAdapter(items, builderBinder)
}

/**
 * Setup without writing an adapter.
 * To be used when creating the item View programmatically
 *
 * Use [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl.onItem]
 * within your builder to update the UI when an item is bound.
 * @param items The data to be shown in the [RecyclerView]
 * @param builder The callback for building item Views
 */
fun <ItemType> RecyclerView.setupWithDeclarativeDsl(
    items: List<ItemType>,
    builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
) {
    adapter = RecyclerAdapterBuilder.fromDeclarativeDsl(items, builder)
}

/**
 * Setup without writing an adapter.
 * To be used when creating the item View from inflating an XML layout.
 * @param items The data to be shown in the [RecyclerView]
 * @param layoutResId The XML layout resource ID to inflate to build the View
 * @param binder The callback for binding each item to its View
 */
fun <ItemType> RecyclerView.setupWithXml(
    items: List<ItemType>,
    @LayoutRes layoutResId: Int,
    binder: (Int, ItemType, View) -> Unit
) {
    adapter = RecyclerAdapterBuilder.fromXml(items, layoutResId, binder)
}

/**
 * Setup without writing an adapter.
 * To be used when view logic is contained within its own class, and you wish to instantiate the View object yourself
 * @param items The data to be shown in the [RecyclerView]
 */
fun <ItemType, ItemViewType> RecyclerView.setupWithView(
    items: List<ItemType>,
    createView: (Context) -> ItemViewType
)
        where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> {
    adapter = RecyclerAdapterBuilder.fromView(items, createView)
}

/**
 * Setup without writing an adapter. To be used when view logic is contained within its own class.
 *
 * The type parameter 'ItemViewType' is the type of the view class, which will handle binding items.
 *
 * @param items The data to be shown in the [RecyclerView]
 */
inline fun <ItemType, reified ItemViewType> RecyclerView.setupWithView(
    items: List<ItemType>
) where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> {
    adapter = RecyclerAdapterBuilder.fromView<ItemType, ItemViewType>(items)
}

fun RecyclerView.scrollToCenter() {
    val adapter = adapter ?: return
    val centerPosition = adapter.itemCount / 2
    layoutManager?.scrollToPosition(centerPosition)
}