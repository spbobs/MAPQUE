package com.bobs.mapque.searchlist.ui

import android.view.*
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bobs.mapque.R
import com.bobs.mapque.databinding.SearchlistItemBinding
import com.bobs.mapque.searchlist.data.model.SearchItem
import com.bobs.mapque.util.listener.MapListener
import com.bobs.mapque.util.listener.SearchListListener

class SearchListAdapter(private val mapListener: MapListener<SearchItem>, private val searchListListener: SearchListListener<SearchItem>)
    : RecyclerView.Adapter<SearchListAdapter.SearchListHolder>() {
    var items: List<SearchItem>? = null
        set(value) {
            // 값을 세팅하면 리사이클러뷰 전체 갱신도 같이 한다
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.searchlist_item, parent, false)
        return SearchListHolder(item)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        val item = items?.get(position)

        holder.run {
            binding?.run {
                this.item = item

                // 팝업메뉴 구성
                popupBtn.setOnClickListener {
                   PopupMenu(holder.itemView.context, it).apply {
                        inflate(R.menu.searchitem_menu)
                        setOnMenuItemClickListener { menuitem ->
                            when(menuitem.itemId){
                                R.id.moveMap -> {
                                    binding.item?.run { mapListener.moveMap(this) }
                                    true
                                }

                                R.id.shareData -> {
                                    binding.item?.run { searchListListener.shareData(this) }
                                    true
                                }

                                R.id.deleteItem -> {
                                    binding.item?.run { searchListListener.deleteItem(this) }
                                    true
                                }

                                else -> false
                            }
                        }

                       show()
                    }
                }
            }
        }
    }

    inner class SearchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // 홀더는 리스트 아이템 바인딩만 세팅해주면 된다.
        val binding: SearchlistItemBinding? = DataBindingUtil.bind(itemView)
    }
}