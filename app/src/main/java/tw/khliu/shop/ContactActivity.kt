package tw.khliu.shop

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ContactActivity : AppCompatActivity() {
    private val TAG="ContactActivity"
    private val REQ_READ_CONTACTS:Int=387

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        val chk = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
        if(chk==PackageManager.PERMISSION_GRANTED) {
            readContacts()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQ_READ_CONTACTS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQ_READ_CONTACTS) {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                readContacts()
            }
        }
    }
    private fun readContacts() {
        val cursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (cursor.moveToNext()) {
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            Log.d(TAG, "Contact Display Name : $name")
        }
    }
}
