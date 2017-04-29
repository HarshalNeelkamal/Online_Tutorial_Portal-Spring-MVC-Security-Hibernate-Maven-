package com.neu.getyourcode.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reports {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int report_id;
	private String message;
	private String title;
	private int reportersId;
	private String repoteesUserName;
	private String type;
	private int sectionId;
	
	public Reports() {
		super();
	}

	public Reports(int report_id, String message, String title, int reportersId, String repoteesUserName, String type,
			int sectionId) {
		super();
		this.report_id = report_id;
		this.message = message;
		this.title = title;
		this.reportersId = reportersId;
		this.repoteesUserName = repoteesUserName;
		this.type = type;
		this.sectionId = sectionId;
	}

	public int getReport_id() {
		return report_id;
	}

	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReportersId() {
		return reportersId;
	}

	public void setReportersId(int reportersId) {
		this.reportersId = reportersId;
	}

	public String getRepoteesUserName() {
		return repoteesUserName;
	}

	public void setRepoteesUserName(String repoteesUserName) {
		this.repoteesUserName = repoteesUserName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	@Override
	public String toString() {
		return "Reports [report_id=" + report_id + ", message=" + message + ", title=" + title + ", reportersId="
				+ reportersId + ", repoteesUserName=" + repoteesUserName + ", type=" + type + ", sectionId=" + sectionId
				+ "]";
	}
	
}
