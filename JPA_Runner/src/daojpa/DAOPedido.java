/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Pedido;

public class DAOPedido extends DAO<Pedido> {

	public Pedido read(Object chave) {
		try {
			String numero = (String) chave;
			TypedQuery<Pedido> q = manager.createQuery("select t from Pedido t where t.numero = :n ",
					Pedido.class);
			q.setParameter("n", numero);

			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
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
