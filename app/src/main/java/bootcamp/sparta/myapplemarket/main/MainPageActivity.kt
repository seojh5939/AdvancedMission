package bootcamp.sparta.myapplemarket.main

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.abstract.BasePageActivity
import bootcamp.sparta.myapplemarket.abstract.dialogButtonClicked
import bootcamp.sparta.myapplemarket.databinding.MainPageActivityBinding

class MainPageActivity : BasePageActivity() {
    private lateinit var binding: MainPageActivityBinding
    private lateinit var spinner: Spinner
    private lateinit var notificationButton: ImageButton
    private lateinit var recyclerview: RecyclerView
    private val itemList: MutableList<String> = mutableListOf()

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
        initNotification()
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

    private fun initView() {
        spinner = binding.mainSpinner
        notificationButton = binding.mainNotification
        recyclerview = binding.mainRecyclerview
    }

    private fun initSpinner() {
        // spinner 더미데이터 추가
        itemList.addAll(resources.getStringArray(R.array.dummy_spinner_items))

        val adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            itemList
        )
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun initRecyclerView() {
        val adapter = MainPageRecyclerViewAdapter()
        val divider = DividerItemDecoration(this, VERTICAL)
        recyclerview.addItemDecoration(divider)
        recyclerview.adapter = adapter
    }

    private fun initNotification() {
        notificationButton.setOnClickListener {
            notification(getString(R.string.notification_title), getString(R.string.notification_content))
        }
    }

}