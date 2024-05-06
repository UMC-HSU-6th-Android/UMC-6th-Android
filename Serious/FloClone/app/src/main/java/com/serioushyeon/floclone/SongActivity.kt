package com.serioushyeon.floclone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.serioushyeon.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(true)
        }
        binding.songRepeatOnIv.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songRandomIv.setOnClickListener {
            setRandomStatus(true)
        }
        binding.songRandomOnIv.setOnClickListener {
            setRandomStatus(false)
        }
        binding.songLikeOnIv.setOnClickListener {
            setLikeStatus(false)
        }
        binding.songLikeIv.setOnClickListener {
            setLikeStatus(true)
        }
        binding.songUnlikeOnIv.setOnClickListener {
            setUnlikeStatus(false)
        }
        binding.songUnlikeIv.setOnClickListener {
            setUnlikeStatus(true)
        }

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }

    fun setLikeStatus(isLike: Boolean) {
        if (isLike) {
            binding.songLikeOnIv.visibility = View.VISIBLE
            binding.songLikeIv.visibility = View.GONE
        } else {
            binding.songLikeIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility = View.GONE
        }
    }
    fun setUnlikeStatus(isUnlike : Boolean){
        if(isUnlike){
            binding.songUnlikeOnIv.visibility = View.VISIBLE
            binding.songUnlikeIv.visibility = View.GONE
        }
        else {
            binding.songUnlikeIv.visibility = View.VISIBLE
            binding.songUnlikeOnIv.visibility = View.GONE
        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setRepeatStatus(isRepeating : Boolean){
        if(isRepeating){
            binding.songRepeatOnIv.visibility = View.VISIBLE
            binding.songRepeatIv.visibility = View.GONE
        }
        else {
            binding.songRepeatOnIv.visibility = View.GONE
            binding.songRepeatIv.visibility = View.VISIBLE
        }
    }

    fun setRandomStatus(isRandoming : Boolean){
        if(isRandoming){
            binding.songRandomOnIv.visibility = View.VISIBLE
            binding.songRandomIv.visibility = View.GONE
        }
        else {
            binding.songRandomOnIv.visibility = View.GONE
            binding.songRandomIv.visibility = View.VISIBLE
        }
    }
}