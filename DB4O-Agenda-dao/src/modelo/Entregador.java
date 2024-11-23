package modelo;

import java.util.ArrayList;
import java.util.List;

public class Entregador {
	
	private int idEntregador;
	private String nome;	
	private List<Entrega> entregas = new ArrayList<>();

	//mesma duvida de Pedido
	public Entregador(int id) {
		this.idEntregador = id;
	}
	
	
    public int getIdEntregador() {
		return idEntregador;
	}
	public void setIdEntregador(int id) {
		this.idEntregador = id;
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
        //conferir se é de fato setEntrega
		e.setEntregador(this);
	}
	public void remover(Entrega e){
		entregas.remove(e);
		e.setEntregador(null);
	}
	public Entrega localizar(int idEntrega){
		for(Entrega e : entregas)
			if (e.getIdEntrega() == idEntrega)
				return e;
		return null;
	}
	public String toString() {
		String texto = "id" + idEntregador+" Nome=" +  getNome();

		texto += "  Entregas: ";
		for(Entrega e : entregas)
			texto += e.getIdEntrega() + ",";
		return texto;
	}
}