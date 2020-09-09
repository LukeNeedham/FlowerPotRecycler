package com.lukeneedham.flowerpotrecycler.adapter.builderbinder

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder

/**
 * A registry of [BuilderBinder]s, which each handle a single item type.
 * No one item type may be handled by multiple [BuilderBinder]s,
 * and each item type submitted to the adapter must have a registered [BuilderBinder].
 * If either of these conditions are violated, this class will throw an exception to let you know.
 *
 * [BaseItemType] provides an upper bound on the types registered
 */
class BuilderBinderRegistry<BaseItemType : Any>(
    val builderBinders: List<BuilderBinder<out BaseItemType, *>>
) {

    init {
        if (builderBinders.isEmpty()) {
            throw FlowerPotRecyclerException(
                "No BuilderBinders have been registered, rendering this registry useless."
            )
        }
    }

    fun build(parent: ViewGroup, itemTypeId: Int): View {
        val builderBinder = requireBuilderBinderForTypeId(itemTypeId)
        return builderBinder.build(parent)
    }

    fun bind(holder: ViewHolder, position: Int, item: BaseItemType) {
        val typeToView = requireBuilderBinderForItem(item)
        val view = holder.itemView
        typeToView.bindUntyped(view, position, item)
    }

    /** Type id is simply the index of the BuilderBinder in [builderBinders] responsible for the item */
    fun getTypeId(item: BaseItemType): Int {
        return requireTypeIdForItem(item)
    }

    fun assertItemsHandled(items: List<BaseItemType>) {
        val duplicateHandledItems = findDuplicateHandledItems(items)
        if (duplicateHandledItems.isNotEmpty()) {
            throw FlowerPotRecyclerException(
                "There are multiple BuilderBinders registered for items: $duplicateHandledItems. " +
                        "Please ensure each item is matched by exactly 1 BuilderBinder"
            )
        }

        val unhandledItems = findUnhandledItems(items)
        if (unhandledItems.isNotEmpty()) {
            throw FlowerPotRecyclerException(
                "There is no BuilderBinder registered for items: $unhandledItems. " +
                        "Please ensure each item is matched by exactly 1 BuilderBinder"
            )
        }
    }

    private fun getBuilderBinderForTypeId(typeId: Int): BuilderBinder<out BaseItemType, *>? =
        builderBinders.getOrNull(typeId)

    private fun requireBuilderBinderForTypeId(typeId: Int): BuilderBinder<out BaseItemType, *> =
        getBuilderBinderForTypeId(typeId)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for type id: $typeId")

    private fun getTypeIdForItem(item: BaseItemType): Int? {
        val index = builderBinders.indexOfLast { it.matchesItem(item) }
        if (index == -1) {
            return null
        }
        return index
    }

    private fun requireTypeIdForItem(item: BaseItemType): Int =
        getTypeIdForItem(item)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for item: $item")

    private fun getBuilderBinderForItem(item: BaseItemType): BuilderBinder<out BaseItemType, *>? =
        builderBinders.firstOrNull { it.matchesItem(item) }

    private fun requireBuilderBinderForItem(item: BaseItemType): BuilderBinder<out BaseItemType, *> =
        getBuilderBinderForItem(item)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for item: $item")

    /**
     * @return all the items in [items] which can be handled by multiple registered BuilderBinder,
     * and therefore create ambiguity
     */
    private fun findDuplicateHandledItems(items: List<BaseItemType>): List<BaseItemType> {
        val duplicateMatchedItems = mutableListOf<BaseItemType>()
        items.forEach { item ->
            val builderBindersForItem = builderBinders.filter { it.matchesItem(item) }
            if (builderBindersForItem.size > 1) {
                duplicateMatchedItems.add(item)
            }
        }
        return duplicateMatchedItems.distinct()
    }

    /**
     * @return all the items in [items] which do not have a registered BuilderBinder,
     * and therefore cannot be handled
     */
    private fun findUnhandledItems(items: List<BaseItemType>): List<BaseItemType> =
        items.filter { getBuilderBinderForItem(it) == null }.distinct()

    companion object {
        fun <BaseItemType : Any> from(
            vararg builderBinders: BuilderBinder<out BaseItemType, *>
        ): BuilderBinderRegistry<BaseItemType> {
            return BuilderBinderRegistry(listOf(*builderBinders))
        }
    }
}
