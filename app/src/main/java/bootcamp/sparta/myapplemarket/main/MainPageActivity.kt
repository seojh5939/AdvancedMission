package bootcamp.sparta.myapplemarket.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
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
import bootcamp.sparta.myapplemarket.data.getDummyData
import bootcamp.sparta.myapplemarket.data.model.Product
import bootcamp.sparta.myapplemarket.databinding.MainPageActivityBinding
import bootcamp.sparta.myapplemarket.detail.DetailPageActivity
import bootcamp.sparta.myapplemarket.main.MainPageRecyclerViewAdapter.onRecyclerViewItemClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainPageActivity : BasePageActivity() {
    private lateinit var binding: MainPageActivityBinding
    private lateinit var spinner: Spinner
    private lateinit var notificationButton: ImageButton
    private lateinit var recyclerview: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val spinnerItemList: MutableList<String> = mutableListOf()
    private val recyclerviewItemList : MutableList<Product> = mutableListOf()

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



    // Main화면에서 Back Button 클릭시 처리
    override fun onBackPressed() {
//        super.onBackPressed()
        val positive = object : dialogButtonClicked {
            override fun onPositiveButtonClick() {
                finish()
            }
        }

        exitDialog(positive)
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
        recyclerviewItemList.addAll(getDummyData())

        val itemClickEvent = recyclerViewItemClickListener()
        val adapter = MainPageRecyclerViewAdapter(recyclerviewItemList, itemClickEvent)
        val divider = DividerItemDecoration(this, VERTICAL)
        recyclerview.addItemDecoration(divider)

        recyclerview.adapter = adapter
    }

    // 리사이클러뷰 아이템 클릭시 DetailPage로 데이터 넘기면서 이동
    private fun recyclerViewItemClickListener() : onRecyclerViewItemClickListener{
        return object : onRecyclerViewItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = recyclerviewItemList[position]
                val intent = DetailPageActivity.newIntnet(this@MainPageActivity)
                intent.putExtra(getString(R.string.intent_key_product), item)
                startActivity(intent)
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