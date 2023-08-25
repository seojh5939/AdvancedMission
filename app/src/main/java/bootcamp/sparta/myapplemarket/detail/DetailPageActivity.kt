package bootcamp.sparta.myapplemarket.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.abstract.BasePageActivity
import bootcamp.sparta.myapplemarket.data.model.Product
import bootcamp.sparta.myapplemarket.databinding.DetailPageActivityBinding
import bootcamp.sparta.myapplemarket.util.setComma

class DetailPageActivity : BasePageActivity() {
    private lateinit var binding: DetailPageActivityBinding

    private lateinit var toolbar : Toolbar // 뒤로가기
    private lateinit var ivImage : ImageView // 상단 제품이미지
    private lateinit var ivProfileCircle : ImageView // 프로필사진
    private lateinit var tvUserId : TextView // 사용자명
    private lateinit var tvLocation : TextView // 위치
    private lateinit var tvPrice : TextView // 위치
    private lateinit var ivMannerIcon: ImageView // 메너 아이콘
    private lateinit var tvMannerTemperature : TextView // 메너온도
    private lateinit var tvBoardTitle : TextView // 글 제목
    private lateinit var tvBoardContents : TextView // 글 내용
    private lateinit var likeButton : ImageButton // 좋아요버튼
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
//        ivProfileCircle = binding.detailProfileCircle
        tvUserId = binding.detailUserid
        tvLocation = binding.detailUserLocation
        tvPrice = binding.detailTvPrice
//        ivMannerIcon = binding.detailIconManner
//        tvMannerTemperature = binding.detailTvTemperature
        tvBoardTitle = binding.detailBoardTitle
        tvBoardContents = binding.detailBoardContent
        likeButton = binding.detailLike
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title=""
            it.setHomeAsUpIndicator(R.drawable.back_arrow)
        }
    }

    private fun setViewValue() {
        val item = getItemData()
        item?.let {
            ivImage.setImageResource(it.image)
            tvUserId.text = it.user
            tvLocation.text = it.location
            tvPrice.text = setComma(it.price) + "원"
            tvBoardTitle.text = it.title
            tvBoardContents.text = it.description
        }
    }

    private fun getItemData() : Product? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return intent.getParcelableExtra(getString(R.string.intent_key_product), Product::class.java)
        } else {
            return intent.getParcelableExtra<Product>("intent_product")
        }
    }
}