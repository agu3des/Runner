package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Entrega;
import modelo.Pedido;

public class DAOEntrega  extends DAO<Entrega>{
	
	//id eh usado como campo unico 
	public Entrega read (int idEntrega) {
		Query q = manager.query();
		q.constrain(Entrega.class);
		q.descend("nome").constrain(idEntrega);
		List<Entrega> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Entrega obj){
		int novoid = super.gerarId(Entrega.class);  	//gerar novo id da classe
		obj.setIdEntrega(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	//quais as entregas na data X
	public List<Entrega> readByData(String dataEntrega) {
		Query q = manager.query();
		q.constrain(Entrega.class);  
		q.descend("dataEntrega").constrain(dataEntrega).contains();
		return q.execute();
	}
	
	//quais as entregas com data diferente da data do pedido
	public boolean dataEhDiferente(String dataEntrega, String dataPedido) {
		Query q = manager.query();
		q.constrain(Entrega.class);
		q.descend("dataEntrega").constrain(dataEntrega);
		
		Query q2 = manager.query();
		q2.constrain(Pedido.class);
		q2.descend("dataPedido").constrain(dataPedido);
		
		if (q.equals(q2)) {
			return q.execute().size()>0;
		} else {
			return false;
		}
		
	}
	

}

