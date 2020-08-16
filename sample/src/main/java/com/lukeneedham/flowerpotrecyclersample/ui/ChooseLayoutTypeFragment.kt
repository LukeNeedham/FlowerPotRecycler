package com.lukeneedham.flowerpotrecyclersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.fragment_choose_layout.*

class ChooseLayoutTypeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_choose_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showXmlLayoutButton.setOnClickListener {
            showXmlLayout()
        }
        showAnkoLayoutButton.setOnClickListener {
            showAnkoLayout()
        }
        showViewLayoutButton.setOnClickListener {
            showViewLayout()
        }
    }

    private fun showXmlLayout() {
        findNavController().navigate(R.id.action_chooseLayoutTypeFragment_to_xmlLayoutFragment)
    }

    private fun showAnkoLayout() {
        findNavController().navigate(R.id.action_chooseLayoutTypeFragment_to_ankoWithDatabindingLayoutFragment)
    }

    private fun showViewLayout() {
        findNavController().navigate(R.id.action_chooseLayoutTypeFragment_to_viewClassFragment)
    }
}
