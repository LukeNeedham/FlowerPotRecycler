package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.delegatedadapter.ViewHolder
import kotlin.reflect.KClass

/**
 * A registry of [ItemBuilderBinder]s, which each handle a single item type.
 * No one item type may be handled by multiple [ItemBuilderBinder]s,
 * and each item type submitted to the adapter must have a registered [ItemBuilderBinder].
 * If either of these conditions are violated, this class will throw an exception to let you know.
 *
 * [BaseItemType] provides an upper bound on the types registered
 */
class BuilderBinderRegistry<BaseItemType : Any>(
    val builderBinders: List<ItemBuilderBinder<out BaseItemType, *>>
) {
    private val typeIdToBuilderBinder = builderBinders.map { it.getTypeId() to it }.toMap()

    /** Caches look-ups */
    private val typeToBuilderBinderCache: MutableMap<KClass<*>, ItemBuilderBinder<out BaseItemType, *>> =
        mutableMapOf()

    init {
        if (builderBinders.isEmpty()) {
            throw FlowerPotRecyclerException(
                "No BuilderBinders have been registered, rendering this registry useless."
            )
        }
        assertNoDuplicateBuilderBuilderType()
    }

    fun createView(parent: ViewGroup, itemTypeId: Int): View {
        val typeToView = typeIdToBuilderBinder.getValue(itemTypeId)
        return typeToView.createView(parent)
    }

    fun bind(holder: ViewHolder, position: Int, item: BaseItemType) {
        val typeToView = requireBuilderBinder(item)
        val view = holder.itemView
        typeToView.bindUntyped(view, position, item)
    }

    fun getTypeId(item: BaseItemType): Int {
        val typeToView = requireBuilderBinder(item)
        return typeToView.getTypeId()
    }

    /**
     * @return the item types in [items] which do not have a registered BuilderBinder,
     * and therefore cannot be handled
     */
    fun findUnhandledItems(items: List<BaseItemType>): List<KClass<out BaseItemType>> =
        items.filter { getBuilderBinder(it) == null }
            .map { it::class }
            .distinct()

    private fun requireBuilderBinder(item: BaseItemType): ItemBuilderBinder<out BaseItemType, *> =
        getBuilderBinder(item)
            ?: throw FlowerPotRecyclerException("No BuilderBinder registered for item: $item")

    /**
     * Gets the [ItemBuilderBinder] based on the type of [item].
     * This searches for exact class matches,
     */
    private fun getBuilderBinder(item: BaseItemType): ItemBuilderBinder<out BaseItemType, *>? {
        val itemClass: KClass<*> = item::class

        val cachedValue = typeToBuilderBinderCache[itemClass]
        if (cachedValue != null) {
            return cachedValue
        }

        val builderBinder = findBuilderBinder(itemClass) ?: return null
        typeToBuilderBinderCache[itemClass] = builderBinder
        return builderBinder
    }

    /**
     * @return the [ItemBuilderBinder] for item, based on matching its exact class.
     * Returns null when there is no match.
     * Prefer to use [getBuilderBinder], which takes advantage of a cache
     */
    private fun findBuilderBinder(typeClass: KClass<*>): ItemBuilderBinder<out BaseItemType, *>? {
        return builderBinders.firstOrNull { it.itemTypeClass == typeClass }
    }

    /** Throws an exception if there are multiple BuilderBinders registered for the same item type */
    private fun assertNoDuplicateBuilderBuilderType() {
        val duplicateBuilderBinderTypes = builderBinders.groupingBy { it.itemTypeClass }
            .eachCount()
            .filter { it.value > 1 }
            .map { it.key }

        if (duplicateBuilderBinderTypes.isNotEmpty()) {
            val duplicateBuilderBinderTypeNames =
                duplicateBuilderBinderTypes.map { it.qualifiedName }
            throw FlowerPotRecyclerException(
                "There are multiple BuilderBinders registered for item types: $duplicateBuilderBinderTypeNames"
            )
        }

    }

    companion object {
        private fun ItemBuilderBinder<*, *>.getTypeId(): Int = itemTypeClass.getTypeId()
        private fun KClass<*>.getTypeId(): Int = hashCode()
    }
}
