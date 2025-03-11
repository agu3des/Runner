package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Pedido;
import modelo.Telefone;

public class DAOPedido extends DAO<Pedido> {

	public Pedido read(Object chave) {
		try {
			String codigo = (String) chave;
			TypedQuery<Pedido> q = manager.createQuery("select p from Pedido p where p.codigoPedido = :c ",
					Pedido.class);
			q.setParameter("c", codigo);
			return q.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Pedido> readByCodigo(String codigo) {
		// carregar telefone com seus relacionamentos
		TypedQuery<Pedido> q = manager.createQuery("""
				select p from Pedido p 
				where p.codigoPedido like :x """, Pedido.class);
		q.setParameter("x", "%" + codigo + "%");
		return q.getResultList();
	}
	// --------------------------------------------
	// consultas
	// --------------------------------------------

	public List<Pedido> readByValor(double valor) {
		TypedQuery<Pedido> q = manager
				.createQuery(
						"""
						select p from Pedido
						where p.valor = :m
						""", Pedido.class);
		q.setParameter("m", valor);
		return q.getResultList();

	}

}
