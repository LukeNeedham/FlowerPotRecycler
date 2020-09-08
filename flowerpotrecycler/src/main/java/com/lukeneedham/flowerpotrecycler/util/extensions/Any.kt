package com.lukeneedham.flowerpotrecycler.util.extensions

internal val Any.TAG: String
    get() = this.javaClass.simpleName
