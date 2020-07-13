package tw.khliu.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_busdata.*
import kotlinx.android.synthetic.main.row_busdata.*
import kotlinx.android.synthetic.main.row_busdata.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import java.net.URL

class BusdataActivity : AppCompatActivity(), AnkoLogger{
    val _TAG="BusdataActivity"
    val _URL="http://www.diandiandian.com.tw/busdata.php"
    var jstr:String? = null
    var busdatas:List<Busdata>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        info("111111111")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busdata)
        doAsync {
            jstr = URL(_URL).readText()
            busdatas = Gson().fromJson<List<Busdata>>(jstr, object : TypeToken<List<Busdata>>(){}.type)
            uiThread{
                info(jstr)
                recycler.layoutManager=LinearLayoutManager(this@BusdataActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter=BusdataAdapter()
            }
        }
    }
    inner class BusdataAdapter():RecyclerView.Adapter<BusdataHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusdataHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_busdata,parent,false)
            return BusdataHolder(view)
        }

        override fun getItemCount(): Int {
             val size:Int = busdatas?.size?:0
             return size
        }

        override fun onBindViewHolder(holder: BusdataHolder, position: Int) {
            val item = busdatas?.get(position)
            holder.bindData(item!!)

        }

    }
    inner class BusdataHolder(view : View):RecyclerView.ViewHolder(view){
          val tvBusid:TextView = view.tv_busid
          val tvRouteid:TextView=view.tv_routeid
          val tvSpeed:TextView = view.tv_speed
          fun bindData(bdata : Busdata) {
               tvBusid.text = bdata.BusID
               tvRouteid.text= bdata.RouteID
               tvSpeed.text = bdata.Speed
           }
    }
}

data class Busdatas(
    val datas: List<Busdata>
)

data class Busdata(
    val Azimuth: String,
    val BusID: String,
    val BusStatus: String,
    val DataTime: String,
    val DutyStatus: String,
    val GoBack: String,
    val Latitude: String,
    val Longitude: String,
    val ProviderID: String,
    val RouteID: String,
    val Speed: String,
    val ledstate: String,
    val sections: String
)