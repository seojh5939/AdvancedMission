package bootcamp.sparta.myapplemarket.data.model

import androidx.annotation.DrawableRes

// Product의 고유값 id
private var num: Int = -1
fun increment() = ++num

// 제품 Model Class
data class Product(
    val id: Int = increment(), // id(like PK)
    @DrawableRes
    val iamge: Int?, // 제품 이미지
    val title: String, // 제목
    val location: String, // 지역
    val price: String, // 가격
    val chat: String, // 채팅개수
    val like: Int, // 좋아요 수
)