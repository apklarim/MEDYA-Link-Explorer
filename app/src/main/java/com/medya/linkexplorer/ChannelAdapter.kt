package com.medya.linkexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChannelAdapter(
    private val channels: List<M3uChannel>
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    inner class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtChannelName: TextView = view.findViewById(R.id.txtChannelName)
        val txtChannelGroup: TextView = view.findViewById(R.id.txtChannelGroup)
        val txtChannelUrl: TextView = view.findViewById(R.id.txtChannelUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]
        holder.txtChannelName.text = channel.name
        holder.txtChannelGroup.text = if (channel.group.isBlank()) "Grup: -" else "Grup: ${channel.group}"
        holder.txtChannelUrl.text = channel.url
    }

    override fun getItemCount(): Int = channels.size
}
