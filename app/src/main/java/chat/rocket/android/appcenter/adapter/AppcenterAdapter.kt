package chat.rocket.android.appcenter.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import chat.rocket.android.R
import chat.rocket.android.util.extensions.inflate
import chat.rocket.android.util.extensions.openTabbedUrl
import chat.rocket.common.model.Game
import kotlinx.android.synthetic.main.item_app.view.*

class AppcenterAdapter(
       private var games: List<Game>
) : RecyclerView.Adapter<AppcenterAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_app))
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun clearData() {
        games = emptyList()
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    class ViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            with(itemView) {
                image_app.setImageURI(game.icon)
                text_app.text = game.name
                text_app_description.text = game.description
                setOnClickListener {
                    it.openTabbedUrl(game.url)
                }
            }
        }
    }
}