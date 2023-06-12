package com.example.filmmodu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmmodu.databinding.MovieListItemBinding
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.ui.fragment.MovieDetailFragment

class RecyclerViewAdapter(
    val onItemClicked: (MovieModel) -> Unit,
    var modelArraylist:List<MovieModel>,
    var context: Context): RecyclerView.Adapter<ReciyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciyclerViewHolder {
        val mBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReciyclerViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ReciyclerViewHolder, position: Int) {
        holder.list_item_binding.tvMovieName.text = modelArraylist.get(position).movie_name

        Glide.with(context).load(modelArraylist[position].movie_poster).into(holder.list_item_binding.ivMoviePoster)


        holder.itemView.setOnClickListener {
            onItemClicked(modelArraylist[position])
//            Toast.makeText(holder.itemView.context,modelArraylist.get(position).movie_imdb_id,Toast.LENGTH_SHORT).show()
//
//            // Fragment'ler arası veri transferi ve geçiş yapmak için kullanılabilir.
//            val selectedMovie = modelArraylist.get(position)
//            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(selectedMovie = selectedMovie)
//            Navigation.findNavController(it).navigate(action)


            //Doğrudan geçiş Yapmak için Kullanılabilir
            //Navigation.findNavController(it).navigate(R.id.countryFragment)
        }
    }

    override fun getItemCount(): Int {
        return modelArraylist.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<MovieModel>) {
        modelArraylist = newData
        notifyDataSetChanged()
    }
}

class ReciyclerViewHolder(var list_item_binding: MovieListItemBinding):RecyclerView.ViewHolder(list_item_binding.root){
}