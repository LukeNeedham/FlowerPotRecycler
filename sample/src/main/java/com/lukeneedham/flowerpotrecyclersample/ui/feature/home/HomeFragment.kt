package com.lukeneedham.flowerpotrecyclersample.ui.feature.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.xml.XmlBuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = RecyclerView.VERTICAL
        }

        val config = AdapterConfig<Any, View>().apply {
            items = listOf(HeaderItem) + ChoiceItem.values()
            addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                if (item !is ChoiceItem) {
                    return@addOnItemClickListener
                }
                val navigationActionId = when (item) {
                    ChoiceItem.MULTI_ANY -> R.id.action_chooseLayoutTypeFragment_to_anyAdapterFragment
                    ChoiceItem.MULTI_NUMBER -> R.id.action_chooseLayoutTypeFragment_to_numberAdapterFragment
                    ChoiceItem.RECYCLER_ITEM_VIEW -> R.id.action_chooseLayoutTypeFragment_to_recyclerItemViewAdapterFragment
                    ChoiceItem.VIEW -> R.id.action_chooseLayoutTypeFragment_to_viewAdapterFragment
                    ChoiceItem.XML -> R.id.action_chooseLayoutTypeFragment_to_xmlAdapterFragment
                    ChoiceItem.ANKO -> R.id.action_chooseLayoutTypeFragment_to_ankoFragment
                    ChoiceItem.STATIC -> R.id.action_chooseLayoutTypeFragment_to_staticViewAdapterFragment
                    ChoiceItem.CUSTOM_ADAPTER -> R.id.action_chooseLayoutTypeFragment_to_customAdapterFragment
                }
                findNavController().navigate(navigationActionId)
            }
        }

        optionsRecyclerView.adapter = RecyclerAdapterBuilder.fromBuilderBinders(
            XmlBuilderBinder.create<HeaderItem>(R.layout.view_header_item),
            RecyclerItemViewBuilderBinder.create<ChoiceItem, ChoiceItemView>(),
            config = config
        )
    }
}
