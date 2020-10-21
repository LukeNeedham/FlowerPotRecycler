package com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher

/** Determines whether an item matches some condition */
interface ItemMatcher {
    fun isMatch(item: Any): Boolean
}
