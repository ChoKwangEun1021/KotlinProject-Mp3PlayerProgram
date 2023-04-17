package com.example.mp3playerondbpro

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3playerondbpro.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter(val context: Context, val musicList: MutableList<MusicData>) :
    RecyclerView.Adapter<MusicRecyclerAdapter.CustomViewHolder>() {
    val ALBUM_IMAGE_SIZE = 90

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        //이미지, artist, title, duration binding
        val bitmap = musicList.get(position).getAlbumBitmap(context, ALBUM_IMAGE_SIZE)
        if (bitmap != null) {
            binding.imageAlbum.setImageBitmap(bitmap)
        } else {
            binding.imageAlbum.setImageResource(R.drawable.music_player)
        }
        binding.textArtist.text = musicList.get(position).artist
        binding.textTitle.text = musicList.get(position).title
        binding.textDuration.text =
            SimpleDateFormat("mm:ss").format(musicList.get(position).duration)
        when (musicList.get(position).likes) {
            0 -> binding.ivItemLike.setImageResource(R.drawable.favorite_24)
            1 -> binding.ivItemLike.setImageResource(R.drawable.favorite_like_24)
        }

        //아이템항목 클릭시 PlayActivity MusicData 전달
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, PlayActivity::class.java)
            val parcelableList: ArrayList<Parcelable>? = musicList as ArrayList<Parcelable>
            intent.putExtra("parcelableList", parcelableList)
            intent.putExtra("position", position)
            binding.root.context.startActivity(intent)
        }

        binding.ivItemLike.setOnClickListener {
            when (musicList.get(position).likes) {
                0 -> {
                    musicList.get(position).likes = 1
                    binding.ivItemLike.setImageResource(R.drawable.favorite_like_24)
                }
                1 -> {
                    musicList.get(position).likes = 0
                    binding.ivItemLike.setImageResource(R.drawable.favorite_24)
                }
            }
            val db = DBOpenHelper(context, MainActivity.DB_NAME, MainActivity.VERSION)
            db.updateLike(musicList.get(position))
            var errorFlag = db.updateLike(musicList.get(position))
            if (errorFlag) {
                Toast.makeText(context, "updateLike 실패", Toast.LENGTH_SHORT).show()
            } else {
                this.notifyDataSetChanged()
            }
        }
    }

    inner class CustomViewHolder(val binding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root)
}