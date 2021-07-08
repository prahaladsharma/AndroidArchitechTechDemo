package com.viaplaytest.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.viaplaytest.commonUtil.ConnectivityUtil
import com.viaplaytest.di.Injectable
import com.viaplaytest.di.injectViewModel
import javax.inject.Inject
import com.viaplaytest.api.Status
import com.viaplaytest.databinding.FragmentViaplayListBinding
import com.viaplaytest.ui.viewmodel.ViaPlayViewModel
import kotlinx.android.synthetic.main.fragment_viaplay_list.*

class ViaplaySectionListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ViaPlayViewModel
    private var isConnected : Boolean = true
    private lateinit var  binding : FragmentViaplayListBinding
    val newsViewModel : ViaPlayViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViaplayListBinding.inflate(inflater,container,false)
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        isConnected = ConnectivityUtil.isConnected(context)
        if (!isConnected)
            Toast.makeText(context?.applicationContext,"No internet connection!",Toast.LENGTH_SHORT).show()

        val adapter = NewsAdapter()
        binding.rvNewsList.adapter = adapter
        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: NewsAdapter) {
        val data = viewModel.newsList(isConnected)
        data?.networkState?.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.RUNNING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.FAILED -> {
                    progressBar.visibility = View.GONE
                    // Handle fail state
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE

                }
            }
        })
        data?.pagedList?.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}