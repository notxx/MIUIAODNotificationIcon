package com.ztc1997.miuiaodnotificationicon

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

// import java.util.List

import top.trumeet.common.cache.IconCache

private const val NOTIFICATION_ICON = "mipush_notification"
private const val NOTIFICATION_SMALL_ICON = "mipush_small_notification"

public class MainActivity : Activity() {
	private lateinit var listView: ListView
	private lateinit var cache: IconCache
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		listView = ListView(this)
		cache = IconCache.getInstance()
		val manager = getPackageManager()
		val packages = manager.getInstalledPackages(0)
		listView.adapter = object : BaseAdapter() {
			override fun getCount(): Int
				= packages.count()

			override fun getItem(position: Int): PackageInfo
				= packages.get(position)

			override fun getItemId(position: Int): Long
				= packages.get(position).hashCode().toLong()

			override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
				// 如果convertView不为空则复用
				val view  = (if (convertView === null) {
					View.inflate(this@MainActivity, R.layout.app_info, null)
				} else {
					convertView
				})

				val info = getItem(position)
				// applicationLabel
				val appName = view.findViewById(R.id.appName) as TextView
				appName.setText(manager.getApplicationLabel(info.applicationInfo))
				// applicationIcon
				val appIcon = view.findViewById(R.id.appIcon) as ImageView
				appIcon.setImageDrawable(manager.getApplicationIcon(info.applicationInfo))
				// applicationLogo
				val appLogo = view.findViewById(R.id.appLogo) as ImageView
				appLogo.setImageDrawable(manager.getApplicationLogo(info.applicationInfo))
				// smallIcon
				val smallIcon = view.findViewById(R.id.smallIcon) as ImageView
				val gen = view.findViewById(R.id.gen) as ImageView
				try {
					val context = createPackageContext(info.packageName, 0)
					var iconId: Int
					iconId = context.getResources().getIdentifier(NOTIFICATION_SMALL_ICON, "drawable", info.packageName)
					if (iconId != 0) { // has icon
						smallIcon.setImageIcon(Icon.createWithResource(info.packageName, iconId))
					} else {
						iconId = context.getResources().getIdentifier(NOTIFICATION_ICON, "drawable", info.packageName)
						if (iconId != 0) // has icon
							smallIcon.setImageIcon(Icon.createWithResource(info.packageName, iconId))
						else
							smallIcon.setImageIcon(null)
					}
					val iconCache = cache.getIconCache(context, info.packageName) { _, b -> Icon.createWithBitmap(b) }
					if (iconCache != null) {
						gen.setImageIcon(iconCache)
						gen.setColorFilter(cache.getAppColor(context, info.packageName) { _, b -> ColorUtil.getColor(b)[0] })
					}
				} catch (ign : IllegalArgumentException) { Log.d("inspect", "ex " + info.packageName); }
				catch (ign : PackageManager.NameNotFoundException) { Log.d("inspect", "ex " + info.packageName); }
				return view
			}
		}
		setContentView(listView)
	}

}
