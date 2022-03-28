package xyz.heydarrn.latihanretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Response
import xyz.heydarrn.latihanretrofit.databinding.ActivityMainBinding
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinder:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainBinder= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinder.root)

        setRecyclerViewAdapter()

    }

    //menampilkan loading progress
    private fun showLoadingAnimation(isLoading:Boolean){
        if (isLoading){
            mainBinder.progressBar.visibility=View.VISIBLE
        }else{
            mainBinder.progressBar.visibility=View.GONE
        }
    }

    private fun setRecyclerViewAdapter(){
        val layoutManager=LinearLayoutManager(this)
        mainBinder.recyclerviewReviewList.layoutManager=layoutManager
        val itemDecoration=DividerItemDecoration(this,layoutManager.orientation)
        mainBinder.recyclerviewReviewList.addItemDecoration(itemDecoration)

        findRestaurant()
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        mainBinder.textviewTitle.text = restaurant.name
        mainBinder.textviewReview.text = restaurant.description
        Glide.with(this@MainActivity)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(mainBinder.imageRestoran)
    }
    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) {
        val listReview = ArrayList<String>()
        for (review in consumerReviews) {
            listReview.add(
                """
                ${review.review}
                - ${review.name}
                """.trimIndent()
            )
        }
        val adapter = RestaurantReviewAdapter(listReview)
        mainBinder.recyclerviewReviewList.adapter = adapter
        mainBinder.editTextReview.setText("")
    }


    private fun findRestaurant(){
        showLoadingAnimation(true)
        val client=ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : retrofit2.Callback<RestaurantResponse> {

            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                showLoadingAnimation(false)
                if (response.isSuccessful){
                    val responseBody=response.body()
                    if (responseBody!=null){
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }else{
                        Log.d(TAG, "onFailure : ${response.message()}")
                    }

                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                showLoadingAnimation(false)
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
}