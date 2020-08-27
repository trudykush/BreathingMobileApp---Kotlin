package com.kush.breathingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import com.github.florent37.viewanimator.ViewAnimator
import com.kush.breathingapplication.util.Prefs
import java.text.MessageFormat

class MainActivity : AppCompatActivity() {

    lateinit var prefs: Prefs
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = Prefs(this)

        breathsTakenTxt.text = MessageFormat.format("{0} min today ", prefs.sessions)
        lastBreathTxt.text = MessageFormat.format("{0} breaths ", prefs.breathe)
        todayMinTxt.text = prefs.date

        startIntroAnimation()

        startBtn.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        ViewAnimator.animate(lotusIV)
            .alpha(0f, 1f)
            .onStart {
                guideTxt.text = "Inhale..Exhale"
            }
            .decelerate()
            .duration(3000)
            .thenAnimate(lotusIV)
            .scale(0.02f, 1.5f, 0.02f)
            .rotation(360f)
            .repeatCount(5)
            .accelerate()
            .duration(5000)
            .onStop {
                guideTxt.text = "Good Job"
                lotusIV.scaleX = 1.0f
                lotusIV.scaleY = 1.0f

                prefs.sessions = prefs.sessions + 1
                prefs.breathe = prefs.breathe + 1
                prefs.setDate(System.currentTimeMillis())

                val handler =  Handler()
                val countDownTimer = Runnable {
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    finish()
                }
                handler.postDelayed(countDownTimer, 100)

            }.start()
    }

    private fun startIntroAnimation() {
        ViewAnimator.animate(guideTxt)
            .scale(0f, 1f)
            .duration(1500)
            .onStart {
                guideTxt.text = "Breathe"
            }.start()
    }
}