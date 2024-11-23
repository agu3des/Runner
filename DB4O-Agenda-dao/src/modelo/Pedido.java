package modelo;

import java.util.ArrayList;
import java.util.List;


public class Pedido {
	private int id;
    private String datapedido;	
    private double valor;
	private String descricao;

//incluir valor e descrição??
	public Pedido(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataPedido() {
		return datapedido;
	}

	public void setDataPedido(String dt) {
		datapedido = dt ; 
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double novoValor) {
		this.valor = novoValor;
	}
    public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String novaDescricao) {
		this.descricao = novaDescricao;
	}

	public String toString() {
		String texto = "id" + id+" DataPedido=" +  getDataPedido() + ", Valor=" + getValor()  + ", Descrição=" + getDescricao();

		return texto;
	}
}