package ovh.gorillahack.wazabi.dao;

//import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@SuppressWarnings("serial")
public abstract class DaoImpl<E> implements Dao<E> {
	private Class<E> entityClass;

	@PersistenceContext(unitName="wazabi")
	private EntityManager entityManager;
	
	
	public DaoImpl(Class<E> clazz) {
		entityClass = clazz;
	}
	
	public E rechercher(int id) {
		return (E) entityManager.find(entityClass, id);
	}

	public E enregistrer(E entite) {
		entityManager.persist(entite);
		return entite;
	}

	public E mettreAJour(E entite) {
		return entityManager.merge(entite);		
	}

	public E recharger(int id) {
		E entite = rechercher(id);
		entityManager.refresh(entite);
		return entite;
	}

	public void supprimer(int id) {
		E entite = rechercher(id);
		entityManager.remove(entite);
	}

	public List<E> lister() {
		return liste("select x from " + entityClass.getName() + " x");
	}

	protected List<E> liste(String queryString, Object... params) {
		List<E> entites = null;
		TypedQuery<E> query = entityManager.createQuery(queryString, entityClass);
		int i = 0, j = 1;
		while(i < params.length) {
			if (params[i] instanceof Date ) {
				query.setParameter(j, (Date)params[i], (TemporalType) params[i+1]);
				i+=2; 
			} else if (params[i] instanceof Calendar) {
				query.setParameter(j, (Calendar)params[i], (TemporalType) params[i+1]);
				i+=2; 
			} else {
				query.setParameter(j, params[i]);
				i++; 
			}
			j++;
		}
		entites = query.getResultList();
		return entites;
	}

	protected E recherche(String queryString, Object... params) {
		try {
			TypedQuery<E> query = entityManager.createQuery(queryString, entityClass);
			int i = 0, j = 1;
			while(i < params.length) {
				if (params[i] instanceof Date ) {
					query.setParameter(j, (Date)params[i], (TemporalType) params[i+1]);
					i+=2; 
				} else if (params[i] instanceof Calendar) {
					query.setParameter(j, (Calendar)params[i], (TemporalType) params[i+1]);
					i+=2; 
				} else {
					query.setParameter(j, params[i]);
					i++; 
				}
				j++;
			}
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null; // throw new InternalError();
		}
	}
}
