package appconsole;

import jakarta.persistence.EntityManager;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;

public class Cadastrar {
    private EntityManager manager;

    public Cadastrar() {
        try {
            manager = Util.conectarBanco();
            System.out.println("Cadastrando entregadores, pedidos e entregas...");

            // Criando pedidos
            Pedido p1 = new Pedido(15.69, "Forma de bolo de cenoura");
            Pedido p2 = new Pedido(100, "Suporte de monitor");
            Pedido p3 = new Pedido(50, "Caixa organizadora");
            Pedido p4 = new Pedido(120.49, "Conjunto de ferramentas");
            Pedido p5 = new Pedido(42, "Saia");

            // Criando entregadores
            Entregador er1 = new Entregador("João");
            Entregador er2 = new Entregador("Maria");
            Entregador er3 = new Entregador("Carlos");
            Entregador er4 = new Entregador("Ana");

            // Criando entregas
            Entrega e1 = new Entrega("Avenida Vasco da Gama, 1245");
            Entrega e2 = new Entrega("Rua das Flores, 456");
            Entrega e3 = new Entrega("Avenida Central, 789");
            Entrega e4 = new Entrega("Praça da Liberdade, 321");
            Entrega e5 = new Entrega("Rua Tavares de Lima, 298");
            
            
            
            // Relacionamentos Entregas - Pedidos
            e1.adicionar(p1);
			e1.adicionar(p2);
			e2.adicionar(p3);
			e3.adicionar(p4);
			e4.adicionar(p5);
            
            
            // Relacionamentos Entregadores - Entregas
            er1.adicionar(e1);
			er4.adicionar(e2);
			er2.adicionar(e3);
			er3.adicionar(e4);
			er4.adicionar(e5);
			

			// Pedidos
			
            manager.getTransaction().begin();
            manager.persist(p1);
            manager.persist(p2);
            manager.persist(p3);
            manager.persist(p4);
            manager.persist(p5);
            manager.getTransaction().commit();
            
            // Entregadores
            manager.getTransaction().begin();
            manager.persist(er1);
            manager.persist(er2);
            manager.persist(er3);
            manager.persist(er4);
            manager.getTransaction().commit();
            
            // Entregas
            manager.getTransaction().begin();
            manager.persist(e1);
            manager.persist(e2);
            manager.persist(e3);
            manager.persist(e4);
            manager.persist(e5);
            manager.getTransaction().commit();
            

        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }

        Util.fecharBanco();
        System.out.println("\nFim da aplicação");
    }

    // =================================================
    public static void main(String[] args) {
        new Cadastrar();
    }
}
