package com.abcd.monitoring_gym.ui.activity.user.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.databinding.ActivityMainBinding
import com.abcd.monitoring_gym.ui.fragment.user.home.HomeFragment
import com.abcd.monitoring_gym.ui.fragment.user.pelatihan.PelatihanFragment
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private lateinit var scaleAnimation: ScaleAnimation
    private var checkFragmentPosition = 0   // 0 Home, 1 pelatihan, 2 agenda, 3 account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurationSharedPreferences()
        setScaleAnimation()
        setFragment(HomeFragment())
        setButtonBottomBar()

    }


    private fun configurationSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@MainActivity)
    }

    private fun setButtonBottomBar() {
        binding.icBottom.apply {
            btnHome.setOnClickListener {
                clickHome()
            }
            btnPelatihan.setOnClickListener {
                clickPelatihan()
            }
            btnAgenda.setOnClickListener {
                clickAgenda()
            }
            btnAccount.setOnClickListener {
                clickAccount()
            }
        }
    }

    private fun clickHome() {
        binding.icBottom.apply {
            // text color
            tvHome.setTextColor(resources.getColor(R.color.primaryColor))
            tvPelatihan.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAgenda.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAccount.setTextColor(resources.getColor(R.color.textColorBlack))

            // image view visibility
            ivHome.setImageResource(R.drawable.ic_home_active)
            ivPelatihan.setImageResource(R.drawable.ic_pelatihan)
            ivAgenda.setImageResource(R.drawable.ic_agenda)
            ivAccount.setImageResource(R.drawable.ic_akun)

            setFragment(HomeFragment())
            checkFragmentPosition = 0
        }
    }

    fun clickPelatihan() {
        binding.icBottom.apply {
            // text color
            tvHome.setTextColor(resources.getColor(R.color.textColorBlack))
            tvPelatihan.setTextColor(resources.getColor(R.color.primaryColor))
            tvAgenda.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAccount.setTextColor(resources.getColor(R.color.textColorBlack))

            // image view visibility
            ivHome.setImageResource(R.drawable.ic_home)
            ivPelatihan.setImageResource(R.drawable.ic_pelatihan_active)
            ivAgenda.setImageResource(R.drawable.ic_agenda)
            ivAccount.setImageResource(R.drawable.ic_akun)

            setFragment(PelatihanFragment())
            checkFragmentPosition = 1
        }
    }

    fun clickAgenda() {
        binding.icBottom.apply {
            // text color
            tvHome.setTextColor(resources.getColor(R.color.textColorBlack))
            tvPelatihan.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAgenda.setTextColor(resources.getColor(R.color.primaryColor))
            tvAccount.setTextColor(resources.getColor(R.color.textColorBlack))

            // image view visibility
            ivHome.setImageResource(R.drawable.ic_home)
            ivPelatihan.setImageResource(R.drawable.ic_pelatihan)
            ivAgenda.setImageResource(R.drawable.ic_agenda_active)
            ivAccount.setImageResource(R.drawable.ic_akun)

//            setFragment(AgendaFragment())
            checkFragmentPosition = 2
        }
    }

    fun clickAccount() {
        binding.icBottom.apply {
            // text color
            tvHome.setTextColor(resources.getColor(R.color.textColorBlack))
            tvPelatihan.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAgenda.setTextColor(resources.getColor(R.color.textColorBlack))
            tvAccount.setTextColor(resources.getColor(R.color.primaryColor))

            // image view visibility
            ivHome.setImageResource(R.drawable.ic_home)
            ivPelatihan.setImageResource(R.drawable.ic_pelatihan)
            ivAgenda.setImageResource(R.drawable.ic_agenda)
            ivAccount.setImageResource(R.drawable.ic_akun_active)

//            setFragment(ProfileFragment())
            checkFragmentPosition = 3
        }
    }

    @SuppressLint("CommitTransaction")
    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.flMain, fragment)
            commit()
        }
    }

    private fun setScaleAnimation(){
        scaleAnimation = ScaleAnimation(
            0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        )
        scaleAnimation.apply {
            duration = 200
            fillAfter = true
        }
    }

    private var tapDuaKali = false
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(checkFragmentPosition == 0){
            if (tapDuaKali){
                super.onBackPressed()
            }
            tapDuaKali = true
            Toast.makeText(this@MainActivity, "Tekan Sekali Lagi untuk keluar", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                tapDuaKali = false
            }, 2000)
        } else{
            clickHome()
        }
    }
}