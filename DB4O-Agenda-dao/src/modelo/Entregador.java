package modelo;

import java.util.ArrayList;
import java.util.List;

public class Entregador {
	private int id;
	private String nome;	
	private List<Entrega> entregas = new ArrayList<>();

//mesma duvida de Pedido
	public Entregador(int id) {
		this.id = id;
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
	public void setNome(String Nome) {
		this.nome = nome;
	}
	public List<Entrega> getEntregas() {
		return entregas;
	}

	public void setEntregas(List<Entrega> listaEntregas){
		this.entregas = listaEntregas;
	}
	public void adicionar(Entrega e){
		entregas.add(e);
        //conferir se é de fato setEntrega
		e.setEntregador(this);
	}
	public void remover(Entrega e){
		entregas.remove(e);
		e.setEntregador(null);
	}
	public Entrega localizar(int idEntrega){
		for(Entrega e : entregas)
			if (e.getId().equals(idEntrega))
				return e;
		return null;
	}
	public String toString() {
		String texto = "id" + id+" Nome=" +  getNome();

		texto += "  Entregas: ";
		for(Entrega e : entregas)
			texto += e.getIdEntrega() + ",";
		return texto;
	}
}