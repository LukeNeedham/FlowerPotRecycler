package com.lukeneedham.flowerpotrecyclersample.ui.view.staticview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.fragment_xml_layout.*

class StaticAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Build a recyclerview for a static view, which doesn't require binding

        // Requires a different config for static views
        val staticConfig = AdapterConfig<Any>().apply {
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { _, _ ->
                Toast.makeText(requireContext(), "Static view clicked", Toast.LENGTH_SHORT).show()
            }
        }
        val staticViewAdapter =
            RecyclerAdapterBuilder.fromStaticView<Any, ExampleStaticView>(staticConfig)

        // Alternative function to specify view creation function explicitly
        val staticViewAdapterAlternative =
            RecyclerAdapterBuilder.fromStaticViewCreator<Any, ExampleStaticView> {
                ExampleStaticView(it.context)
            }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            // On its own, this is clearly quite useless
            // However, this adapter can be combined with other adapters, for example with ConcatAdapter
            adapter = staticViewAdapter
        }
    }
}
