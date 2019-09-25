package com.fdmgroup.jsp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;




public class JpaUserDao extends UserDao{

	private EntityManagerFactory emf;

	public JpaUserDao(EntityManagerFactory emf) {
		
		this.emf = emf;
	}

	@Override
	public void create(User user) throws DuplicateKeyException {
		EntityManager em = emf.createEntityManager();
		
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		
			em.close();
		}
	
		

	@Override
	public User read(Long id) throws EntityDoesNotExistException {
		
		EntityManager em = emf.createEntityManager();
		User user = em.find(User.class, id);
		if(user == null) {
			throw new EntityDoesNotExistException();
		}
		return user;
	}

	@Override
	public void delete(User delete) {
		//get transaction must be called any time you make changes to the database
		EntityManager em = emf.createEntityManager();
		//here you start the process of making changes to the database
		em.getTransaction().begin();
		em.remove(em.contains(delete) ? delete : em.merge(delete));
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public User update(User up) {
		//get transaction must be called any time you trying to make changes to the database
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(up);
		em.getTransaction().commit();
		em.close();
		return up;
	}

	@Override
	public List<User> getAll() {
		
		return null;
	}
	

}
