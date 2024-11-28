package appconsole;

import java.util.ArrayList;
import java.util.List;

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando pessoas...");
			Fachada.inicializar();
			
			Fachada.criarPedido("17yv84", "01/01/2025",15.69, "Forma de bolo de cenoura");
			Fachada.criarPedido("2753pk", "15/12/2024",100, "Suporte de monitor");
			
			Fachada.criarEntregador("1","João",new ArrayList<>(List.of()));
			
			Fachada.criarEntrega(1,"01/01/2025", "Avenida Vasco da Gama, 1245", new Entregador("Lays"), new Pedido("l1o574"));
			
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


