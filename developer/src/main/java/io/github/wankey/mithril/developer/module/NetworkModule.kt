package io.github.wankey.mithril.developer.module

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wankey.mithril.developer.R
import io.github.wankey.mithril.developer.module.NetworkController.NetworkChangeEvent
import io.github.wankey.mithril.developer.module.NetworkController.OnNetworkChangedListener
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationTargetException


/**
 * @author wankey
 */
class NetworkModule : DeveloperModule {
  private lateinit var context: Context
  private lateinit var switchWifi: Switch
  private lateinit var switchMobile: Switch
  private lateinit var switchBluetooth: Switch
  private lateinit var switchAirMode: Switch

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.partial_network_info, parent, true)
    switchWifi = view.findViewById(R.id.switch_wifi)
    switchMobile = view.findViewById(R.id.switch_mobile)
    switchBluetooth = view.findViewById(R.id.switch_bluetooth)
    switchAirMode = view.findViewById(R.id.switch_air_mode)
    switchAirMode.isEnabled = false//权限问题，无法修改
    context = view.context

    NetworkController.contextRef = WeakReference(context)

    val rxPermission = RxPermissions(view.context as Activity)
    val dispose = rxPermission.requestEach(android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.CHANGE_WIFI_STATE,
        android.Manifest.permission.BLUETOOTH, android.Manifest.permission.BLUETOOTH_ADMIN)
        .subscribe({ it ->
          run {
            when {
              it.granted -> refresh()

              else -> {
                view.visibility = View.GONE
              }
            }
          }
        }, {
          view.visibility = View.GONE
        })
    return view
  }

  private fun refresh() {

    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    switchWifi.isChecked = wifiManager.isWifiEnabled
    switchWifi.setOnCheckedChangeListener { _, isChecked -> wifiManager.isWifiEnabled = isChecked }

    switchMobile.isChecked = isMobileNetworkEnabled(connectivityManager)
    switchMobile.setOnCheckedChangeListener { _, isChecked -> setMobileNetworkEnabled(connectivityManager, isChecked) }

    if (bluetoothAdapter != null) {
      switchBluetooth.isChecked = hasBluetoothPermission(context) && bluetoothAdapter.isEnabled
      switchBluetooth.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
          bluetoothAdapter.enable()
        } else {
          bluetoothAdapter.disable()
        }
      }
    } else {
      switchBluetooth.isEnabled = false
    }

    switchAirMode.isChecked = isAirModeEnabled()
    switchAirMode.setOnCheckedChangeListener { _, isChecked ->
      run {
        setAirMode(isChecked)

      }
    }
  }

  private fun setAirMode(checked: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.System.canWrite(context)) {
        val intent = Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return
      }
    }
    val value = when (checked) {true -> 1
      false -> 0
    }

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
      Settings.System.putInt(context.contentResolver, Settings.System.AIRPLANE_MODE_ON, value)
    } else {
      Settings.Global.putInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, value)
    }
    val intent = Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED)
    intent.putExtra("state", checked)
    context.sendBroadcast(intent)
  }

  private fun isAirModeEnabled(): Boolean {
    val isAirplaneMode = Settings.System.getInt(context.contentResolver, Settings.System.AIRPLANE_MODE_ON, 0)
    return isAirplaneMode == 1
  }

  override fun onOpened() {
  }

  override fun onClosed() {
  }

  override fun onResume() {

  }

  override fun onPause() {
  }

  override fun onStop() {
    NetworkController.onNetworkChangedListener = null
    NetworkController.unregisterReceiver()
  }

  override fun onStart() {
    NetworkController.onNetworkChangedListener = object : OnNetworkChangedListener {
      override fun onChanged(event: NetworkChangeEvent) {
        refresh()
      }
    }
    NetworkController.registerReceiver()
  }

  /**
   * True if mobile network enabled
   */
  private fun isMobileNetworkEnabled(connectivityManager: ConnectivityManager): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val allNetworks = connectivityManager.allNetworks
      for (network in allNetworks) {
        val networkInfo = connectivityManager.getNetworkInfo(network)
        if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
          return networkInfo.isConnected
        }
      }
    } else {
      val info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
      return info != null && info.isConnected
    }
    return false
  }

  /**
   * http://stackoverflow.com/questions/11555366/enable-disable-data-connection-in-android-programmatically
   * Try to enabled/disable mobile network state using reflection.
   * Returns true if succeeded
   *
   * @param enabled
   */
  private fun setMobileNetworkEnabled(connectivityManager: ConnectivityManager, enabled: Boolean): Boolean {
    try {
      val conmanClass = Class.forName(connectivityManager.javaClass.name)
      val iConnectivityManagerField = conmanClass.getDeclaredField("mService")
      iConnectivityManagerField.isAccessible = true
      val iConnectivityManager = iConnectivityManagerField.get(connectivityManager)
      val iConnectivityManagerClass = Class.forName(iConnectivityManager.javaClass.name)
      val setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", java.lang.Boolean.TYPE)
      setMobileDataEnabledMethod.isAccessible = true
      setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled)
      return true
    } catch (e: ClassNotFoundException) {
    } catch (e: InvocationTargetException) {
    } catch (e: NoSuchMethodException) {
    } catch (e: IllegalAccessException) {
    } catch (e: NoSuchFieldException) {
    }

    return false
  }

  private fun hasBluetoothPermission(context: Context) = context.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED

}