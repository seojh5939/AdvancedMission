package bootcamp.sparta.myapplemarket.abstract

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import bootcamp.sparta.myapplemarket.R
import com.google.android.material.snackbar.Snackbar

// Activity에서 사용할 공통 메서드를 위한 클레스
abstract class BasePageActivity : AppCompatActivity(){

    // 좋아요 클릭시 사용되는 Snackbar
    fun likeSnackBar(view: View, msg: String = getString(R.string.snakbar_msg)) =
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)

    fun exitDialog(positiveButton: dialogButtonClicked) {
        val dialog = ExitDialog(positiveButton, "종료", "종료 하시겠습니까?", R.drawable.icon_chat)
        dialog.isCancelable = false
        dialog.show(this.supportFragmentManager, "ExitDialog")
    }
}