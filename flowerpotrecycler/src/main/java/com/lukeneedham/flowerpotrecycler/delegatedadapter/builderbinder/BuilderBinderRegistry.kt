package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ViewHolder

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
        assertNoDuplicateBuilderBuilderType()
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

    /**
     * @return the item types in [items] which do not have a registered BuilderBinder,
     * and therefore cannot be handled
     */
    fun findUnhandledItems(items: List<BaseItemType>): List<BaseItemType> =
        items.filter { getBuilderBinderForItem(it) == null }.distinct()

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

    /** Throws an exception if there are multiple BuilderBinders registered for the same item type */
    private fun assertNoDuplicateBuilderBuilderType() {
        val duplicateBuilderBinderTypes = builderBinders.groupingBy { it.itemMatcher }
            .eachCount()
            .filter { it.value > 1 }
            .map { it.key }

        if (duplicateBuilderBinderTypes.isNotEmpty()) {
            throw FlowerPotRecyclerException(
                "There are multiple BuilderBinders registered for item types: $duplicateBuilderBinderTypes"
            )
        }
    }

    companion object {
        fun <BaseItemType : Any> from(
            vararg builderBinders: BuilderBinder<out BaseItemType, *>
        ): BuilderBinderRegistry<BaseItemType> {
            return BuilderBinderRegistry(listOf(*builderBinders))
        }
    }
}
