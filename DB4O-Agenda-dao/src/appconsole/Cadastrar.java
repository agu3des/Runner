package appconsole;

import java.util.ArrayList;
import java.util.List;

import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando...");
			Fachada.inicializar();
			
			Fachada.criarPedido("17yv84", "01/01/2025",15.69, "Forma de bolo de cenoura");
			Fachada.criarPedido("2753pk", "15/12/2024",100, "Suporte de monitor");
			//tirei o codigo que acompanhava entregador e não ia de acordo com fachada
			Fachada.criarEntregador("João",new ArrayList<>(List.of()));
			//coloquei o 1 entre aspas porque ele eh considerado String no metodo da fachada
			Fachada.criarEntrega("1","01/01/2025", "Avenida Vasco da Gama, 1245", new Entregador("Lays"), new Pedido("l1o574"));
			
			System.out.println("Cadastrando");

		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}




