package com.android.groupiissue.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.groupiissue.R
import com.android.groupiissue.databinding.ListItemBinding
import com.android.groupiissue.databinding.MainFragmentBinding
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.viewbinding.BindableItem

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.list.layoutManager = LinearLayoutManager(requireContext())
        _binding!!.list.adapter = GroupieAdapter()
        _binding!!.refreshItems.setOnClickListener {
            (_binding!!.list.adapter as GroupieAdapter).updateAsync((0..10).map { ListItemTest(it.toLong()) })
        }

        _binding!!.replaceItems.setOnClickListener {
            (_binding!!.list.adapter as GroupieAdapter).replaceAll((0..10).map { ListItemTest(it.toLong()) })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ListItemTest(private val identifier: Long) : BindableItem<ListItemBinding>(identifier) {

    override fun initializeViewBinding(view: View): ListItemBinding = ListItemBinding.bind(view)

    override fun bind(viewBinding: ListItemBinding, position: Int) {
        viewBinding.root.text = "Item $identifier"
    }

    override fun getLayout() = R.layout.list_item
}