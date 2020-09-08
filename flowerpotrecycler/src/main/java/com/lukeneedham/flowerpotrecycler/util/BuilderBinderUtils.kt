package com.lukeneedham.flowerpotrecycler.util

import android.view.View
import com.lukeneedham.flowerpotrecycler.Binder
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.util.extensions.createBuilder

object BuilderBinderUtils {
    inline fun <reified ViewType : View> createBuilder(): Builder<ViewType> {
        return ViewType::class.createBuilder()
    }

    fun <ItemType : Any, ViewType : View> createEmptyBinder(): Binder<ItemType, ViewType> {
        return { _, _, _ -> }
    }
}
