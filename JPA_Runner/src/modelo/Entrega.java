package modelo;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Entrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String dataEntrega = LocalDate.now();	
    private String endereco;
	private Entregador entregador;
	private Pedido pedido;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})

	public Entrega(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		String texto = "id: " + id +", Data de Entrega: " +  getdataEntrega() + ", Endereço: " + getEndereco() + "\n[Entregadores: " + getEntregador() + "]\n[Pedidos: " + getPedido() + "]\n;";

		return texto;
	}
}
