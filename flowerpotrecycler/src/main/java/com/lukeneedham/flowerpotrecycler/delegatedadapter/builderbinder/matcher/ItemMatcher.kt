package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher

interface ItemMatcher<ItemType : Any> {
    fun isMatch(item: Any): Boolean
}
