package tw.khliu.shop

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nickname.*

class NicknameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname)

        btn_done.setOnClickListener {
            val nick = et_nickname.getText().toString()
            setNickname(nick)
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
}
