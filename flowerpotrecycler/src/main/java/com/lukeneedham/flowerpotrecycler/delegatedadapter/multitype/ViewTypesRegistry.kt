package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View
import kotlin.reflect.KClass

class ViewTypesRegistryImpl(itemTypeToViews: List<IItemToViewType<*, *>>) :
    ViewTypesRegistry<Any, View>(itemTypeToViews)

abstract class ViewTypesRegistry<BaseType : Any, BaseViewType : View>(
    private val itemTypeToViews: List<IItemToViewType<*, *>>
) {
    private val typeIdToTypeView = itemTypeToViews.map { it.getTypeId() to it }.toMap()

    /** Caches look-ups for item classes */
    private val typeViewClassCache: MutableMap<KClass<*>, IItemToViewType<BaseType, BaseViewType>> =
        mutableMapOf()

    fun createView(context: Context, itemTypeId: Int): View {
        val typeToView = typeIdToTypeView.getValue(itemTypeId)
        return typeToView.createView(context)
    }

    fun bind(holder: TypedViewHolder<BaseViewType>, position: Int, item: BaseType) {
        val typeToView = getTypeView(item)
        typeToView.bind(holder, position, item)
    }

    fun getTypeId(item: BaseType): Int {
        val typeToView = getTypeView(item)
        return typeToView.getTypeId()
    }

    /**
     * Gets the [IItemToViewType] based on the type of [item].
     * This searches first for exact class matches,
     * then for matches of the superclass,
     * then matches of the superclass' superclass, etc.
     * So the match with the closest class relation will be returned.
     */
    private fun getTypeView(item: BaseType): IItemToViewType<BaseType, BaseViewType> {
        val itemClass: KClass<*> = item::class

        val cachedValue = typeViewClassCache[itemClass]
        if (cachedValue != null) {
            return cachedValue
        }

        val match = findTypeView(itemClass)
            ?: error("No matching type view for item: $item")
        typeViewClassCache[itemClass] = match
        return match
    }

    /**
     * @return the [IItemToViewType] for item, based on matching its exact class.
     * Returns null when there is no exact match.
     */
    private fun getExactTypeView(typeClass: KClass<*>): IItemToViewType<BaseType, BaseViewType>? {
        return itemTypeToViews.firstOrNull { it.itemTypeClass == typeClass }
    }

    private fun findTypeView(
        originalTypeClass: KClass<out Any>
    ): IItemToViewType<BaseType, BaseViewType>? {
        var currentClass = originalTypeClass
        while (true) {
            val match = getExactTypeView(currentClass)
            if (match != null) {
                return match
            }
            currentClass = currentClass.getSuperClass() ?: return null
        }
    }

    companion object {
        private fun IItemToViewType<*, *>.getTypeId(): Int = itemTypeClass.getTypeId()

        private fun KClass<*>.getTypeId(): Int = hashCode()

        /** @return null when the class has no superclass */
        private fun KClass<*>.getSuperClass(): KClass<*>? {
            val superClass: Class<*>? = java.superclass
            return superClass?.kotlin
        }
    }
}
