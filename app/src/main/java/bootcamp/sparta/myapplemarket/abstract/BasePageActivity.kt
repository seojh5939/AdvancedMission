package bootcamp.sparta.myapplemarket.abstract

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import bootcamp.sparta.myapplemarket.R
import com.google.android.material.snackbar.Snackbar

// Activity에서 사용할 공통 메서드를 위한 클레스
abstract class BasePageActivity : AppCompatActivity(){

    abstract fun initView()

    // 좋아요 클릭시 사용되는 Snackbar
    fun likeSnackBar(view: View, msg: String = getString(R.string.snakbar_msg)) =
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)

    fun exitDialog(positiveButton: dialogButtonClicked) {
        val dialog = ExitDialog(positiveButton, "종료", "종료 하시겠습니까?", R.drawable.icon_chat)
        dialog.isCancelable = false
        dialog.show(this.supportFragmentManager, "ExitDialog")
    }

    fun notification(title: String, content: String) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder : NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "appleMarket-channel"
            val channelName = "MyAppleMarket"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "사과마켓 알림버튼 클릭시 동작합니다."
                setShowBadge(true)

                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttribute = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttribute)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.drawable.notification_bitmap)
            setWhen(System.currentTimeMillis())
            setContentTitle(title)
            setStyle(NotificationCompat.BigTextStyle().bigText(content))
        }

        manager.notify(1, builder.build())
    }
}