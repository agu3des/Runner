package appconsole;

import regras_negocio.Fachada;

public class Cadastrar {
	private EntityManager manager;

	public Cadastrar(){
		try {
			Fachada.inicializar();
			
			Fachada.criarPedido("17yv84", "01/01/2025",15.69, "Forma de bolo de cenoura");
			Fachada.criarPedido("2753pk", "15/12/2024",100, "Suporte de monitor");
			Fachada.criarPedido("9xy123", "05/12/2024", 50.00, "Caixa organizadora");
            Fachada.criarPedido("d4z687", "10/01/2025", 120.49, "Conjunto de ferramentas");
            Fachada.criarPedido("a1b2c3", "20/12/2024", 30.75, "Cesta de frutas");
			
			
			Fachada.criarEntregador("João");
			Fachada.criarEntregador("Maria");
            Fachada.criarEntregador("Carlos");
            Fachada.criarEntregador("Ana");

			
			Fachada.criarEntrega("158","01/01/2025", "Avenida Vasco da Gama, 1245", "João", "17yv84");
			Fachada.criarEntrega("249", "06/12/2024", "Rua das Flores, 456", "Carlos", "9xy123");
            Fachada.criarEntrega("373", "11/01/2025", "Avenida Central, 789", "Ana", "d4z687");
            Fachada.criarEntrega("416", "21/12/2024", "Praça da Liberdade, 321", "Maria", "a1b2c3");
			
			
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
/**********************************
 * projeto POB
 **********************************/

package appconsole;

import jakarta.persistence.EntityManager;
import modelo.Pedido;
import modelo.Entregador;
import modelo.Entrega;

public class Cadastrar {
	private EntityManager manager;

	public Cadastrar() {
		try {
			manager = Util.conectarBanco();
			System.out.println("Cadastrando entregadores, pedidos e entregas...");
			
			Pedido a1, a2, a3, a4, a5;
			a1 = new Pedido("17yv84", "01/01/2025", 15.69, "Forma de bolo de cenoura");
			a2 = new Pedido("2753pk", "15/12/2024", 100, "Suporte de monitor");
			a3 = new Pedido("9xy123", "05/12/2024", 50.00, "Caixa organizadora");
			a4 = new Pedido("d4z687", "10/01/2025", 120.49, "Conjunto de ferramentas");
			a5 = new Pedido("a1b2c3", "20/12/2024", 30.75, "Cesta de frutas");
			
			Entregador e1, e2, e3, e4;
			e1 = new Entregador("João");
			e2 = new Entregador("Maria");
			e3 = new Entregador("Carlos");
			e4 = new Entregador("Ana");

			Entregador p1, p2, p3, p4;
			t1 = new Entrega("158","01/01/2025", "Avenida Vasco da Gama, 1245", "João", "17yv84");
			t2 = new Entrega("249", "06/12/2024", "Rua das Flores, 456", "Carlos", "9xy123");
			t3 = new Entrega("373", "11/01/2025", "Avenida Central, 789", "Ana", "d4z687");
			t4 = new Entrega("416", "21/12/2024", "Praça da Liberdade, 321", "Maria", "a1b2c3");

			t1.adicionar(a1);
			t1.adicionar(e1);
			t2.adicionar(a3);
			t2.adicionar(e3);
			t3.adicionar(a4);
			t3.adicionar(e4);
			t4.adicionar(a5);
			t4.adicionar(e2);
// nao entendi oq vem abaixo disso			
			manager.getTransaction().begin();
			manager.persist(t1);
			manager.getTransaction().commit();

			manager.getTransaction().begin();
			manager.persist(t2);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			manager.persist(t3);
			manager.getTransaction().commit();
			
			manager.getTransaction().begin();
			manager.persist(a6);
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("excecao=" + e.getMessage());
		}

		Util.fecharBanco();
		System.out.println("\nfim da aplica��o");
	}

	// =================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
	// =================================================

}
