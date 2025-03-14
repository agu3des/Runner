package daojpa;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;

public class DAOEntrega extends DAO<Entrega> {

	public Entrega read(Object chave) {
		try {
			String codigo = (String) chave;
			TypedQuery<Entrega> q = manager.createQuery("select e from Entrega e where e.codigoEntrega =:en", 
					Entrega.class);
			q.setParameter("en", codigo);
			return q.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Entrega> readByCodigo(String codigo) {
		TypedQuery<Entrega> q = manager.createQuery("""
				select e from Entrega e 
				where e.codigoEntrega like :x """, Entrega.class);
		q.setParameter("x", "%" + codigo + "%");
		return q.getResultList();
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

	public List<Entrega> readByData(LocalDate dataEntrega) {
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
