package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher

import com.lukeneedham.flowerpotrecycler.util.extensions.TAG
import kotlin.reflect.KClass

/** An [ItemMatcher] which matches items based on their class */
data class ClassMatcher<ItemType : Any>(val itemClass: KClass<ItemType>) :
    ItemMatcher<ItemType> {
    override fun isMatch(item: Any): Boolean {
        return itemClass == item::class
    }

    override fun toString(): String {
        return "$TAG for ${itemClass.qualifiedName}"
    }
}
