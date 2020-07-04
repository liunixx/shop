package tw.khliu.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQ_NICKNAME: Int=110
    private val REQ_SIGNUP: Int= 100
    val auth = FirebaseAuth.getInstance()
    var signup=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth.addAuthStateListener { auth->
            authChanged(auth)
        }
    }

    override fun onResume() {
        super.onResume()
        tv_nickname0.setText(getNickname())
    }
    private fun authChanged(auth: FirebaseAuth) {
            if(auth.currentUser==null) {
                val intent = Intent(this,SignUpActivity::class.java)
                startActivityForResult(intent, REQ_SIGNUP)
            } else {
                Log.d("MainActivity","authcganged : ${auth.currentUser?.uid}")
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
                val nickname = getNickname()
                    
                AlertDialog.Builder(this)
                    .setTitle("show nickname")
                    .setMessage("Your Nickname is $nickname")
                    .setPositiveButton("Confirm", null)
                    .show()
            }
        }
    }
}
