package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Entrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate dataEntrega = LocalDate.now();	
    private String endereco;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Entregador entregador;
    
    @OneToMany(mappedBy = "entrega", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Pedido> pedidos = new ArrayList<>();


	public Entrega() {
	}
	
	public Entrega(String endereco) {
		this.endereco = endereco;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public LocalDate getdataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String novoEndereco) {
		endereco = novoEndereco ; 
	}
	

	/*----------Relacionamento com Entregador-----------*/
	public Entregador getEntregador() {
		return entregador;
	}
	public void setEntregador(Entregador entregador) {
		this.entregador = entregador;
	}
	

	/*----------Relacionamento com Pedidos-----------*/
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<Pedido> listaPedidos){
		this.pedidos = listaPedidos;
	}
	
	
	public void adicionar(Pedido p){
		pedidos.add(p);
		p.setEntrega(this);
	}
	
	public void remover(Pedido p){
		pedidos.remove(p);
		p.setEntrega(null);
	}
	
	public Pedido localizar(int id){
		for(Pedido p : pedidos)
			if (id == p.getId())
				return p;
		return null;
	}
	
	public String toString() {
		String texto = "id: " + id +", Data de Entrega: " + getdataEntrega()+ ", Endereço: " + getEndereco() + "\n[Entregadores: " + getEntregador() + "]\n;";

		texto += ",  Pedidos: ";
		for(Pedido p : pedidos)
			if (p != null) {
		        texto += p.getId() + ",";
		    } else {
		        texto += "";
		    }
		return texto;
	}
}
