package modelo;

import java.util.ArrayList;
import java.util.List;


public class Entrega {
	
	private int id;
	private String idEntrega;
	private String dataEntrega;	
    private String endereco;
	private Entregador entregador;
	private Pedido pedido;


	public Entrega(String idEntrega) {
		this.idEntrega = idEntrega;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
    public String getIdEntrega() {
		return idEntrega;
	}
	public void setIdEntrega(String novoId) {
		this.idEntrega = novoId;
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
	

	/*----------Relacionamento-----------*/
	public Entregador getEntregador() {
		return entregador;
	}
	public void setEntregador(Entregador entregador) {
		this.entregador = entregador;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public String toString() {
		String texto = "id: " + idEntrega+", Data de Entrega: " +  getdataEntrega() + ", Endereço: " + getEndereco() + "\n[Entregadores: " + getEntregador() + "]\n[Pedidos: " + getPedido() + "]\n;";

		return texto;
	}
}
