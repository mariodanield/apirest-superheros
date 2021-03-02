package com.superheros.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="heroes")
public class Hero implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Integer age;
	private String power;
	
	@Column(name="created", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private Date created;

	public Hero() {
		super();
	}
	
	public Hero(String name, Integer age, String power) {
		super();
		this.name = name;
		this.age = age;
		this.power = power;
	}

	public Hero(Hero hero) {
		super();
		this.id = hero.getId();
		this.name = hero.getName();
		this.age = hero.getAge();
		this.power = hero.getPower();
		this.created = hero.getCreated();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + ", age=" + age + ", power=" + power + ", created=" + created + "]";
	}
	

}
