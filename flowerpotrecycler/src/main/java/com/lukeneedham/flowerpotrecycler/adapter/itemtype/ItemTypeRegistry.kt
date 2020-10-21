package com.lukeneedham.flowerpotrecycler.adapter.itemtype

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.config.ItemTypeConfigRegistry

/**
 * A registry of [ItemTypeDelegate]s, which each handle a single item type.
 * No one item type may be handled by multiple [BuilderBinder]s,
 * and each item type submitted to the adapter must have a registered [BuilderBinder].
 * If either of these conditions are violated, this class will throw an exception to let you know.
 *
 * [BaseItemType] provides an upper bound on the item types registered.
 * [BaseItemViewType] provides an upper bound on the view types registered.
 */
@Suppress("MemberVisibilityCanBePrivate")
class ItemTypeRegistry<BaseItemType, BaseItemViewType : View>(
    val itemTypeDelegates: List<ItemTypeDelegate<out BaseItemType, out BaseItemViewType>>
) {

    init {
        if (itemTypeDelegates.isEmpty()) {
            throw FlowerPotRecyclerException(
                "No BuilderBinders have been registered, rendering this registry useless."
            )
        }
    }

    fun build(parent: ViewGroup, itemTypeId: Int): ViewHolder<BaseItemViewType> {
        val builderBinderWithFeatures = requireBuilderBinderForTypeId(itemTypeId)
        val view = builderBinderWithFeatures.build(parent)
        val viewHolder = ViewHolder(view)
        builderBinderWithFeatures.onBuilt(parent, itemTypeId, viewHolder)
        return viewHolder
    }

    fun bind(holder: ViewHolder<BaseItemViewType>, position: Int, item: BaseItemType) {
        val builderBinderWithFeatures = requireBuilderBinderForItem(item)
        builderBinderWithFeatures.bindUntyped(holder, position, item)
    }

    /** Type id is simply the index of the BuilderBinder in [itemTypeDelegates] responsible for the item */
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

    private fun getBuilderBinderForTypeId(typeId: Int): ItemTypeDelegate<out BaseItemType, out BaseItemViewType>? =
        itemTypeDelegates.getOrNull(typeId)

    private fun requireBuilderBinderForTypeId(typeId: Int): ItemTypeDelegate<out BaseItemType, out BaseItemViewType> =
        getBuilderBinderForTypeId(typeId)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for type id: $typeId")

    private fun getTypeIdForItem(item: BaseItemType): Int? {
        val index = itemTypeDelegates.indexOfLast { it.matchesItem(item) }
        if (index == -1) {
            return null
        }
        return index
    }

    private fun requireTypeIdForItem(item: BaseItemType): Int =
        getTypeIdForItem(item)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for item: $item")

    private fun getBuilderBinderForItem(item: BaseItemType): ItemTypeDelegate<out BaseItemType, out BaseItemViewType>? =
        itemTypeDelegates.firstOrNull { it.matchesItem(item) }

    private fun requireBuilderBinderForItem(item: BaseItemType): ItemTypeDelegate<out BaseItemType, out BaseItemViewType> =
        getBuilderBinderForItem(item)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for item: $item")

    /**
     * @return all the items in [items] which can be handled by multiple registered BuilderBinder,
     * and therefore create ambiguity
     */
    private fun findDuplicateHandledItems(items: List<BaseItemType>): List<BaseItemType> {
        val duplicateMatchedItems = mutableListOf<BaseItemType>()
        items.forEach { item ->
            val builderBindersForItem = itemTypeDelegates.filter { it.matchesItem(item) }
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
        fun <BaseItemType, BaseItemViewType : View> newInstance(
            itemTypeConfigRegistry: ItemTypeConfigRegistry<out BaseItemType, out BaseItemViewType>,
            adapter: RecyclerView.Adapter<*>
        ): ItemTypeRegistry<BaseItemType, BaseItemViewType> {
            val itemTypeDelegates = itemTypeConfigRegistry.getItemTypeConfigs().map {
                it.createDelegate(adapter)
            }
            return ItemTypeRegistry(itemTypeDelegates)
        }
    }
}
