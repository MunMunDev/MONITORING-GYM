package com.abcd.monitoring_gym.ui.activity.user.agenda.detail_agenda

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.ProgressModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.databinding.ActivityDetailAgendaBinding
import com.abcd.monitoring_gym.utils.LoadingAlertDialog
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import com.abcd.monitoring_gym.utils.network.UIState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailAgendaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAgendaBinding
    private val viewModel: DetailAgendaViewModel by viewModels()
    private lateinit var progress: ProgressModel
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private var pelatihan = ""
    private var jenisPelatihan = ""
    private var loading: LoadingAlertDialog = LoadingAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAgendaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferencesLogin(this@DetailAgendaActivity)
        setDataSebelumnya()
        setButton()
        getUpdateProgress()
    }

    private fun setButton() {
        binding.apply {
            btnTandai.setOnClickListener {
                postUpdateProgress(progress.id_progress!!)
            }
            myAppBar.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun postUpdateProgress(idProgress: Int) {
        viewModel.postUpdateProgress(idProgress)
    }

    private fun getUpdateProgress(){
        viewModel.getUpdateProgress.observe(this@DetailAgendaActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@DetailAgendaActivity)
                is UIState.Failure-> setFailureUpdateProgress(result.message)
                is UIState.Success-> setSuccessUpdateProgress(result.data)
            }
        }
    }

    private fun setFailureUpdateProgress(message: String) {
        Toast.makeText(this@DetailAgendaActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateProgress(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.response=="0"){
            Toast.makeText(this@DetailAgendaActivity, "Berhasil", Toast.LENGTH_SHORT).show()
            progress.sudah_tercapai = 1
            loadDetailAktivitas(progress, pelatihan, jenisPelatihan)
        } else{
            Toast.makeText(this@DetailAgendaActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataSebelumnya() {
        val i = intent
        if(i!=null){
            progress = i.getParcelableExtra("progress")!!
            pelatihan = i.getStringExtra("pelatihan")!!
            jenisPelatihan = i.getStringExtra("jenis_pelatihan")!!
            loadDetailAktivitas(progress, pelatihan, jenisPelatihan)
            setYoutubVideo(progress.link_youtube!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDetailAktivitas(
        progress: ProgressModel,
        pelatihan: String,
        jenisPelatihan: String
    ) {
        lifecycleScope.launch {
            binding.apply {
                setTopAppBar(pelatihan)
                tvJudul.text = pelatihan
                tvPelatih.text = progress.pelatih?.nama
                tvJenisPelatihan.text = jenisPelatihan
                tvIntruksi.text = progress.intruksi

                if(progress.sudah_tercapai == 0){
                    tvKeterangan.text = "Belum"
                    tvKeterangan.setTextColor(ContextCompat.getColor(this@DetailAgendaActivity, R.color.danger))
                    btnTandai.visibility = View.VISIBLE
                } else{
                    tvKeterangan.text = "Selesai"
                    tvKeterangan.setTextColor(ContextCompat.getColor(this@DetailAgendaActivity, R.color.success))
                    btnTandai.visibility = View.GONE
                }
            }
        }
    }
    private fun setTopAppBar(pelatihan: String) {
        binding.myAppBar.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            tvTitle.text = pelatihan
        }
    }

    private fun setYoutubVideo(videoUrl: String) {
        val videoId = searchIdUrlVideo(videoUrl)
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun searchIdUrlVideo(urlVideo: String): String {
        var url = ""
        try {
            val arrayUrlIdVideo = urlVideo.split("v=")
            url = arrayUrlIdVideo[1]

            try {
                val arraySearchUrlSymbol = url.split("&")
                url = arraySearchUrlSymbol[0]
            } catch (_: Exception){
                url = arrayUrlIdVideo[1]
            }
        } catch (ex: Exception){
            try {
                val arrayUrlIdVideo = urlVideo.split("si=")
                url = arrayUrlIdVideo[1]
            } catch (ex: Exception){
                url = "0"
            }
        }
        return url
    }
}