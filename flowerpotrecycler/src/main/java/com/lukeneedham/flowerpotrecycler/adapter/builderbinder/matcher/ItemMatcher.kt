package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher

interface ItemMatcher<ItemType : Any> {
    fun isMatch(item: Any): Boolean
}
