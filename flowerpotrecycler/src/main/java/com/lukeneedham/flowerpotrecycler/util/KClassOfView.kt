package com.lukeneedham.flowerpotrecycler.util

import android.content.Context
import android.view.View
import kotlin.reflect.KClass

fun <ViewType : View> KClass<ViewType>.build(context: Context): ViewType =
    java.getConstructor(Context::class.java).newInstance(context)
