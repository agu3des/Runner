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
			Fachada.criarEntregador("Lucas");
			Fachada.criarEntregador("Carla");
			Fachada.criarEntregador("Ricardo");
			Fachada.criarEntregador("Fernanda");
        	
			Fachada.criarPedido("17yv84",  LocalDate.now(), 15.50, "Forma de bolo de cenoura");
			Fachada.criarPedido("2753pk", LocalDate.now(), 100, "Suporte de monitor");
			Fachada.criarPedido("9xy123", LocalDate.now(), 50, "Caixa organizadora");
			Fachada.criarPedido("d4z687", LocalDate.now(), 120, "Conjunto de ferramentas");
			Fachada.criarPedido("a1b2c3", LocalDate.now(), 30, "Cesta de frutas");
			Fachada.criarPedido("x2g9q8", LocalDate.now(), 75.00, "Smartphone");
			Fachada.criarPedido("a4f57t", LocalDate.now(), 35.00, "Caneca personalizada");
			Fachada.criarPedido("z9t21k", LocalDate.now(), 220.00, "Notebook");
			Fachada.criarPedido("j7b46n", LocalDate.now(), 45.00, "Fone de ouvido Bluetooth");
			Fachada.criarPedido("d4e26", LocalDate.now(), 655.00, "Guarda-roupa");
			
			Fachada.criarEntrega("1deu58", LocalDate.now(), "Avenida Vasco da Gama, 1245", "João", "17yv84");
			Fachada.criarEntrega("24lhy9", LocalDate.now(), "Rua das Flores, 456", "Carlos", "9xy123");
			Fachada.criarEntrega("3bdg73", LocalDate.now(), "Avenida Central, 789", "Ana", "d4z687");
			Fachada.criarEntrega("4pws16", LocalDate.now(), "Praça da Liberdade, 321", "Maria", "a1b2c3");
			Fachada.criarEntrega("2sdn85", LocalDate.now(), "Rua Rio de Janeiro, 12", "Lucas", "x2g9q8");
			Fachada.criarEntrega("3hk7t4", LocalDate.now(), "Avenida Paulista, 2000", "Fernanda", "a4f57t");
			Fachada.criarEntrega("5drx92", LocalDate.now(), "Rua dos Três Irmãos, 60", "Fernanda", "z9t21k");
			Fachada.criarEntrega("8l9u84", LocalDate.now(), "Avenida das Nações, 500", "Fernanda", "j7b46n");
			Fachada.criarEntrega("7956oh", LocalDate.now(), "Rua Fafá de Belém, 45", "Maria", "2753pk");
			

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
