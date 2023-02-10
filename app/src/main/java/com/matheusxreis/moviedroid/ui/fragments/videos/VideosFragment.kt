package com.matheusxreis.moviedroid.ui.fragments.videos

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.adapters.VideosAdapter
import com.matheusxreis.moviedroid.models.Video
import kotlinx.android.synthetic.main.fragment_videos.*

class VideosFragment : Fragment() {

    private val args by navArgs<VideosFragmentArgs>()
    private val videosAdapter by lazy {
        VideosAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setVideo()

        setUpRecyclerView()
        populateRecyclerView()
    }

    override fun onDestroy() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onDestroy()

    }


    // CUSTOM FUNCTIONS

    private fun setUpRecyclerView(){
        videosRecyclerView.adapter = videosAdapter
        videosRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
    private fun populateRecyclerView(){
        val mockVideos = listOf(
            Video(key="AkFqg5wAuFk", from="youtube", title="title"),
            Video(key="AkFqg5wAuFk", from="youtube", title="title"),
            Video(key="AkFqg5wAuFk", from="youtube", title="title"),
            Video(key="AkFqg5wAuFk", from="youtube", title="title"),
            Video(key="AkFqg5wAuFk", from="youtube", title="title"),
            Video(key="AkFqg5wAuFk", from="youtube", title="title")
        )

        videosAdapter.setData(mockVideos)
    }
    private fun setVideo() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }


}