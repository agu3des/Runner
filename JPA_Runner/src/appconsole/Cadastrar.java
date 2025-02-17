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
            Pedido a1 = new Pedido("17yv84", "01/01/2025", 15.69, "Forma de bolo de cenoura");
            Pedido a2 = new Pedido("2753pk", "15/12/2024", 100, "Suporte de monitor");
            Pedido a3 = new Pedido("9xy123", "05/12/2024", 50.00, "Caixa organizadora");
            Pedido a4 = new Pedido("d4z687", "10/01/2025", 120.49, "Conjunto de ferramentas");
            Pedido a5 = new Pedido("a1b2c3", "20/12/2024", 30.75, "Cesta de frutas");

            // Criando entregadores
            Entregador e1 = new Entregador("João");
            Entregador e2 = new Entregador("Maria");
            Entregador e3 = new Entregador("Carlos");
            Entregador e4 = new Entregador("Ana");

            // Criando entregas
            Entrega t1 = new Entrega("158", "01/01/2025", "Avenida Vasco da Gama, 1245", e1, a1);
            Entrega t2 = new Entrega("249", "06/12/2024", "Rua das Flores, 456", e3, a3);
            Entrega t3 = new Entrega("373", "11/01/2025", "Avenida Central, 789", e4, a4);
            Entrega t4 = new Entrega("416", "21/12/2024", "Praça da Liberdade, 321", e2, a5);

            // Persistindo os dados no banco de uma vez só para otimizar
            manager.getTransaction().begin();
            manager.persist(a1);
            manager.persist(a2);
            manager.persist(a3);
            manager.persist(a4);
            manager.persist(a5);
            manager.persist(e1);
            manager.persist(e2);
            manager.persist(e3);
            manager.persist(e4);
            manager.persist(t1);
            manager.persist(t2);
            manager.persist(t3);
            manager.persist(t4);
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
