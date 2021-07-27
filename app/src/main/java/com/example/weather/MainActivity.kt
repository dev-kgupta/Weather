package com.example.weather

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
//import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.NumberFormat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    // weather url to get JSON
    var weather_url1 = ""

    // api id for url
    var api_id1 = "55dbf578e8b3ff3bbe7cd54fe5e9b0df"

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // link the textView in which the
        // temperature will be displayed
        textView = findViewById(R.id.TextView1)

        // create an instance of the Fused
        // Location Provider Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.e("lat", weather_url1)

        // on clicking this button function to
        // get the coordinates will be called
        button = findViewById(R.id.button1)
        button.setOnClickListener {
            Log.e("lat", "onClick")
            // function to find the coordinates
            // of the last location
            obtainLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        Log.e("lat", "function")
        // get the last location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // get the latitude and longitude
                // and create the http URL
              weather_url1 = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&appid=" + api_id1
               // weather_url1 = "api.openweathermap.org/data/2.5/weather?" + "lat=" + 139 + "&lon=" + 35 + "&appid=" + api_id1
              //weather_url1 = "http://api.openweathermap.org/data/2.5/weather?" + "lat=139" + "&lon=35" + "&appid=" + api_id1
                Log.e("lat", weather_url1.toString())
                // this function will
                // fetch data from URL
                getTemp()
            }
    }

    fun getTemp() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = weather_url1
        Log.e("lat", url)

        // Request a string response
        // from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            { response ->
                Log.e("lat", response.toString())

                // get the JSON object
                val obj = JSONObject(response)

                // get the Array from obj of name - "data"
               // obj.getJSONArray()
                //val arr = obj.getJSONArray("main")
               // Log.e("lat obj1", arr.toString())

                // get the JSON object from the
                // array at index position 0
                val obj2 = obj.getJSONObject("main")
                Log.e("lat obj2", obj2.toString())

                //Kelvin to Celsius
                var temp=obj2.getString("temp")
                var tempint=temp.toDouble()
                //tempint=kotlin.math.ceil(tempint)
                var tempinc=tempint-273.15
                tempinc=kotlin.math.ceil(tempinc)
                // set the temperature and the city
                // name using getString() function

                textView.text = "Current temperature is "+tempinc.toString() + " deg Celcius"
                //textView.text = obj2.toString() + " deg Celcius"
                //textView.text = "hey"
            },
            // In case of any error
            { var err= VolleyError()
                Log.e("Error",err.message.toString())
                textView!!.text = "That didn't work!" })
        queue.add(stringReq)
    }
}