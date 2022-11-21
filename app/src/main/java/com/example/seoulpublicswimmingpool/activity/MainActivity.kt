package com.example.seoulpublicswimmingpool.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seoulpublicswimmingpool.R
import com.example.seoulpublicswimmingpool.SeoulOpenApi
import com.example.seoulpublicswimmingpool.SeoulOpenService
import com.example.seoulpublicswimmingpool.adapter.CustomAdapter
import com.example.seoulpublicswimmingpool.database.DBHelper
import com.example.seoulpublicswimmingpool.database.SwimLesson
import com.example.seoulpublicswimmingpool.databinding.ActivityMainBinding
import com.example.seoulpublicswimmingpool.seoulPublicSwimLessonData.SwimmingPool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    companion object {
        const val DB_NAME = "swimLessonOfSoulPublicSwimmingPoolDB"
        var VERSION = 1
    }

    lateinit var binding: ActivityMainBinding
    var dataList = ArrayList<SwimLesson>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            swimmlessonData()
            setRecyclerView()
        }
    }

    /*Apply list type data to recyclerView using adapter*/
    private fun setRecyclerView() {
        customAdapter = CustomAdapter(dataList)
        binding.recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
        }
    }

    /*Receive public data using retrofit, insert into DB*/
    private fun swimmlessonData() {
        // 1.create a retrofit object
        val retrofit = Retrofit.Builder().baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create()).build()

        // 2.Pass interface to retrofit object
        val service = retrofit.create(SeoulOpenService::class.java)

        // 3.Implemented by overriding interface functions
        service.getSwimmingPools(SeoulOpenApi.API_KEY, SeoulOpenApi.LIMIT)
            .enqueue(object : Callback<SwimmingPool> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<SwimmingPool>, response: Response<SwimmingPool>,
                ) {
                    val data = response.body()

                    data?.let {
                        it.ListProgramByPublicSportsFacilitiesService.list_total_count
                        Log.d("retrofit", "contents loaded successfully")
                        val loadData = it.ListProgramByPublicSportsFacilitiesService.row
                        for (i in loadData.indices) {
                            val id = i + 1
                            val centerName = loadData[i].CENTER_NAME
                            val week = loadData[i].WEEK
                            val classTime = loadData[i].CLASS_TIME
                            val fee = loadData[i].FEE.toString()
                            dataList.add(SwimLesson(id, centerName, week, classTime, fee))
                            Log.d("retrofit", "$centerName $week $classTime $fee")
                        }
                        Log.d("retrofit", "${dataList.size}")

                        //4. insert into DB
                        val dbHelper = DBHelper(this@MainActivity, DB_NAME, VERSION)
                        if (dataList.size != 0) {
                            for (data in 0 until dataList.size) {
                                val swimLesson: SwimLesson = dataList[data]
                                dbHelper.insertData(swimLesson)
                            }
                        } else {
                            Log.d("seoulpublicswimmingpool", "DB Connection Failed ")
                        }
                    } ?: let {
                        Log.d("retrofit", "No data")
                    }
                    customAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<SwimmingPool>, t: Throwable) {
                    Log.d("retrofit", "loading error ${t.printStackTrace()}")
                    Toast.makeText(this@MainActivity, "Seoul public swimming pool lesson data loading error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    /*Register search menu in action bar*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        val searchMenu = menu?.findItem(R.id.menuSearch)
        val searchView = searchMenu?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(query: String?): Boolean {
                val dbHelper = DBHelper(applicationContext, DB_NAME, VERSION)
                if (query.isNullOrBlank()) {
                    dataList?.clear()
                    dbHelper.selectAll()?.let { dataList?.addAll(it) }
                    customAdapter.notifyDataSetChanged()
                } else {
                    dataList?.clear()
                    dbHelper.searchCenter(query)?.let { dataList?.addAll(it) }
                    customAdapter.notifyDataSetChanged()
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
