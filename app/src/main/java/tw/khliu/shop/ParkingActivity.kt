package tw.khliu.shop

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_parking.*
import java.net.URL

class ParkingActivity : AppCompatActivity() {
    private val _TAG=this::class.java.simpleName
    private val _URL = "http://www.saja.com.tw/site/mall/?json=Y"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        ParkingTask().execute(_URL)
    }

    inner class ParkingTask:AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json = url.readText()
            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tv_parking.text=result
        }
    }
}
