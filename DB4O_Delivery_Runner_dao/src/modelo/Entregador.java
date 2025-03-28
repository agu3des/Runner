package modelo;

import java.util.ArrayList;
import java.util.List;

public class Entregador {
	
	private int id;
	private String nome;	
	private List<Entrega> entregas = new ArrayList<>();


	public Entregador(String nome) {
		this.nome = nome;
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
	
	public List<Entrega> getEntregas() {
		return entregas;
	}
	public void setEntregas(List<Entrega> listaEntregas){
		this.entregas = listaEntregas;
	}
	
	
	public void adicionar(Entrega e){
		entregas.add(e);
		e.setEntregador(this);
	}
	
	public void remover(Entrega e){
		entregas.remove(e);
		e.setEntregador(null);
	}
	
	public Entrega localizar(String idEntrega){
		for(Entrega e : entregas)
			if (idEntrega.equals(e.getIdEntrega()))
				return e;
		return null;
	}
	
	public String toString() {
		String texto = "id: " + id +", Nome: " +  getNome();

		texto += ",  Entregas: ";
		for(Entrega e : entregas)
			if (e != null) {
		        texto += e.getIdEntrega() + ",";
		    } else {
		        texto += "";
		    }
		return texto;
	}
}