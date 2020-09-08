package com.lukeneedham.flowerpotrecyclersample.ui.view.multitype

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
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.RecyclerItemViewBuilderBinder
import com.lukeneedham.flowerpotrecycler.util.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.fragment_xml_layout.*

class NumberAdapterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The config is of the shared super-type
        // We want to show ints and doubles, so the shared-super class is Number
        val numberAdapterConfig = AdapterConfig<Number>().apply {
            items = listOf(1, 2, 3.0)
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        // This BuilderBinder says: when an item of type Int is encountered,
        // delegate its building and binding to IntItemView
        val intBuilderBinder =
            RecyclerItemViewBuilderBinder.fromType<Int, IntItemView>()
        val doubleBuilderBinder =
            RecyclerItemViewBuilderBinder.fromType<Double, DoubleItemView>()

        // Multi-type adapter from type registry
        // Config optional
        val numberRegistry =
            BuilderBinderRegistry<Number>(
                listOf(intBuilderBinder, doubleBuilderBinder)
            )
        val numberAdapter =
            RecyclerAdapterBuilder.fromBuilderBinderRegistry(numberRegistry, numberAdapterConfig)

        // Alternatively, a function using varargs
        // Config optional
        val numberAdapterAlternative = RecyclerAdapterBuilder.fromBuilderBinders(
            intBuilderBinder,
            doubleBuilderBinder,
            config = numberAdapterConfig
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = numberAdapter
        }
    }
}
