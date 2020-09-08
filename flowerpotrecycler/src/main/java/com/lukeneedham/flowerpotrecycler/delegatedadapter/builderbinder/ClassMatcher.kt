package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import com.lukeneedham.flowerpotrecycler.util.TAG
import kotlin.reflect.KClass

data class ClassMatcher<ItemType : Any>(val itemClass: KClass<ItemType>) : ItemTypeMatcher<ItemType> {
    override fun isMatch(item: Any): Boolean {
        return itemClass == item::class
    }

    override fun toString(): String {
        return "$TAG for ${itemClass.qualifiedName}"
    }
}
