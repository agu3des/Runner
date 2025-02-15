package modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String datapedido = LocalDate.now();	
    private double valor;
	private String descricao;

	
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
	public void setValor(double valor) {
		this.valor = valor ; 
	}
	
	
    public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String novaDescricao) {
		this.descricao = novaDescricao;
	}

	
	public String toString() {
		String texto = "id:" + id + ", Data Pedido: " +  getDataPedido() + ", Valor: " + getValor()  + ", Descrição: " + getDescricao();

		return texto;
	}
}