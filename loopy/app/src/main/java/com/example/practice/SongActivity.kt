package com.example.practice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.data.SongEx
import com.example.practice.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongBinding
    lateinit var songex: SongEx
    lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickBackButton()
        bringIntentData()
        setPlayer()
    }


    private fun bringIntentData() {
        with(intent) {
            songex = SongEx(
                singer = getStringExtra("singer").toString(),
                title = getStringExtra("title").toString(),
                isPlaying = getBooleanExtra("isPlaying", false),
                playTime = getIntExtra("playTime", 0),
                second = getIntExtra("second", 0)
            )
        }
        startTimer()

    }

    private fun setPlayer() {
        with(binding) {
            with(songex) {
                songTitleTv.text = title
                songSingerTv.text = singer
                songStartTomeTv.text =
                    String.format("%02d:%02d", songex.second / 60, songex.second % 60)
                songEndTimeTv.text =
                    String.format("%02d:%02d", songex.playTime / 60, songex.playTime % 60)
                songPlayLineMl.progress = (songex.second * 1000 / songex.playTime)
                playSongState(songex.isPlaying)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun playSongState(state: Boolean) {
        songex.isPlaying = state
        timer.isPlaying = state
        with(binding) {
            if (state) {
                songPauseBt.visibility = View.VISIBLE
                songPlayBt.visibility = View.GONE
            } else {
                songPlayBt.visibility = View.VISIBLE
                songPauseBt.visibility = View.GONE
            }
        }
    }

    private fun startTimer() {
        timer = Timer(songex.playTime, songex.isPlaying)
        timer.start()
    }

    private fun playLikeState(state: Boolean) {
        with(binding) {
            if (state) {
                songLikeOnBtn.visibility = View.VISIBLE
                songLikeBtn.visibility = View.GONE
            } else {
                songLikeBtn.visibility = View.VISIBLE
                songLikeOnBtn.visibility = View.GONE
            }
        }
    }

    private fun playUnLikeState(state: Boolean) {
        with(binding) {
            if (state) {
                songUnlikeOnBtn.visibility = View.VISIBLE
                songUnlikeBtn.visibility = View.GONE
            } else {
                songUnlikeBtn.visibility = View.VISIBLE
                songUnlikeOnBtn.visibility = View.GONE
            }
        }
    }

    private fun onClickBackButton() {
        with(binding) {
            songDropButtonIbt.setOnClickListener {
                val intent = Intent(this@SongActivity, MainActivity::class.java).apply {
                    putExtra(MainActivity.STRING_INTENT_KEY, binding.songTitleTv.text.toString())
                }
                setResult(Activity.RESULT_OK, intent)
                if (!isFinishing)
                    finish()
            }

            songPlayBt.setOnClickListener {
                playSongState(true)
            }
            songPauseBt.setOnClickListener {
                playSongState(false)
            }
            songUnlikeBtn.setOnClickListener {
                playUnLikeState(true)
            }
            songUnlikeOnBtn.setOnClickListener {
                playUnLikeState(false)
            }
            songLikeBtn.setOnClickListener {
                playLikeState(true)
            }
            songLikeOnBtn.setOnClickListener {
                playLikeState(false)
            }


        }
    }

    inner class Timer(private val playTIme: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTIme)
                        break

                    if (isPlaying) {
                        sleep(50)
                        Log.d("Song", "$mills")
                        mills += 50
                        runOnUiThread {
                            binding.songPlayLineMl.progress = ((mills / playTIme) * 100).toInt()
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTomeTv.text =
                                    String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. + ${e.message}")
            }

        }
    }
}