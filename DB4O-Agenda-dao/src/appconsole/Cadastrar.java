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
			Fachada.criarPedido("9xy123", "05/12/2024", 50.00, "Caixa organizadora");
            Fachada.criarPedido("d4z687", "10/01/2025", 120.49, "Conjunto de ferramentas");
            Fachada.criarPedido("a1b2c3", "20/12/2024", 30.75, "Cesta de frutas");
			
			
			Fachada.criarEntregador("João",new ArrayList<>(List.of()));
			Fachada.criarEntregador("Maria", new ArrayList<>(List.of()));
            Fachada.criarEntregador("Carlos", new ArrayList<>(List.of()));
            Fachada.criarEntregador("Ana", new ArrayList<>(List.of()));

			
			
			Fachada.criarEntrega("158","01/01/2025", "Avenida Vasco da Gama, 1245", Fachada.localizarEntregador("Lays"), Fachada.localizarPedido("l1o574"));
			Fachada.criarEntrega("249", "06/12/2024", "Rua das Flores, 456", Fachada.localizarEntregador("Carlos"), Fachada.localizarPedido("9xy123"));
            Fachada.criarEntrega("373", "11/01/2025", "Avenida Central, 789", Fachada.localizarEntregador("Ana"), Fachada.localizarPedido("d4z687"));
            Fachada.criarEntrega("416", "21/12/2024", "Praça da Liberdade, 321", Fachada.localizarEntregador("Maria"), Fachada.localizarPedido("a1b2c3"));
			
			
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



<<<<<<< HEAD
=======

>>>>>>> 8605f4bc156e993758338a2d389d27af18a19b81
