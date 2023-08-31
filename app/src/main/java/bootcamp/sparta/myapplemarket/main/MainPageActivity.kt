package bootcamp.sparta.myapplemarket.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.abstract.BasePageActivity
import bootcamp.sparta.myapplemarket.abstract.dialogButtonClicked
import bootcamp.sparta.myapplemarket.data.ProductsItemData
import bootcamp.sparta.myapplemarket.databinding.MainPageActivityBinding
import bootcamp.sparta.myapplemarket.detail.DetailPageActivity
import bootcamp.sparta.myapplemarket.abstract.onRecyclerViewItemClickListener
import bootcamp.sparta.myapplemarket.abstract.onRecyclerViewItemLongClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainPageActivity : BasePageActivity() {
    private lateinit var binding: MainPageActivityBinding
    private lateinit var spinner: Spinner
    private lateinit var notificationButton: ImageButton
    private lateinit var recyclerview: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val spinnerItemList: MutableList<String> = mutableListOf()
//    private val recyclerviewItemList : MutableList<Product> = ProductsItemData.getProducts()

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, MainPageActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initSpinner()
        initRecyclerView()
        addRecyclerViewScrollListener()
        initNotification()
        setFabClickListener()
    }

    override fun onResume() {
        super.onResume()
        getIntentItemId()
    }

    // DetailPage에서 받은 Position Refresh
    private fun getIntentItemId() {
        val result = intent.getIntExtra("intent_position", -1)
        if(result != -1) {
            recyclerview.adapter?.notifyItemChanged(result)
        }
    }

    // Main화면에서 Back Button 클릭시 처리
    override fun onBackPressed() {
        val positive = object : dialogButtonClicked {
            override fun onPositiveButtonClick() {
                finish()
            }
        }

        exitDialog("종료", "종료하시겠습니까?", positiveButton = positive)
    }

    override fun initView() {
        spinner = binding.mainSpinner
        notificationButton = binding.mainNotification
        recyclerview = binding.mainRecyclerview
        fab = binding.mainFab
    }

    private fun initSpinner() {
        // spinner 더미데이터 추가
        spinnerItemList.addAll(resources.getStringArray(R.array.dummy_spinner_items))

        val adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            spinnerItemList
        )
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun initRecyclerView() {
        val itemClickEvent = recyclerViewItemClickListener()
        val itemLongClickEvent = recyclerViewItemLongClickListener()
        val adapter = MainPageRecyclerViewAdapter(ProductsItemData.getProducts(), itemClickEvent, itemLongClickEvent)
        val divider = DividerItemDecoration(this, VERTICAL)
        recyclerview.addItemDecoration(divider)

        recyclerview.adapter = adapter
    }

    // 리사이클러뷰 아이템 클릭시 DetailPage로 데이터 넘기면서 이동
    private fun recyclerViewItemClickListener() : onRecyclerViewItemClickListener {
        return object : onRecyclerViewItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                when(view.id) {
                    R.id.item_main_layout -> itemViewClicked(position)
                    R.id.item_main_icon_like -> likeBtnClicked(position)
                }
            }
        }
    }

    private fun itemViewClicked(position: Int) {
        val item = ProductsItemData.getProducts()[position]
        val intent = DetailPageActivity.newIntnet(this@MainPageActivity)
        intent.putExtra(getString(R.string.intent_key_product), item)
        startActivity(intent)
    }

    private fun likeBtnClicked(position: Int) {
        val item = ProductsItemData.getProducts()[position]
        item.isLike = !item.isLike
        if(item.isLike){
            item.likeIcon = R.drawable.icon_like_fill
            item.like += 1
            recyclerview.adapter?.notifyItemChanged(position)
        } else {
            item.likeIcon = R.drawable.icon_like
            item.like -= 1
            recyclerview.adapter?.notifyItemChanged(position)
        }
    }

    // 아이템 삭제
    private fun recyclerViewItemLongClickListener(): onRecyclerViewItemLongClickListener {
        return object : onRecyclerViewItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                // 다이얼로그 클릭시 item 삭제
                val positive = object : dialogButtonClicked {
                    override fun onPositiveButtonClick() {
                        ProductsItemData.getProducts().removeAt(position)
                        recyclerview.adapter?.notifyItemRemoved(position)
                    }
                }
                exitDialog("삭제", "선택한 아이템을 삭제하시겠습니까?", R.drawable.manner_icon, positive)
            }
        }
    }

    // 리사이클러뷰 스크롤시 fab버튼 보이거나 사라지도록 구현
    private fun addRecyclerViewScrollListener() {
        val listener = object : View.OnScrollChangeListener {
            val fadeOut = AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_fade_out)
            val fadeIn = AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_fade_in)
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                // 최상단일경우 fab 숨김
                if(!recyclerview.canScrollVertically(-1)) {

                    if(checkFabVisibility()){
                        fab.visibility = View.INVISIBLE
                        fab.startAnimation(fadeOut)
                    }
                    // 그 외 경우 보이도록
                } else {

                    if(!checkFabVisibility()) {
                        fab.visibility = View.VISIBLE
                        fab.startAnimation(fadeIn)
                    }
                }
            }
        }
        recyclerview.setOnScrollChangeListener(listener)
    }

    // fab의 현재 visibility 상태체크
    private fun checkFabVisibility() : Boolean {
        return fab.visibility == View.VISIBLE
    }

    // 알람기능
    private fun initNotification() {
        notificationButton.setOnClickListener {
            notification(getString(R.string.notification_title), getString(R.string.notification_content))
        }
    }

    // fab버튼 클릭시 최상단으로 이동
    private fun setFabClickListener() {
        fab.setOnClickListener {
            recyclerview.smoothScrollToPosition(0)
        }
    }

}