package io.github.wankey.mithril.developer.module

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import io.github.wankey.mithril.developer.R


/**
 * @author wankey
 */
class SettingModule : DeveloperModule, OnClickListener {

  private lateinit var context: Context

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_setting, parent, true)
    context = view.context
    (view.findViewById(R.id.iv_developer) as ImageView).setOnClickListener(this)
    (view.findViewById(R.id.iv_battery) as ImageView).setOnClickListener(this)
    (view.findViewById(R.id.iv_location) as ImageView).setOnClickListener(this)
    (view.findViewById(R.id.iv_setting) as ImageView).setOnClickListener(this)
    (view.findViewById(R.id.iv_app_info) as ImageView).setOnClickListener(this)
    (view.findViewById(R.id.iv_uninstall) as ImageView).setOnClickListener(this)
    return view
  }

  override fun onClick(view: View?) {
    when (view?.id) {
      R.id.iv_developer -> openDeveloperSetting()
      R.id.iv_battery -> openBatterySetting()
      R.id.iv_location -> openLocationSetting()
      R.id.iv_setting -> openSetting()
      R.id.iv_app_info -> openAppInfo()
      R.id.iv_uninstall -> uninstall()
    }
  }

  /**
   * 卸载app
   */
  private fun uninstall() {
    val packageURI = Uri.parse("package:" + context.packageName)
    val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
    context.startActivity(uninstallIntent)
  }

  /**
   * 打开应用信息界面
   */
  private fun openAppInfo() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.data = Uri.parse("package:" + context.packageName)
    context.startActivity(intent)
  }

  /**
   * 打开设置界面
   */
  private fun openSetting() {
    val settingsIntent = Intent(Settings.ACTION_SETTINGS)
    settingsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(settingsIntent)
  }

  /**
   * 打开位置界面
   */
  private fun openLocationSetting() {
    val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    locationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(locationIntent)
  }

  /**
   * 打开电池界面
   */
  private fun openBatterySetting() {
    val batteryIntent = Intent(Intent.ACTION_POWER_USAGE_SUMMARY)
    batteryIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    val resolveInfo = context.packageManager.resolveActivity(batteryIntent, 0)
    if (resolveInfo != null) {
      context.startActivity(batteryIntent)
    } else {
      Toast.makeText(context, "No app found to handle power usage intent", Toast.LENGTH_SHORT).show()
    }
  }

  /**
   * 打开开发者选项界面
   */
  private fun openDeveloperSetting() {
    val devIntent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
    devIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    val resolveInfo = context.packageManager.resolveActivity(devIntent, 0)
    if (resolveInfo != null) {
      context.startActivity(devIntent)
    } else {
      Toast.makeText(context, "Developer settings not available on device", Toast.LENGTH_SHORT).show()

      val deviceInfoIntent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
      deviceInfoIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(deviceInfoIntent)

    }
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
}