package tw.khliu.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val REQ_NICKNAME: Int=110
    private val REQ_SIGNUP: Int= 100
    var signup=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!signup) {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent, REQ_SIGNUP)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQ_SIGNUP) {
            if(resultCode== Activity.RESULT_OK) {
                val intent = Intent(this,NicknameActivity::class.java)
                startActivityForResult(intent,REQ_NICKNAME)
            }
        }
        if(requestCode==REQ_NICKNAME) {
            if(resultCode==Activity.RESULT_OK) {
                val nickname = getSharedPreferences("shop", Context.MODE_PRIVATE)
                    .getString("NICKNAME","").toString()

                AlertDialog.Builder(this)
                    .setTitle("show nickname")
                    .setMessage("Your Nickname is $nickname")
                    .setPositiveButton("Confirm", null)
                    .show()
            }
        }
    }
}
