package com.blackbelt.dogkotlin.view.photo

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.blackbelt.dogkotlin.R
import com.blackbelt.dogkotlin.utils.loadUrl

const val FULL_SCREEN_PHOTO_KEY = "FULL_SCREEN_PHOTO_KEY"

const val FULL_SCREEN_PHOTO_INDEX = "FULL_SCREEN_PHOTO_INDEX"

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        supportPostponeEnterTransition()

        var imageView = findViewById<ImageView>(R.id.full_screen_image)

        ViewCompat.setTransitionName(imageView, intent.getStringExtra(FULL_SCREEN_PHOTO_INDEX));

        imageView.loadUrl(intent.getStringExtra(FULL_SCREEN_PHOTO_KEY)) {

            onSuccess {
                supportStartPostponedEnterTransition()
            }

            onError {
                supportStartPostponedEnterTransition()
            }
        }
    }
}
