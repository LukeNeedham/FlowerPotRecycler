package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

interface ItemTypeMatcher<ItemType : Any> {
    fun isMatch(item: Any): Boolean
}
