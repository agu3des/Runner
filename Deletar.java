package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Pedido;
import java.util.List;

public class Deletar {
    private EntityManager manager;

    public Deletar() {
        try {
            manager = Util.conectarBanco();
            manager.getTransaction().begin();
            
            System.out.println("Localizar e apagar entregadores com nome João");
            TypedQuery<Entregador> queryEntregador = manager.createQuery("select e from Entregador e where e.nome = :nome", Entregador.class);
            queryEntregador.setParameter("nome", "João");
            List<Entregador> entregadores = queryEntregador.getResultList();
            
            for (Entregador entregador : entregadores) {
                System.out.println(entregador);
                manager.remove(entregador);
            }
            
            System.out.println("Localizar entrega com ID 249");
            TypedQuery<Entrega> queryEntrega = manager.createQuery("select e from Entrega e where e.id = :id", Entrega.class);
            queryEntrega.setParameter("id", "249");
            Entrega entrega = queryEntrega.getSingleResult();
            System.out.println(entrega);
            
            System.out.println("Apagar entrega com ID 249");
            manager.remove(entrega);
            
            System.out.println("Localizar pedido com ID 17yv84");
            TypedQuery<Pedido> queryPedido = manager.createQuery("select p from Pedido p where p.id = :id", Pedido.class);
            queryPedido.setParameter("id", "17yv84");
            Pedido pedido = queryPedido.getSingleResult();
            System.out.println(pedido);
            
            System.out.println("Apagar pedido com ID 17yv84");
            manager.remove(pedido);
            
            manager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }
        
        Util.fecharBanco();
        System.out.println("\nFim da aplicação");
    }

    // =================================================
    public static void main(String[] args) {
        new Deletar();
    }
    // =================================================
}


