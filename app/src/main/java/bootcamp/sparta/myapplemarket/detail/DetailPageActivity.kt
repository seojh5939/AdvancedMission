package bootcamp.sparta.myapplemarket.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.R.*
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.abstract.BasePageActivity
import bootcamp.sparta.myapplemarket.data.ProductsItemData
import bootcamp.sparta.myapplemarket.data.model.Product
import bootcamp.sparta.myapplemarket.databinding.DetailPageActivityBinding
import bootcamp.sparta.myapplemarket.main.MainPageActivity
import bootcamp.sparta.myapplemarket.util.setComma

class DetailPageActivity : BasePageActivity() {
    private lateinit var binding: DetailPageActivityBinding

    private lateinit var toolbar: Toolbar // 뒤로가기
    private lateinit var ivImage: ImageView // 상단 제품이미지
    private lateinit var tvUserId: TextView // 사용자명
    private lateinit var tvLocation: TextView // 위치
    private lateinit var tvPrice: TextView // 위치
    private lateinit var tvBoardTitle: TextView // 글 제목
    private lateinit var tvBoardContents: TextView // 글 내용
    private lateinit var likeButton: ImageButton // 좋아요버튼

    private var position: Int = 0

    companion object {
        fun newIntnet(context: Context): Intent = Intent(context, DetailPageActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initToolbar()
        setViewValue()
    }

    override fun initView() {
        toolbar = binding.detailToolbar
        ivImage = binding.detailImage
        tvUserId = binding.detailUserid
        tvLocation = binding.detailUserLocation
        tvPrice = binding.detailTvPrice
        tvBoardTitle = binding.detailBoardTitle
        tvBoardContents = binding.detailBoardContent
        likeButton = binding.detailLike
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
            it.setHomeAsUpIndicator(R.drawable.back_arrow)
        }
    }

    // 툴바 뒤로가기버튼처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.home -> {
                finish()
                val intent: Intent = MainPageActivity.newIntent(this)
                    .putExtra("intent_position", position)
               startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setViewValue() {
        val item = getIntentItemData()
        item?.let {
            ivImage.setImageResource(it.image)
            tvUserId.text = it.user
            tvLocation.text = it.location
            tvPrice.text = setComma(it.price) + "원"
            tvBoardTitle.text = it.title
            tvBoardContents.text = it.description
            likeButton.setImageResource(it.likeIcon)
            setButtonOnClickListener(item.id)
            position = item.id - 1
        }
    }

    private fun getIntentItemData(): Product? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableExtra(
                getString(R.string.intent_key_product),
                Product::class.java
            )
        } else {
            return intent.getParcelableExtra<Product>("intent_product")
        }
    }

    private fun setButtonOnClickListener(id: Int) {
        likeButton.setOnClickListener {
            val item = ProductsItemData.getProducts(id)
            item?.let {
                it.isLike = !it.isLike
                if (it.isLike) {
                    it.like += 1
                    it.likeIcon = R.drawable.icon_like_fill
                } else {
                    it.like -= 1
                    it.likeIcon = R.drawable.icon_like
                }
            }
            refreshView(item!!)
        }
    }

    private fun refreshView(item: Product) {
        val intent: Intent = DetailPageActivity.newIntnet(this)
        intent.putExtra("intent_product", item)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}