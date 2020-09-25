package com.lukeneedham.flowerpotrecycler.util

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.util.extensions.createBuilder

object BuilderBinderUtils {
    inline fun <reified ViewType : View> createReflectiveBuilder(): Builder<ViewType> {
        return ViewType::class.createBuilder()
    }

    fun <ItemType, ViewType : View> createEmptyBinder(): Binder<ItemType, ViewType> {
        return { _, _, _ -> }
    }
}
