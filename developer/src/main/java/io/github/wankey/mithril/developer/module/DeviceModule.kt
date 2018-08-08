package io.github.wankey.mithril.developer.module

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.wankey.mithril.developer.R


/**
 * @author wankey
 */
class DeviceModule : DeveloperModule {
  lateinit var tvMake: TextView
  lateinit var tvModel: TextView
  lateinit var tvResolution: TextView
  lateinit var tvDensity: TextView
  lateinit var tvRelease: TextView
  lateinit var tvApi: TextView
  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_device_info, parent, true)
    tvMake = view.findViewById(R.id.tv_make)
    tvModel = view.findViewById(R.id.tv_model)
    tvResolution = view.findViewById(R.id.tv_resolution)
    tvDensity = view.findViewById(R.id.tv_density)
    tvRelease = view.findViewById(R.id.tv_release)
    tvApi = view.findViewById(R.id.tv_api)
    refresh(view.context)
    return view
  }

  override fun onOpened() {
  }

  override fun onClosed() {
  }

  override fun onResume() {
  }

  override fun onPause() {
  }

  override fun onStart() {
  }

  override fun onStop() {
  }

  @SuppressLint("SetTextI18n")
  private fun refresh(context: Context) {
    tvMake.text = Build.MANUFACTURER
    tvModel.text = Build.MODEL
    tvResolution.text = "${context.resources.displayMetrics.widthPixels}x${context.resources.displayMetrics.heightPixels}"
    tvDensity.text = "${context.resources.displayMetrics.densityDpi}dpi(${getDensityString(context.resources.displayMetrics)})"
    tvRelease.text = Build.VERSION.RELEASE
    tvApi.text = Build.VERSION.SDK_INT.toString()
  }

  private fun getDensityString(displayMetrics: DisplayMetrics): String {
    return when (displayMetrics.densityDpi) {
      DisplayMetrics.DENSITY_LOW -> "ldpi"
      DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
      DisplayMetrics.DENSITY_HIGH -> "hdpi"
      DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
      DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
      DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
      DisplayMetrics.DENSITY_TV -> "tvdpi"
      else -> displayMetrics.densityDpi.toString()
    }
  }
}