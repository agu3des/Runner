package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="entregador_20231370020")
public class Entregador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;	
	
	@OneToMany(mappedBy = "entregador", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Entrega> entregas = new ArrayList<>();

	public Entregador() {
	}
	
	public Entregador(String nome) {
		this.nome = nome.toLowerCase();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String novoNome) {
		this.nome = novoNome;
	}
	
	
	public void adicionar(Entrega e){
		entregas.add(e);
		e.setEntregador(this);
	}
	
	public void remover(Entrega e){
		entregas.remove(e);
		e.setEntregador(null);
	}
	
	public Entrega localizar(String codigo){
		for(Entrega e : entregas)
			if (codigo.equals(e.getCodigoEntrega()))
				return e;
		return null;
	}
	
	public String toString() {
		String texto = "Id: " + id +
				", Nome: " +  getNome();
		texto += ", [ Entregas: ";
		for(Entrega e : entregas)
			if (e != null) {
		        texto += e.getCodigoEntrega() + ",";
		    } else {
		        texto += " ]";
		    }
		return texto += " ]";
	}
	
	/*----------Relacionamento com Entregas-----------*/
	public List<Entrega> getEntregas() {
		return entregas;
	}
	public void setEntregas(List<Entrega> listaEntregas){
		this.entregas = listaEntregas;
	}
	
}