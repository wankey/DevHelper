package io.github.wankey.mithril.demo.devtool

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import io.github.wankey.mithril.developer.DeveloperTool

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false)
    setContentView(view)
  }

  override fun onDestroy() {
    super.onDestroy()
    DeveloperTool.watch(this)
  }
}
