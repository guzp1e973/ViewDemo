package com.example.lifeassistant.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.lifeassistant.bean.ContactsInfo;

import android.content.Context;
import android.graphics.Bitmap;

public class ContactsBiz {
	private ContactsDao dao;

	public ContactsBiz(Context context) {
		dao = new ContactsDao(context);
	}

	/**
	 * 获取所有的联系人
	 * 
	 * @return 返回所有的联系人
	 */
	public ArrayList<ContactsInfo> getContacts() {
		return dao.getContacts();
	}

	/**
	 * 获取联系人的所有电话号码
	 * 
	 * @return 返回该联系人的所有电话号码
	 */
	public ArrayList<HashMap<String, Object>> getPhoes(int contact_id) {
		return dao.getPhones(contact_id);
	}

	/**
	 * 获取联系人的所有邮箱号码
	 * 
	 * @return 返回该联系人的所有邮箱号码
	 */
	public ArrayList<HashMap<String, Object>> getEmails(int contact_id) {
		return dao.getEmails(contact_id);
	}
	
	/**
	 * 获取联系人的头像
	 * 
	 * @return 返回该联系人的头像
	 */
	public Bitmap getPhoto(int contact_id) {
		return dao.getPhoto(contact_id);
	}
}
