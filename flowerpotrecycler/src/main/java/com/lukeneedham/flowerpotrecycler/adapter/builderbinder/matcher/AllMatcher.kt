package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher

import com.lukeneedham.flowerpotrecycler.util.extensions.TAG

/**
 * This matcher matches all items.
 * This is potentially dangerous -
 * it's only real use-case is in adapters with a single item type
 */
class AllMatcher<ItemType : Any> :
    ItemMatcher<ItemType> {
    override fun isMatch(item: Any) = true

    override fun toString() = TAG
}
