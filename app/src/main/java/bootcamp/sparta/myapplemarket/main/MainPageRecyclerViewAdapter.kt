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
import bootcamp.sparta.myapplemarket.util.setComma


class MainPageRecyclerViewAdapter(
    listData: MutableList<Product>,
    val onItemClickEventListener: onRecyclerViewItemClickListener? = null,
    val onItemLongClickEventListener: onRecyclerViewItemLongClickListener? = null
) : RecyclerView.Adapter<MainPageRecyclerViewAdapter.Holder>() {

    interface onRecyclerViewItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface onRecyclerViewItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    private val mListData: MutableList<Product>

    init {
        mListData = listData
    }

    class Holder(private val binding: MainItemRecyclerviewBinding, private val onItemClick: onRecyclerViewItemClickListener? = null) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var image: ImageView
        private lateinit var title: TextView
        private lateinit var location: TextView
        private lateinit var price: TextView
        private lateinit var chat: Button
        private lateinit var like: Button

        private lateinit var mOnItemClick : onRecyclerViewItemClickListener

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

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemClick?.let{
                mOnItemClick = onItemClick
                onItemClick.onItemClick(p0!!, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPageRecyclerViewAdapter.Holder {
        val bind =
            MainItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind, onItemClickEventListener)
    }

    override fun onBindViewHolder(holder: MainPageRecyclerViewAdapter.Holder, position: Int) {
        val item = mListData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mListData.size
    }
}