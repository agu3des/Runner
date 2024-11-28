package daodb4o;

import java.util.List;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Entrega;
import modelo.Entregador;



public class DAOEntregador extends DAO<Entregador>{
	
	//nome eh usado como campo unico 
	public Entregador read (String nome) {
		Query q = manager.query();
		q.constrain(Entregador.class);
		q.descend("nome").constrain(nome);
		List<Entregador> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	
	public  List<Entregador> readAll(String entregadores) {
		Query q = manager.query();
		q.constrain(Entregador.class);
		q.descend("id").constrain(entregadores).like();		//insensitive
		List<Entregador> result = q.execute(); 
		return result;
	}
	
	public void create(Entregador obj){
		int novoid = super.gerarId(Entregador.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	//dúvida acerca do funcionamento
	//quais os entregadores que tem mais de n entregas
	public List<Entregador> readByNEntregas(int n) {
		Query q = manager.query();
		q.constrain(Entregador.class);
		q.constrain(new Filtro(n));
		return q.execute(); 
	}
	
	/*-------------------------------------------------*/
	@SuppressWarnings("serial")
	class Filtro  implements Evaluation {
		private int n;
		public Filtro (int n) {
			this.n=n;
		}
		public void evaluate(Candidate candidate) {
			Entregador e = (Entregador) candidate.getObject();
			candidate.include( e.getEntregas().size() > n );
		}
	}

}

