package de.rauschdo.flipdigit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val dataset: List<DemosAdapter.Demo> = listOf(
        DemosAdapter.Demo(
            "Demo",
            "Basic Display of the provided Views",
            DemoActivity::class.java
        )
        //Postponed
//        ,
//        DemosAdapter.Demo(
//            "Playground",
//            "Playground with controls to manipulate the customizable values",
//            PlaygroundActivity::class.java
//        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = DemosAdapter(dataset)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun start(activity: Class<*>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}