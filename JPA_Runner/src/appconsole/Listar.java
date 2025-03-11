
package appconsole;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class Listar {

	public Listar() {
		try {
			Fachada.inicializar();

			System.out.println("\n*** Listagem de pedidos:");
			for (Pedido p : Fachada.listarPedidos())
				System.out.println(Fachada.localizarPedido(p.getCodigoPedido()));
			System.out.println("\n------------------------------------------\n");
			
			System.out.println("*** Listagem de entregadores:");
			for (Entregador e : Fachada.listarEntregadores())
				System.out.println(Fachada.localizarEntregador(e.getNome()));
			System.out.println("\n------------------------------------------\n");
			
			System.out.println("*** Listagem de entregas:");
			for (Entrega e : Fachada.listarEntregas())
				System.out.println(Fachada.localizarEntrega(e.getCodigoEntrega()));
			System.out.println("\n------------------------------------------\n");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
	}

	// =================================================
	public static void main(String[] args) {
		new Listar();
	}
	// =================================================
}
