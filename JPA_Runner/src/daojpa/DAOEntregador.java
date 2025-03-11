package daojpa;

import java.util.List;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.Entregador;

public class DAOEntregador extends DAO<Entregador>{

	public Entregador read (Object chave){
		try{
			String nome = (String) chave;
			TypedQuery<Entregador> q = manager.createQuery("select e from Entregador e where e.nome =:n", Entregador.class);
			q.setParameter("n", nome);
			return q.getSingleResult();

		}catch(NoResultException e){
			return null;
		}
	}
	
	// --------------------------------------------
		// consultas
		// --------------------------------------------
		public List<Entregador> readLikeNome(String nome) {
			TypedQuery<Entregador> q = manager.createQuery(
					"""
					select er from Entregador er 
					where er.nome like :x  
					""",Entregador.class);
			q.setParameter("x", "%" + nome + "%");
			return q.getResultList();
		}
		
		public List<Entregador> readByNEntregas(int n) {
			TypedQuery<Entregador> q = manager.createQuery(
					"""
					select er from Entregador er 
					where SIZE(er.entregas) = :x
					""", Entregador.class);
			q.setParameter("x", n);
			return q.getResultList();
		}

	}

