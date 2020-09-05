package com.lukeneedham.flowerpotrecycler.staticview.delegates.implementation

import com.lukeneedham.flowerpotrecycler.staticview.delegates.BaseStaticViewAdapterFeatureDelegate

class StaticViewOnClickDelegate(
    private val onClickListener: () -> Unit
) : BaseStaticViewAdapterFeatureDelegate() {
    override fun onClick() {
        onClickListener()
    }
}
