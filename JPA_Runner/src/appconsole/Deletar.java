package appconsole;

import regras_negocio.Fachada;

public class Deletar {

	public Deletar() {
		Fachada.inicializar();
		try {
			// Excluir um entregador
			Fachada.excluirEntregador("João");
			System.out.println("Apagou o entregador João");

			// Excluir uma entrega
			Fachada.excluirEntrega("3hk7t4");
			System.out.println("Apagou a entrega com código 3hk7t4");

			// Excluir um pedido
			Fachada.excluirPedido("17yv84");
			System.out.println("Apagou o pedido com código 17yv84");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("Fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Deletar();
	}
}
