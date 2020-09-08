package com.lukeneedham.flowerpotrecyclersample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.xml.XmlLabmdaBuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.fragment_choose_layout.*

class ChooseLayoutTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.fragment_choose_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        optionsRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = RecyclerView.VERTICAL
        }

        val config = AdapterConfig<Any>().apply {
            items = listOf(HeaderItem) + ChoiceItem.values()
            addItemLayoutParams(RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                if (item !is ChoiceItem) {
                    return@addOnItemClickListener
                }
                val navigationActionId = when (item) {
                    ChoiceItem.ANKO -> R.id.action_chooseLayoutTypeFragment_to_ankoWithDatabindingLayoutFragment
                    ChoiceItem.XML -> R.id.action_chooseLayoutTypeFragment_to_xmlLayoutFragment
                    ChoiceItem.VIEW -> R.id.action_chooseLayoutTypeFragment_to_viewClassFragment
                    ChoiceItem.STATIC -> R.id.action_chooseLayoutTypeFragment_to_staticAdapterFragment
                    ChoiceItem.MULTI_ANY -> R.id.action_chooseLayoutTypeFragment_to_anyAdapterFragment
                    ChoiceItem.MULTI_NUMBER -> R.id.action_chooseLayoutTypeFragment_to_numberAdapterFragment
                }
                findNavController().navigate(navigationActionId)
            }
        }

        optionsRecyclerView.adapter = RecyclerAdapterBuilder.fromBuilderBinders(
            XmlLabmdaBuilderBinder.fromStaticClass<HeaderItem>(R.layout.view_header_item),
            RecyclerItemViewBuilderBinder.fromItemType<ChoiceItem, ChoiceItemView>(),
            config = config
        )
    }
}
