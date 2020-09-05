package com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.implementation

import com.lukeneedham.flowerpotrecycler.staticviewadapter.delegates.BaseStaticViewAdapterFeatureDelegate

class StaticViewOnClickDelegate(
    private val onClickListener: () -> Unit
) : BaseStaticViewAdapterFeatureDelegate() {
    override fun onClick() {
        onClickListener()
    }
}
