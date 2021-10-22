package com.jejbuitenhuis.spotitube.util.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public abstract class JPADAO<T>
{
	private EntityManagerFactory emf;
	private EntityManager em;

	protected abstract String getQueryAll();
	protected abstract String getQueryAllMatching();

	private void construct()
	{
		this.emf = Persistence.createEntityManagerFactory("spotitube_jpa");
		this.em = this.emf.createEntityManager();
	}

	private void deconstruct()
	{
		this.em.close();
		this.emf.close();

		this.em = null;
		this.emf = null;
	}

	public <N> List<T> getAllMatching(N needle)
	{
		assert needle != null;

		this.construct();

		Query q = this.em.createQuery( this.getQueryAllMatching() );

		q.setParameter(1, needle);

		var ret = q.getResultList();

		this.deconstruct();

		return ret;
	}

	public void save(T object)
	{
		assert object != null;

		this.construct();

		this.em.persist(object);
		this.em.merge(object); // just to be safe

		this.deconstruct();
	}
}
