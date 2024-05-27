package com.example.practice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.practice.data.Song
import com.example.practice.data.SongEx
import com.example.practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var resultIntent : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Practice)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        onClickButton()
        setResultIntent()

    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fr_main, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_main, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_main, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_main, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fr_main, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }


    private fun setResultIntent(){
        resultIntent = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val returnResult = it.data?.getStringExtra(STRING_INTENT_KEY)
                Toast.makeText(this, returnResult, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClickButton() {
        val songex = SongEx("","",0,60,false)
        with(binding){
            songex.singer = mainMiniplayerSingerTv.text.toString()
            songex.title = mainMiniplayerTitleTv.text.toString()
        }
        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java).apply {
                putExtra("title", songex.title)
                putExtra("singer", songex.singer)
                putExtra("second", songex.second)
                putExtra("isPlaying", songex.isPlaying)
                putExtra("playTime", songex.playTime)
            }
            resultIntent.launch(intent)
        }
    }

    companion object {
        const val STRING_INTENT_KEY = "intent_key"
    }
}

