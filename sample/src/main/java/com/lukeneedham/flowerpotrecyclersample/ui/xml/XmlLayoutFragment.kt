package com.lukeneedham.flowerpotrecyclersample.ui.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.SingleTypeRecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.AdapterConfig
import com.lukeneedham.flowerpotrecycler.util.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.util.addOnItemClickListener
import com.lukeneedham.flowerpotrecyclersample.R
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotDatabase
import com.lukeneedham.flowerpotrecyclersample.domain.FlowerPotModel
import kotlinx.android.synthetic.main.fragment_xml_layout.*
import kotlinx.android.synthetic.main.view_flower_pot_item.view.*

class XmlLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = AdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val recyclerAdapter = SingleTypeRecyclerAdapterBuilder.fromXml(
            R.layout.view_flower_pot_item,
            config
        ) { itemView, position, item ->
            itemView.potImageView.setImageResource(item.imageResId)
            itemView.potNameTextView.setText(item.nameResId)
        }

        // Alternatively, we can create a static adapter if we don't need any binding to be done
        val staticAdapter =
            SingleTypeRecyclerAdapterBuilder.fromStaticXml(R.layout.view_flower_pot_item, config)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}
