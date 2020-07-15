package tw.khliu.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*

class MainActivity : AppCompatActivity() {
    private val TAG="MainActivity"
    private val REQ_NICKNAME: Int=110
    private val REQ_SIGNUP: Int= 100
    val auth = FirebaseAuth.getInstance()
    var signup=false
    val functions = listOf<String>(
        "Camera"
        ,"Invite Friends"
        ,"Parking"
        ,"Download Coupon"
        ,"Maps"
        ,"News"
        ,"Bus Data"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth.addAuthStateListener { auth->
            authChanged(auth)
        }
        val colors = arrayOf("red","green","blue","yellow")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colors)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spn.adapter=adapter
        spn.onItemSelectedListener = object:OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("MainActivity","Selected idx : ${position}, item : ${colors[position]}")
            }

        }

        // recycler view
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = FunctionAdapter()
    }

    inner class FunctionAdapter():RecyclerView.Adapter<FunctionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_function,parent,false)
            val holder = FunctionHolder(view)
            return holder
        }

        override fun getItemCount(): Int {
           return functions.size
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
                 holder.nameText.text = functions.get(position)
                 holder.itemView.setOnClickListener { view->
                     functionClick(holder, position)
                 }
        }

    }

    private fun functionClick(holder: FunctionHolder, position: Int) {
        Log.d(TAG,"function clicked : $position")
        // startActivity(Intent(this,BusdataActivity::class.java))

        when(position){
            6->startActivity(Intent(this,BusdataActivity::class.java))
            1->startActivity(Intent(this,ContactActivity::class.java))
            2->startActivity(Intent(this,ParkingActivity::class.java))
        }

    }

    class FunctionHolder(view :View):RecyclerView.ViewHolder(view){
            var nameText: TextView = view.tv_item_name
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
            if(resultCode==Activity.RESULT_OK) {
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
