package com.lukeneedham.flowerpotrecycler

import android.view.ViewGroup

typealias Builder<ItemViewType> = (parent: ViewGroup) -> ItemViewType

typealias Binder<ItemType, ItemViewType> =
            (itemView: ItemViewType, position: Int, item: ItemType) -> Unit

