package tw.khliu.shop

import android.app.Activity
import android.content.Context

fun Activity.setNickname(nick:String) {
    getSharedPreferences("shop", Context.MODE_PRIVATE)
        .edit().putString("NICKNAME", nick)
        .apply()
}

fun Activity.getNickname():String {
    return getSharedPreferences("shop", Context.MODE_PRIVATE)
        .getString("NICKNAME","").toString()
}