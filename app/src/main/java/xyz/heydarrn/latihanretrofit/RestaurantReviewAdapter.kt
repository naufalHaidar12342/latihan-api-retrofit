package xyz.heydarrn.latihanretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantReviewAdapter(private val listOfReviews:List<String>) : RecyclerView.Adapter<RestaurantReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val reviewtext:TextView=view.findViewById(R.id.visitor_thoughts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val layoutReview=LayoutInflater.from(parent.context).inflate(R.layout.visitor_review,parent,false)
        return ReviewViewHolder(layoutReview)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.reviewtext.text=listOfReviews[position]
    }

    override fun getItemCount(): Int {
        return listOfReviews.size
    }
}