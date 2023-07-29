package com.example.movieretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.movieretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL= "https://www.omdbapi.com/"
    private val API_KEY ="d16b94f8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val movieTitle = binding.edSearchBox.text.toString()
            getMovie(movieTitle)
        }
    }
    private fun getMovie(title:String){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDataInterface::class.java)
        api.getMovieDetails(API_KEY,title).enqueue(object: Callback<MovieData> {
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if(response.isSuccessful){
                    var movieData = response.body()!!
                   if(movieData.Title!=null){
                       binding.edMovieDetails.setText(movieData.toString())
                   }
                   else{
                       binding.edMovieDetails.text.clear()
                       Toast.makeText(applicationContext,"NOT FOUND",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext,"Response ${response.code()}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("MYAPI","Error $t")
            }
        })
    }
}