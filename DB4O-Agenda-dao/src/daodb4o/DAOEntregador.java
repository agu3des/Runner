package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Entregador;



public class DAOEntregador  extends DAO<Entregador>{
	
	//nome � usado como campo unico 
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
	
	public void create(Entregador obj){
		int novoid = super.gerarId(Entregador.class);  	//gerar novo id da classe
		obj.setIdEntregador(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
}

