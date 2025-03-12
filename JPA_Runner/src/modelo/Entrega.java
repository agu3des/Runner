package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Table(name="entrega_20231370020")
public class Entrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String codigoEntrega;
	private LocalDate dataEntrega = LocalDate.now();	
    private String endereco;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	private Entregador entregador;
    
    @OneToMany(mappedBy = "entrega", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Pedido> pedidos = new ArrayList<>();


	public Entrega() {
	}

	public Entrega(String codigoEntrega) {
		this.codigoEntrega = codigoEntrega;
	}

	public Entrega(String codigoEntrega, String endereco) {
		this.codigoEntrega = codigoEntrega;
		this.endereco = endereco;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getCodigoEntrega() {
		return codigoEntrega;
	}
	public void setCodigoEntrega(String codigoEntrega) {
		this.codigoEntrega = codigoEntrega;
	}

	public LocalDate getDataEntrega() {
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

	
	public void adicionar(Pedido p){
		pedidos.add(p);
		p.setEntrega(this);
	}
	
	public void remover(Pedido p){
		pedidos.remove(p);
		p.setEntrega(null);
	}
	
	public Pedido localizar(String codigo){
		for(Pedido p : pedidos)
			if (codigo.equals(p.getCodigoPedido()))
				return p;
		return null;
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
	
	
	public String toString() {
		String texto = "Codigo da Entrega: " + getCodigoEntrega() +
				", Data de Entrega: " + getDataEntrega() + 
				", Endereço: " + getEndereco() + 
				"\n[Entregadores: " + getEntregador() + 
				"]\n";

		texto += ", [ Pedidos: ";
		for(Pedido p : pedidos)
			if (p != null) {
		        texto += p.getCodigoPedido() + ",";
		    } else {
		        texto += " ]";
		    }
		return texto += " ]";
	}

}
