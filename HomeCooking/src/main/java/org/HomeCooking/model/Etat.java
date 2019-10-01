package org.HomeCooking.model;

import java.io.Serializable;

import javax.persistence.Entity;

public enum Etat implements Serializable {
		EnCours,
		Facturée,
		Annulée;
}
