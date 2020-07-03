package com.lukeneedham.flowerpotrecyclersample

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.RecyclerAdapterBuilder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.config.LinearAdapterConfig
import com.lukeneedham.flowerpotrecycler.extensions.addItemLayoutParams
import com.lukeneedham.flowerpotrecycler.extensions.addOnItemClickListener
import com.lukeneedham.flowerpotrecycler.extensions.setCyclic
import kotlinx.android.synthetic.main.fragment_xml_layout.*
import kotlinx.android.synthetic.main.recycler_item_flower_pot.view.*

class ViewClassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_xml_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = LinearAdapterConfig<FlowerPotModel>().apply {
            items = FlowerPotDatabase.getAllEntries()
            addItemLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            )
            addOnItemClickListener { item, position ->
                val context = requireContext()
                Toast.makeText(context, context.getString(item.nameResId), Toast.LENGTH_SHORT)
                    .show()
            }
            setCyclic()
        }
        // Config optional
        val recyclerAdapter =
            RecyclerAdapterBuilder.fromView<FlowerPotModel, FlowerPotItemView>(config)

        // Alternatively, we could instantiate the view ourselves. Config optional
        val recyclerAdapterFromViewCreator =
            RecyclerAdapterBuilder.fromViewCreator { FlowerPotItemView(it) }

        // Alternatively, we could also specify the view class manually
        // This is useful when calling from Java. Config optional
        val recyclerAdapterFromViewClass =
            RecyclerAdapterBuilder.fromViewClass(FlowerPotItemView::class.java)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    class FlowerPotItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FlowerPotModel> {

        init {
            LayoutInflater.from(context).inflate(R.layout.recycler_item_flower_pot, this)
        }

        override fun setItem(position: Int, item: FlowerPotModel) {
            potImageView.setImageResource(item.imageResId)
            potNameTextView.setText(item.nameResId)
        }
    }
}