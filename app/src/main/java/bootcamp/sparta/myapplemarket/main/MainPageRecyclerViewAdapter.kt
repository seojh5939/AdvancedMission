package bootcamp.sparta.myapplemarket.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.myapplemarket.data.model.Product
import bootcamp.sparta.myapplemarket.databinding.MainItemRecyclerviewBinding
import bootcamp.sparta.myapplemarket.abstract.onRecyclerViewItemClickListener
import bootcamp.sparta.myapplemarket.abstract.onRecyclerViewItemLongClickListener
import bootcamp.sparta.myapplemarket.util.setComma


class MainPageRecyclerViewAdapter(
    listData: MutableList<Product>,
    val onItemClickEventListener: onRecyclerViewItemClickListener? = null,
    val onItemLongClickEventListener: onRecyclerViewItemLongClickListener? = null
) : RecyclerView.Adapter<MainPageRecyclerViewAdapter.Holder>() {

    private val mListData: MutableList<Product>

    init {
        mListData = listData
    }

    class Holder(private val binding: MainItemRecyclerviewBinding, private val onItemClick: onRecyclerViewItemClickListener? = null, private val onItemLongClick: onRecyclerViewItemLongClickListener? = null) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        private lateinit var image: ImageView
        private lateinit var title: TextView
        private lateinit var location: TextView
        private lateinit var price: TextView
        private lateinit var chat: Button
        private lateinit var like: Button

        init {
            initView()
        }

        private fun initView() {
            image = binding.itemMainImage
            image.clipToOutline = true
            title = binding.itemMainTitle
            location = binding.itemMainLocation
            price = binding.itemMainPrice
            chat = binding.itemMainIconChat
            like = binding.itemMainIconLike
        }

        fun bind(data: Product) {
            image.setImageResource(data.image)
            title.text = data.title
            location.text = data.location
            price.text = setComma(data.price) + "원"
            chat.text = data.chat
            like.text = data.like.toString()
            changeLikeIconDrawable(data.likeIcon)
            setItemClickListener(itemView)
        }

        // 좋아요 아이콘 drawable 변경
        private fun changeLikeIconDrawable(icon: Int) {
            like.setCompoundDrawablesWithIntrinsicBounds(null, null, itemView.resources.getDrawable(icon, null), null)
        }

        private fun setItemClickListener(itemView: View) {
            // item Click
            itemView.setOnClickListener(this)
            // 좋아요 Click
            like.setOnClickListener(this)
            // item LongClick
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemClick?.onItemClick(p0!!, layoutPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            onItemLongClick?.onItemLongClick(p0!!, layoutPosition)
            return false
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageRecyclerViewAdapter.Holder {
        val bind =
            MainItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind, onItemClickEventListener, onItemLongClickEventListener)
    }

    override fun onBindViewHolder(holder: MainPageRecyclerViewAdapter.Holder, position: Int) {
        val item = mListData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mListData.size
    }
}