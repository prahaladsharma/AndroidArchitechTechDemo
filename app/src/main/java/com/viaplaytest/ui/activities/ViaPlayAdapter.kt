package com.viaplaytest.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.viaplaytest.api.ViaplaySection
import com.viaplaytest.databinding.RvNewsListItemsBinding

/**
 * Adapter for the [RecyclerView] in [ViaplaySectionListFragment].  //RecyclerView.Adapter
 */
class NewsAdapter : PagedListAdapter<ViaplaySection, NewsAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.apply {
            bind(createOnClickListener( newsItem), newsItem)
            itemView.tag = newsItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvNewsListItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }
    private fun createOnClickListener( newItem: ViaplaySection?): View.OnClickListener {
        return View.OnClickListener {
          val direction = ViaplaySectionListFragmentDirections
              .actionViaPlayListFragmentToViaPlayDetailFragment(newItem ?: ViaplaySection(newItem!!.id,newItem.title,newItem.href,newItem.type,newItem.name,newItem.templated))
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: RvNewsListItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: ViaplaySection?) {
            binding.apply {
                clickListener = listener
                newsItem = item
                executePendingBindings() }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ViaplaySection>() {

    override fun areItemsTheSame(oldItem: ViaplaySection, newItem: ViaplaySection): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ViaplaySection, newItem: ViaplaySection): Boolean {
        return oldItem == newItem
    }
}
