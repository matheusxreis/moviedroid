import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.moviedroid.databinding.MoviesRowLayoutBinding
import com.matheusxreis.moviedroid.models.MoviePoster


class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private var moviePosters = listOf<MoviePoster>()

    class MyViewHolder(val binding: MoviesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moviePoster: MoviePoster) {
            binding.moviePoster = moviePoster
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MoviesRowLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = moviePosters[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int = moviePosters.size

    fun setData(newMoviePostes: List<MoviePoster>) {
        moviePosters = newMoviePostes
        notifyItemInserted(itemCount)
    }

}