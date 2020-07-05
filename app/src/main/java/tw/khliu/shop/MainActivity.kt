package tw.khliu.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQ_NICKNAME: Int=110
    private val REQ_SIGNUP: Int= 100
    val auth = FirebaseAuth.getInstance()
    var signup=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth.addAuthStateListener { auth2->
            authChanged(auth2)
        }
        val colors = arrayOf("red","green","blue","yellow")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spn.adapter=adapter
        
    }

    override fun onResume() {
        super.onResume()
        // tv_nickname0.setText(getNickname())
        val uid = auth.currentUser?.uid
        if(uid==null ){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivityForResult(intent, REQ_SIGNUP)
            return
        }
        FirebaseDatabase.getInstance()
            .getReference("users")
            .child(uid)
            .child("nickname")
            .addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(dataerror: DatabaseError) {
                    //TODO("Not yet implemented")
                }
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    tv_nickname0.text = datasnapshot.value.toString()
                }
            })
    }
    private fun authChanged(auth: FirebaseAuth) {
            if(auth.currentUser==null) {
                val intent = Intent(this,SignUpActivity::class.java)
                startActivityForResult(intent, REQ_SIGNUP)
            } else {
                Log.d("MainActivity","authcganged : ${auth.currentUser?.uid}")
            }
            return
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
                tv_nickname0.text=nickname
                AlertDialog.Builder(this)
                    .setTitle("show nickname")
                    .setMessage("Your Nickname is $nickname")
                    .setPositiveButton("Confirm", null)
                    .show()
            }
        }
    }
}
