package com.matheusxreis.moviedroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.databinding.VideoRowLayoutBinding
import com.matheusxreis.moviedroid.models.Video

class VideosAdapter: RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {

    private var videos: List<Video> = listOf()

    class MyViewHolder(private val binding: VideoRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {
            fun bind(video: Video){
                binding.video = video
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VideoRowLayoutBinding.inflate(layoutInflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentVideo = videos[position]
        holder.bind(currentVideo)
    }

    override fun getItemCount(): Int = videos.size


    fun setData(newVideoList:List<Video>){
        videos = newVideoList
        notifyItemInserted(itemCount)
    }

}