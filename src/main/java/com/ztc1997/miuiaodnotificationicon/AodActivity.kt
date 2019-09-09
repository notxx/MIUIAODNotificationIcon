package com.ztc1997.miuiaodnotificationicon

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

import java.io.DataOutputStream


data class Notification(val _id: Int, val icon: Bitmap?, val title: String, val content: String, val time: String, val info: String, val subtext: String, val key: Int, val pkg: String, val user_id: Int)

fun ByteArray.toBitmap(): Bitmap {
	return BitmapFactory.decodeByteArray(this, 0, this.size)
}

public class AodActivity : Activity() {
	
	override fun onResume() {
		super.onResume()

		val URI = Uri.parse("content://keyguard.notification/notifications");
		val query = this.getContentResolver().query(URI, arrayOf("*"), null, null, null)
		Log.d("inspect0", "${query?.getCount()}")
		val list = ArrayList<Notification>()
		if (query != null) {
			val index = query.getColumnIndex("_id")
			while (query.moveToNext()) {
				val n = Notification(
					_id = query.getInt(index),
					icon = query.getBlob(1)?.toBitmap(),
					title = query.getString(2),
					content = query.getString(3),
					time = query.getString(4),
					info = query.getString(5),
					subtext = query.getString(6),
					key = query.getInt(7),
					pkg = query.getString(8),
					user_id = query.getInt(9)
				)
				list.add(n)
				for (i in 0..9) {
					when (query.getType(i)) {
					0 -> Log.d("inspect0", "$i ${query.getColumnName(i)} NULL")
					1 -> Log.d("inspect0", "$i int ${query.getColumnName(i)} ${query.getInt(i)}")
					2 -> Log.d("inspect0", "$i float ${query.getColumnName(i)} ${query.getFloat(i)}")
					3 -> Log.d("inspect0", "$i string ${query.getColumnName(i)} ${query.getString(i)}")
					4 -> Log.d("inspect0", "$i blob ${query.getColumnName(i)} ${query.getBlob(i)}")
					}
				}
			}
		}

		val listView = ListView(this)
		listView.adapter = object : BaseAdapter() {
			override fun getCount(): Int
				= list.count()

			override fun getItem(position: Int): Notification
				= list.get(position)

			override fun getItemId(position: Int): Long
				= getItem(position).hashCode().toLong()

			override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
				// 如果convertView不为空则复用
				val view  = (if (convertView === null) {
					View.inflate(this@AodActivity, R.layout.aod_info, null)
				} else {
					convertView
				})

				val n = getItem(position)
				(view.findViewById(R.id.id) as? TextView)?.text = n._id.toString()
				(view.findViewById(R.id.icon) as? ImageView)?.setImageBitmap(n.icon)
				Log.d("inspect0", "icon: ${n.icon}")
				(view.findViewById(R.id.title) as? TextView)?.text = n.title
				(view.findViewById(R.id.content) as? TextView)?.text = n.content
				(view.findViewById(R.id.time) as? TextView)?.text = n.time
				(view.findViewById(R.id.info) as? TextView)?.text = n.info
				(view.findViewById(R.id.subtext) as? TextView)?.text = n.subtext
				(view.findViewById(R.id.key) as? TextView)?.text = n.key.toString()
				(view.findViewById(R.id.pkg) as? TextView)?.text = n.pkg
				(view.findViewById(R.id.user_id) as? TextView)?.text = n.user_id.toString()
				return view
			}
		}
		setContentView(listView)
	}

}
