package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;

public class
DAOEntrega extends DAO<Entrega> {

	public Entrega read(Object chave) {
		try {
			int id = (int) chave;
			TypedQuery<Entrega> q = manager.createQuery("select e from Entrega e where e.id=:n", Entrega.class);
			q.setParameter("n", id);
			return q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}


	// --------------------------------------------
	// consultas
	// --------------------------------------------
	
	public List<Entrega> readByNPedidos(int n) {
		TypedQuery<Entrega> q = manager.createQuery(
				"""
				select e from Entrega e 
				where SIZE(e.pedidos) = :x
				""", Entrega.class);
		q.setParameter("x", n);
		return q.getResultList();
	}

	public List<Entrega> readByData(String dataEntrega) {
		TypedQuery<Entrega> q = manager
				.createQuery(
						"""
						select e from Entrega e 
						where e.dataEntrega = :m
						""", Entrega.class);
		q.setParameter("m", dataEntrega);
		return q.getResultList();

	}

	//verificar
	public boolean dataEhDiferente(String dataPedido) {
		try {
			Query q = manager.createQuery(
					"select e from Entrega e join e.pedidos p where e.dataEntrega <> p.dataPedido");
			long cont = (Long) q.getSingleResult();
			return cont > 0;
		} catch (NoResultException e) {
			return false;
		}
	}


}
