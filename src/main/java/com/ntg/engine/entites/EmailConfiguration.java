package com.ntg.engine.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "adm_Email_Configuration")
public class EmailConfiguration {

	@Id
	@SequenceGenerator(allocationSize = 1, name = "adm_email_Configuration_s", sequenceName = "dm_email_Configuration_s", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_email_Configuration_s")
	@Column(name = "recid", nullable = false)
	
	private Long recId;
	
	
	@Column(name = "host")
	private  String host;
	
	
	@Column(name = "type_Id", nullable = false)
	private Long typeId;

	
	@Column(name = "port")
	private  int port;
	
	
	@Column(name = "email_user")
	private  String user;
	
	
	@Column(name = "password")
	private  String password;
	
	
	@Column(name = "map_sender_name_to_udaid")
	private  Long mapSenderNameToUdaId;
	
	
	@Column(name = "map_sender_name_to")
	private  String mapSenderNameTo;
	
	
	@Column(name = "map_subject_to")
	private String mapSubjectTo;

	
	@Column(name = "map_subject_to_udaid")
	private Long mapSubjectToUdaId;

	
	@Column(name = "map_body_to")
	private String mapBodyTo;
	
	
	@Column(name = "map_body_to_udaid")
	private Long mapBodyToUdaId;

	
	@Column(name = "is_ssl" , nullable=true)
	private Boolean isSSL ;

	
	@Column(name = "email_reading_type")
	private Byte emailReadingType ;

	public Long getMapSenderNameToUdaId() {
		return mapSenderNameToUdaId;
	}

	public void setMapSenderNameToUdaId(Long mapSenderNameToUdaId) {
		this.mapSenderNameToUdaId = mapSenderNameToUdaId;
	}

	public Long getMapSubjectToUdaId() {
		return mapSubjectToUdaId;
	}

	public void setMapSubjectToUdaId(Long mapSubjectToUdaId) {
		this.mapSubjectToUdaId = mapSubjectToUdaId;
	}

	public Long getMapBodyToUdaId() {
		return mapBodyToUdaId;
	}

	public void setMapBodyToUdaId(Long mapBodyToUdaId) {
		this.mapBodyToUdaId = mapBodyToUdaId;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMapSenderNameTo() {
		return mapSenderNameTo;
	}

	public void setMapSenderNameTo(String mapSenderNameTo) {
		this.mapSenderNameTo = mapSenderNameTo;
	}

	public String getMapSubjectTo() {
		return mapSubjectTo;
	}

	public void setMapSubjectTo(String mapSubjectTo) {
		this.mapSubjectTo = mapSubjectTo;
	}

	public String getMapBodyTo() {
		return mapBodyTo;
	}

	public void setMapBodyTo(String mapBodyTo) {
		this.mapBodyTo = mapBodyTo;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsSSL() {
		return isSSL;
	}

	public void setIsSSL(Boolean isSSL) {
		this.isSSL = isSSL;
	}

	public Byte getEmailReadingType() {
		return emailReadingType;
	}

	public void setEmailReadingType(Byte emailReadingType) {
		this.emailReadingType = emailReadingType;
	}
	
	
	
	
	
}
