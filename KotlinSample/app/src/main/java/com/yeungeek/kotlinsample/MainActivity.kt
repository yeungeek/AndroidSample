package com.yeungeek.kotlinsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastList = findViewById(R.id.forecast_list) as RecyclerView
        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecatListAdapter(items)
//        message.text = "Hello Kotlin"
//        toast("Hello Kotlin")
//        toast("Hello Kotlin Again!", Toast.LENGTH_LONG)

//        niceToast("Hello Kotlin")
//        niceToast("Hello Kotlin Long", length = Toast.LENGTH_LONG)
    }

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    class ForecatListAdapter(val items: List<String>) : RecyclerView.Adapter<ForecatListAdapter.ViewHolder>() {
        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.textView?.text = items[position]
        }

        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    }

    fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, length).show()
    }

    fun niceToast(message: String, tag: String = MainActivity::class.java.simpleName, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, "[$tag] $message", length).show()
    }
}
