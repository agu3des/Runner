package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import modelo.Entrega;
import modelo.Pedido;

public class DAOPedido  extends DAO<Pedido>{
	
	//id eh usado como campo unico 
	public Pedido read (String idPedido) {
		Query q = manager.query();
		q.constrain(Pedido.class);
		q.descend("id").constrain(idPedido);
		List<Pedido> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	
	public  List<Pedido> readAll(String pedidos) {
		Query q = manager.query();
		q.constrain(Pedido.class);
		q.descend("id").constrain(pedidos).like();		//insensitive
		List<Pedido> result = q.execute(); 
		return result;
	}
	
	public void create(Pedido obj){
		int novoid = super.gerarId(Pedido.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
	
	//pedidos por valor, ex: todos os pedidos com 100 reais
	public  List<Pedido> readByValor(double valor) {
		Query q = manager.query();
		q.constrain(Pedido.class);
		q.descend("valor").constrain(valor).like();		//insensitive
		List<Pedido> result = q.execute(); 
		return result;
	}
	
}

