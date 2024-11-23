package modelo;

import java.util.ArrayList;
import java.util.List;


public class Entrega {
	
	private int idEntrega;
	private String dataEntrega;	
    private String endereco;
	private List<Entregador> entregadores = new ArrayList<>();
	private List<Pedido> pedidos = new ArrayList<>();

	//mesma duvida de Pedido
	public Entrega(int id) {
		this.idEntrega = id;
	}
	
	
    public int getIdEntrega() {
		return idEntrega;
	}
	public void setIdEntrega(int id) {
		this.idEntrega = id;
	}
	
	
	public String getdataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String novoEndereco) {
		endereco = novoEndereco ; 
	}
	

	public List<Entregador> getEntregadores() {
		return entregadores;
	}

	public void setEntregadores(List<Entregador> listaEntregadores){
		this.entregadores = listaEntregadores;
	}
    
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> listaPedidos){
		this.pedidos = listaPedidos;
	}
    
	
	
	public void adicionarEntregador(Entregador e){
		entregadores.add(e);
        //conferir se é de fato setEntrega
		e.setEntrega(this);
	}
	public void removerEntregador(Entregador e){
		entregadores.remove(e);
		e.setEntrega(null);
	}
	public Entregador localizarEntregador(int idEntregador){
		for(Entregador e : entregadores)
			if (e.getIdEntregador() == idEntregador)
				return e;
		return null;
	}
	
	
	
    public void adicionarPedido(Pedido p){
		pedidos.add(p);
		p.setEntrega(this);
	}
	public void removerPedido(Pedido p){
		pedidos.remove(p);
		p.setEntrega(null);
	}
	public Pedido localizarPedido(int idPedido){
		for(Pedido p : pedidos)
			if (p.getIdPedido() == idPedido)
				return p;
		return null;
	}
	
	
	public String toString() {
		String texto = "id" + idEntrega+" Data de Entrega=" +  getdataEntrega() + ", Endereço=" + getEndereco();

		texto += "  Entregadores: ";
		for(Entregador e : entregadores)
			texto += e.getIdEntregador() + ",";

		texto += "  Pedidos: ";
		for(Pedido p : pedidos)
			texto += p.getIdPedido() + ",";

		return texto;
	}
}
