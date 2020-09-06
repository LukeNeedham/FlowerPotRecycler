package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.TypedViewHolder

class TypedRecyclerViewHolder<ItemType, ItemViewType>(view: ItemViewType) :
    TypedViewHolder<ItemViewType>(view)
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType>
