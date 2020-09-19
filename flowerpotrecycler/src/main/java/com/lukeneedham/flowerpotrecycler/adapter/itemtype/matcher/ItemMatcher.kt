package com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher

/** Determines whether an item matches some condition */
interface ItemMatcher<ItemType : Any> {
    fun isMatch(item: Any): Boolean
}
