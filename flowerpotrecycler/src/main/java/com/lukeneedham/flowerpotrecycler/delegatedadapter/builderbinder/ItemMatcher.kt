package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

interface ItemMatcher<ItemType : Any> {
    fun isMatch(item: Any): Boolean
}
