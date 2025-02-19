
package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;

public class Listar {
	private EntityManager manager;

	public Listar() {
		try {
			manager = Util.conectarBanco();

			System.out.println("\nListagem de pedidos:");
			TypedQuery<Pedido> query1 = manager.createQuery("select p from Pedido p order by p.id", Pedido.class);
			List<Pedido> resultados1 = query1.getResultList();
			for (Pedido p : resultados1) {
				System.out.println(p);
			}

			System.out.println("\nListagem de entregadores:");
			TypedQuery<Entregador> query2 = manager.createQuery("select e from Entregador e order by e.id", Entregador.class);
			List<Entregador> resultados2 = query2.getResultList();
			for (Entregador e : resultados2) {
				System.out.println(e);
			}

			System.out.println("\nListagem de entregas:");
			TypedQuery<Entrega> query3 = manager.createQuery("select e from Entrega e order by e.id", Entrega.class);
			List<Entrega> resultados3 = query3.getResultList();
			for (Entrega e : resultados3) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println("excecao=" + e.getMessage());
		}
		Util.fecharBanco();
	}

	// =================================================
	public static void main(String[] args) {
		new Listar();
	}
	// =================================================
}
