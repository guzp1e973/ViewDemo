package com.example.lifeassistant.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.lifeassistant.bean.ContactsInfo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

public class ContactsDao {
	ContentResolver cr;

	public ContactsDao(Context context) {
		this.cr = context.getContentResolver();
	}

	/**
	 * 查询所有联系人信息 并返回
	 * 
	 * @return
	 */
	public ArrayList<ContactsInfo> getContacts() {
		ArrayList<ContactsInfo> infos = null;
		// 查询出所有联系人的 主键id以及name 并且按照名字进行排序
		Cursor c = cr.query(Contacts.CONTENT_URI, new String[] { Contacts._ID,
				Contacts.DISPLAY_NAME }, null, null, Contacts.DISPLAY_NAME);
		if (c != null) {
			infos = new ArrayList<ContactsInfo>();// 所有联系人的集合
			while (c.moveToNext()) {
				ContactsInfo info = new ContactsInfo();// 表示一个联系人对象
				info.setId(c.getInt(c.getColumnIndex(Contacts._ID)));// Id
				info.setName(c.getString(c
						.getColumnIndex(Contacts.DISPLAY_NAME)));// 名字
				infos.add(info);// 把查询到的人加入集合
			}
			c.close();
		}

		return infos;
	}
	/**
	 * 根据每个联系人的id 查询并返回他所有的电话号码
	 * @param contact_id 联系人id
	 * @return	返回该联系人的所有的电话号码
	 */
	public ArrayList<HashMap<String, Object>> getPhones(int contact_id){
		ArrayList<HashMap<String, Object>> data = null;
		Cursor c = cr.query(Phone.CONTENT_URI, new String[]{Phone.NUMBER,Phone.TYPE}, Phone.CONTACT_ID+"="+contact_id, null, null);
		if (c!=null&&c.getCount()>0) {//如果这个人有电话号码
			data = new ArrayList<HashMap<String,Object>>();
			while (c.moveToNext()) {
				HashMap<String, Object> phoneInfo = new HashMap<String, Object>();
				int type = c.getInt(c.getColumnIndex(Phone.TYPE));
				if (type==Phone.TYPE_HOME) {
					phoneInfo.put("type", "家庭号码：");
				}else if (type==Phone.TYPE_MOBILE) {
					phoneInfo.put("type", "手机：");
				}else {
					phoneInfo.put("type", "其它号码：");
				}
				phoneInfo.put("data", c.getString(c.getColumnIndex(Phone.NUMBER)));
				data.add(phoneInfo);
			}
			c.close();
		}
		return data;
	}
	/**
	 * 根据联系人的id 查询Email信息并返回
	 * @param contact_id 联系人的id
	 * @return	返回该联系人的所有的邮箱
	 */
	public ArrayList<HashMap<String, Object>> getEmails(int contact_id){
		ArrayList<HashMap<String, Object>> data = null;
		Cursor c = cr.query(Email.CONTENT_URI, new String[]{Email.DATA,Email.TYPE}, Email.CONTACT_ID+"="+contact_id, null, null);
		if (c!=null&&c.getCount()>0) {//如果这个人有邮箱}
			data = new ArrayList<HashMap<String,Object>>();
			while (c.moveToNext()) {
				HashMap<String, Object> emailInfo = new HashMap<String, Object>();
				int type = c.getInt(c.getColumnIndex(Email.TYPE));
				if (type==Email.TYPE_HOME) {
					emailInfo.put("type", "家庭邮箱：");
				}else if (type==Email.TYPE_MOBILE) {
					emailInfo.put("type", "工作邮箱：");
				}else {
					emailInfo.put("type", "其它邮箱：");
				}
				emailInfo.put("data", c.getString(c.getColumnIndex(Email.DATA)));
				data.add(emailInfo);
			}
			c.close();
		}
		return data;
	}
	
	/**
	 * 根据联系人的id 查询出这个联系人的头像
	 * @param contact_id 该联系人的id
	 * @return 该联系人的头像
	 */
	public Bitmap getPhoto(int contact_id){
		Bitmap bm = null;
		Uri uri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contact_id);
		InputStream is = Contacts.openContactPhotoInputStream(cr, uri);
		bm = BitmapFactory.decodeStream(is);
		return bm;
	}
	
}
