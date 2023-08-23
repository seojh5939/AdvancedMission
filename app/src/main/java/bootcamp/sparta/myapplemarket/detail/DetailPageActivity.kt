package bootcamp.sparta.myapplemarket.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.databinding.DetailPageActivityBinding

class DetailPageActivity : AppCompatActivity() {
    private var binding: DetailPageActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailPageActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}