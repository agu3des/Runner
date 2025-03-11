package appconsole;

import java.time.LocalDate;

import regras_negocio.Fachada;

public class Cadastrar {

    public Cadastrar() {
        try {
        	System.out.println("Cadastrando entregadores, pedidos e entregas...");
        	Fachada.inicializar();
            
			Fachada.criarEntregador("João");
			Fachada.criarEntregador("Maria");
			Fachada.criarEntregador("Carlos");
			Fachada.criarEntregador("Ana");
        	
			Fachada.criarPedido("17yv84",  LocalDate.now(), 15.50, "Forma de bolo de cenoura");
			Fachada.criarPedido("2753pk", LocalDate.now(), 100, "Suporte de monitor");
			Fachada.criarPedido("9xy123", LocalDate.now(), 50, "Caixa organizadora");
			Fachada.criarPedido("d4z687", LocalDate.now(), 120, "Conjunto de ferramentas");
			Fachada.criarPedido("a1b2c3", LocalDate.now(), 30, "Cesta de frutas");

			Fachada.criarEntrega("1deu58", LocalDate.now(), "Avenida Vasco da Gama, 1245", "João", "17yv84");
			Fachada.criarEntrega("24lhy9", LocalDate.now(), "Rua das Flores, 456", "Carlos", "9xy123");
			Fachada.criarEntrega("3bdg73", LocalDate.now(), "Avenida Central, 789", "Ana", "d4z687");
			Fachada.criarEntrega("4pws16", LocalDate.now(), "Praça da Liberdade, 321", "Maria", "a1b2c3");
           

        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("\nFim da aplicação");
    }

    // =================================================
    public static void main(String[] args) {
        new Cadastrar();
    }
}
