package modelo;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String codigoPedido;
    private LocalDate dataPedido = LocalDate.now();	
    private double valor;
	private String descricao;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Entrega entrega;
	
	public Pedido() {
	}

	public Pedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}
	
	public Pedido(String codigoPedido, double valor, String descricao) {
		this.codigoPedido = codigoPedido;
		this.valor = valor;
		this.descricao = descricao;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}
	public void setCodigoPedido(String codigo) {
		this.codigoPedido = codigo;
	}



	public LocalDate getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(LocalDate dt) {
		dataPedido = dt ; 
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

	
	public Entrega getEntrega() {
		return entrega;
	}
	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	
	
	public String toString() {
		String texto = "[ Codigo do Pedido:" + getCodigoPedido() + 
				", Data Pedido: " +  getDataPedido() + 
				", Valor: " + getValor()  + 
				", Descrição: " + getDescricao() +
				" ]";

		return texto;
	}
}