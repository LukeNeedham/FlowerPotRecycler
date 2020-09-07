package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.TypedViewHolder

class SingleTypedViewHolder<ItemType, ItemViewType>(view: ItemViewType) :
    TypedViewHolder<ItemViewType>(view)
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType>
