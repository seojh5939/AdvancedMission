package bootcamp.sparta.myapplemarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// 제품 Model Class
@Parcelize
data class Product(
    val id: Int, // id(like PK)
    val image: Int, // 제품 이미지
    val title: String, // 제목
    val description : String, // 상품소개
    val user: String, // 판매자
    val location: String, // 지역
    val price: String, // 가격
    val chat: String, // 채팅개수
    var like: Int, // 좋아요 수
    var isLike: Boolean = false // 좋아요 클릭여부
) : Parcelable