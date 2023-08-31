package bootcamp.sparta.myapplemarket.abstract

import android.view.View

// RecyclerViewItem에서 사용하는 ClickEvent용 interface
interface onRecyclerViewItemClickListener {
    fun onItemClick(view: View, position: Int)
}

// RecyclerViewItem에서 사용하는 LongClickEvent용 interface
interface onRecyclerViewItemLongClickListener {
    fun onItemLongClick(view: View, position: Int)
}
