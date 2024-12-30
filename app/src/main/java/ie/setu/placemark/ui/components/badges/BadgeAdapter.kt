package ie.setu.placemark.ui.components.badges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ie.setu.placemark.R

class BadgeAdapter(private val badges: List<Badge>) :
    RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {

    class BadgeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val badgeImage: ImageView = view.findViewById(R.id.imageViewBadge)
        val badgeName: TextView = view.findViewById(R.id.textViewBadgeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_badge, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = badges[position]
        holder.badgeName.text = badge.name
        holder.badgeImage.setImageResource(badge.imageRes)
    }

    override fun getItemCount(): Int = badges.size
}
