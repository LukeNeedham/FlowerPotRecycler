package com.lukeneedham.flowerpotrecycler.util.extensions

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Builder
import kotlin.reflect.KClass

fun <ViewType : View> KClass<ViewType>.build(context: Context): ViewType =
    java.getConstructor(Context::class.java).newInstance(context)

fun <ViewType : View> KClass<ViewType>.createBuilder(): Builder<ViewType> = {
    build(it.context)
}
