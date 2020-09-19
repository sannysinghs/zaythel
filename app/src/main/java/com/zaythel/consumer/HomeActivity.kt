package com.zaythel.consumer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zaythel.consumer.ui.product.ProductFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ProductFragment.newInstance(2))
            .commit()

    }
}
