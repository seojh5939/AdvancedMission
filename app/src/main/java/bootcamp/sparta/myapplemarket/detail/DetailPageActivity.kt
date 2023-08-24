package bootcamp.sparta.myapplemarket.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.databinding.DetailPageActivityBinding

class DetailPageActivity : AppCompatActivity() {
    private lateinit var binding: DetailPageActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.detailToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title=""
            it.setHomeAsUpIndicator(R.drawable.back_arrow)
        }
    }
}