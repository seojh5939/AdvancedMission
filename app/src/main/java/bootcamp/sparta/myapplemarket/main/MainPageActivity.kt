package bootcamp.sparta.myapplemarket.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bootcamp.sparta.myapplemarket.R
import bootcamp.sparta.myapplemarket.abstract.BasePageActivity
import bootcamp.sparta.myapplemarket.databinding.MainPageActivityBinding

class MainPageActivity : BasePageActivity() {
    private var binding: MainPageActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }
}