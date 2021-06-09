package com.example.lifeassistant.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather {
	private String city;
	private String week;
	String temp;
	String weather;
	private String date_y;
	private List<DayWeather> evday=new ArrayList<DayWeather>();
	private String index;
	private String wind1;
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate_y() {
		return date_y;
	}
	public void setDate_y(String date_y) {
		this.date_y = date_y;
	}
	
	public List<DayWeather> getEvday() {
		return evday;
	}
	public void setEvday(List<DayWeather> evday) {
		this.evday = evday;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getWind1() {
		return wind1;
	}
	public void setWind1(String wind1) {
		this.wind1 = wind1;
	}
	
	
}
