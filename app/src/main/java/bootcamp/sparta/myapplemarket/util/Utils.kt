package bootcamp.sparta.myapplemarket.util

import java.lang.StringBuilder

// 가격표에 콤마 추가
fun setComma(data: String): String {
    val length = data.length
    // 콤마 개수
    val commaSize: Int = length / 4
    if (commaSize == 0) {
        return data
    }

    // 콤마 포지션 구하기
    var position = length - 3
    val price = StringBuilder(data)

    // 금액에 들어가야할 콤마개수만큼 콤마추가
    for (i in 0 until commaSize) {
        price.insert(position, ',')
        position -= 3
    }

    return price.toString()
}