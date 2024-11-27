package modelo;

import java.util.ArrayList;
import java.util.List;


public class Entrega {
	
	private int idEntrega;
	private String dataEntrega;	
    private String endereco;
	private Entregador entregador;
	private Pedido pedido;


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
		String texto = "id" + idEntrega+" Data de Entrega=" +  getdataEntrega() + ", Endereço=" + getEndereco() + "  Entregadores: " + getEntregador() + ",   Pedido: " + getPedido() + ",";

		return texto;
	}
}
