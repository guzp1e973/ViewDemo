package com.example.lifeassistant.bean;

public class DayWeather{
	String temp;
	String weather;
	String week;
	String date_y;
	public String getDate_y() {
		return date_y;
	}
	public void setDate_y(String date_y) {
		this.date_y = date_y;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getWeather() {
		return weather;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
}
