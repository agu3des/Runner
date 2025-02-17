package appconsole;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;

public class Consultar {
    protected EntityManager manager;

    public Consultar() {
        try {
            manager = Util.conectarBanco();
            TypedQuery<Pedido> queryPedido;
            TypedQuery<Entregador> queryEntregador;
            TypedQuery<Entrega> queryEntrega;
            List<Pedido> pedidos;
            List<Entregador> entregadores;
            List<Entrega> entregas;
            String jpql;

            System.out.println("\n---Consultar pedidos que tenham 17 no seu id");
            jpql = "select p from Pedido p where p.id = 17";
            queryPedido = manager.createQuery(jpql, Pedido.class);
            pedidos = queryPedido.getResultList();
            pedidos.forEach(System.out::println);

            System.out.println("\n---Consultar entregadores com nome contendo 'An'");
            jpql = "select e from Entregador e where e.nome like '%An%'";
            queryEntregador = manager.createQuery(jpql, Entregador.class);
            entregadores = queryEntregador.getResultList();
            entregadores.forEach(System.out::println);

            System.out.println("\n---Consultar entregas que tenham 1 no seu id");
            jpql = "select e from Entrega e where e.id = 1";
            queryEntrega = manager.createQuery(jpql, Entrega.class);
            entregas = queryEntrega.getResultList();
            entregas.forEach(System.out::println);

            System.out.println("\n---Consultar entregas com a data 06/12/2024");
            jpql = "select e from Entrega e where e.data = '2024-12-06'";
            queryEntrega = manager.createQuery(jpql, Entrega.class);
            entregas = queryEntrega.getResultList();
            entregas.forEach(System.out::println);

            System.out.println("\n---Consultar entregadores com mais de 1 entrega");
            jpql = "select e from Entregador e where size(e.entregas) > 1";
            queryEntregador = manager.createQuery(jpql, Entregador.class);
            entregadores = queryEntregador.getResultList();
            entregadores.forEach(System.out::println);

            System.out.println("\n---Consultar pedidos com valor de 100 reais");
            jpql = "select p from Pedido p where p.valor = 100.00";
            queryPedido = manager.createQuery(jpql, Pedido.class);
            pedidos = queryPedido.getResultList();
            pedidos.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }
        
        Util.fecharBanco();
        System.out.println("\nFim da aplicação");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}


