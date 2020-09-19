package com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder

import android.view.ViewGroup

typealias Builder<ItemViewType> = (parent: ViewGroup) -> ItemViewType

typealias Binder<ItemType, ItemViewType> =
            (itemView: ItemViewType, position: Int, item: ItemType) -> Unit

