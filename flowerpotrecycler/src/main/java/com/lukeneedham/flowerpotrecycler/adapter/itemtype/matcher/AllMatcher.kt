package com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher

/** Matches everything. Use with care! */
class AllMatcher : ItemMatcher {
    override fun isMatch(item: Any) = true
}
