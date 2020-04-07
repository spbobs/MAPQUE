package com.bobs.mapque.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bobs.mapque.searchlist.ui.SearchListAdapter
import com.bobs.mapque.searchlist.data.model.SearchItem

@BindingAdapter("bind_items")
fun setItems(view: RecyclerView, items: List<SearchItem>?){
    items?.let {
         val adapter = view.adapter as SearchListAdapter
        adapter.items = it
    }
}