package org.HomeCooking.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public enum Type implements Serializable{
	Plat,
	Boisson;
}
