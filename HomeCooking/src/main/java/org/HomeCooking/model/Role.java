package org.HomeCooking.model;

import java.io.Serializable;

import javax.persistence.Entity;


public enum Role implements Serializable{
	Restaurateur,
	Client,
	Admin;
}

