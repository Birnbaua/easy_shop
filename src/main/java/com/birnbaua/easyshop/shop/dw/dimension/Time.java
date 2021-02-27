package com.birnbaua.easyshop.shop.dw.dimension;

import java.time.LocalDateTime;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_dt")
public class Time {
	
	@Id
	@Column(name = "sk")
	private Long sk;
	
	@Column(name = "hour")
	private Byte hour;
	
	@Column(name = "weekday")
	private Byte weekday;
	
	@Column(name = "week")
	private Byte week;
	
	@Column(name = "month")
	private Byte month;
	
	@Enumerated(EnumType.STRING)
	private Quarter quarter;
	
	@Column(name = "year")
	private Integer year;

	public Time() {
		
	}
	
	public Time(LocalDateTime time) {
		this.year = time.getYear();
		this.month = (byte) time.getMonthValue();
		Calendar c = Calendar.getInstance();
		c.set(year, month,time.getDayOfMonth());
		this.week = (byte) c.get(Calendar.WEEK_OF_YEAR);
		this.weekday = (byte) time.getDayOfWeek().getValue();
		this.hour = (byte) time.getHour();
		this.quarter = Quarter.of(month);
		this.sk = this.year*10000000L + this.month*100000L + this.week*1000L + this.weekday*100L + this.hour;  
	}

	public Long getSk() {
		return sk;
	}

	public void setSk(Long sk) {
		this.sk = sk;
	}

	public Byte getHour() {
		return hour;
	}

	public void setHour(Byte hour) {
		this.hour = hour;
	}

	public Byte getWeekday() {
		return weekday;
	}

	public void setWeekday(Byte weekday) {
		this.weekday = weekday;
	}

	public Byte getWeek() {
		return week;
	}

	public void setWeek(Byte week) {
		this.week = week;
	}

	public Byte getMonth() {
		return month;
	}

	public void setMonth(Byte month) {
		this.month = month;
	}

	public Quarter getQuarter() {
		return quarter;
	}

	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
