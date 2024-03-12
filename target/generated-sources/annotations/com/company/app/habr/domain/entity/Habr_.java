package com.company.app.habr.domain.entity;

import com.company.app.habr.domain.enums.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Habr.class)
public abstract class Habr_ {

	public static volatile SingularAttribute<Habr, Long> id;
	public static volatile ListAttribute<Habr, Participant> users;
	public static volatile SingularAttribute<Habr, Status> status;

	public static final String ID = "id";
	public static final String USERS = "users";
	public static final String STATUS = "status";

}

