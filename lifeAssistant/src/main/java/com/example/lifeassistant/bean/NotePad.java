package com.example.lifeassistant.bean;

public class NotePad {
	private int mID;
	private String mTitle;
	private String mBody;

	public NotePad() {

	}

	public NotePad(int mID, String mTitle, String mBody) {
		this.mID = mID;
		this.mTitle = mTitle;
		this.mBody = mBody;
	}

	public int getmID() {
		return mID;
	}

	public void setmID(int mID) {
		this.mID = mID;
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmBody() {
		return mBody;
	}

	public void setmBody(String mBody) {
		this.mBody = mBody;
	}

	@Override
	public String toString() {
		return "NotePad [mID=" + mID + ", mTitle=" + mTitle + ", mBody="
				+ mBody + "]";
	}

}
