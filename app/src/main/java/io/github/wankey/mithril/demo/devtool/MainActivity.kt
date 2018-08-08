package io.github.wankey.mithril.demo.devtool

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import io.github.wankey.mithril.developer.ui.DeveloperViewModifier

class MainActivity : AppCompatActivity() {
  private val viewModify = DeveloperViewModifier()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val view = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
    setContentView(viewModify.modify(view))
  }
}
