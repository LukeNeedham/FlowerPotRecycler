package com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher

import com.lukeneedham.flowerpotrecycler.util.extensions.TAG
import kotlin.reflect.KClass

/** An [ItemMatcher] which matches items based on their class. Also matches null. */
data class NullableClassMatcher(val itemClass: KClass<*>) : ItemMatcher {
    override fun isMatch(item: Any?): Boolean {
        return if (item == null) true else itemClass == item::class
    }

    override fun toString(): String {
        return "$TAG for ${itemClass.qualifiedName}"
    }
}
