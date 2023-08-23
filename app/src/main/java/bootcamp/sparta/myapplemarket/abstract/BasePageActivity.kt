package bootcamp.sparta.myapplemarket.abstract

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import bootcamp.sparta.myapplemarket.R
import com.google.android.material.snackbar.Snackbar

// Activity에서 사용할 공통 메서드를 위한 클레스
abstract class BasePageActivity : AppCompatActivity() {

    // 좋아요 클릭시 사용되는 Snackbar
    fun likeSnackBar(view: View, msg: String = getString(R.string.snakbar_msg)) =
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
}