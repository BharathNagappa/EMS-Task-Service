package com.ems.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ems.config.CustomSequenceGenerator;



@Entity
public class Task implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long taskId;
	public String title;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "task_generator")
	@GenericGenerator(name ="task_generator", strategy = "com.ems.config.CustomSequenceGenerator",
	parameters = {
			@Parameter(name=CustomSequenceGenerator.INCREMENT_PARAM,value="1"),
			@Parameter(name=CustomSequenceGenerator.VALUE_PREFIX_PARAMETER,value="EMS-"),
			@Parameter(name=CustomSequenceGenerator.NUMBER_FORMAT_PARAMETER,value="%2d"),

	}
			)
	public String taskNumber;
	public String description;
	public String status;
	public Date createDate;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch = FetchType.LAZY,mappedBy = "task")
	public List<Comment> comment;
	public Long userId;
	@Transient
	private Map<String, String> additionalProperties;
	
	public long getId() {
		return taskId;
	}
	public void setId(long id) {
		this.taskId = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public Map<String, String> getAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(Map<String, String> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	
}
