package com.rittmann.robbie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rittmann.widgets.dialog.DialogUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        findViewById<View>(R.id.layout).post {
            DialogUtil().init(this, "Testing ok show", "Title", ok = true, show = true)

            DialogUtil().init(
                this,
                "Testing ok cancelable false",
                "Title",
                ok = true,
                show = true,
                cancelable = false
            )

            DialogUtil().init(
                this,
                "Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text",
                "Title",
                ok = false,
                show = true,
                cancelable = false
            )
        }
    }
}
