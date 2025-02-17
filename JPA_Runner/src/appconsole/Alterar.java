package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Entregador;

public class Alterar {
    private EntityManager manager;

    public Alterar() {
        try {
            manager = Util.conectarBanco();
            manager.getTransaction().begin();

            // Alteração do entregador de uma entrega
            System.out.println("Localizando entrega com ID 158");
            TypedQuery<Entrega> queryEntrega = manager.createQuery("select e from Entrega e where e.id = :id", Entrega.class);
            queryEntrega.setParameter("id", "158");
            Entrega entrega = queryEntrega.getSingleResult();
            System.out.println(entrega);

            System.out.println("Localizando entregador Ana");
            TypedQuery<Entregador> queryEntregador = manager.createQuery("select e from Entregador e where e.nome = :nome", Entregador.class);
            queryEntregador.setParameter("nome", "Ana");
            Entregador entregador = queryEntregador.getSingleResult();
            System.out.println(entregador);

            entrega.setEntregador(entregador);
            System.out.println("Entregador alterado");

            // Alteração do endereço de uma entrega
            System.out.println("Localizando entrega com ID 249");
            queryEntrega.setParameter("id", "249");
            entrega = queryEntrega.getSingleResult();
            System.out.println(entrega);

            entrega.setEndereco("Avenida João Machado, 246");
            System.out.println("Endereço alterado");

            // Alteração da data de uma entrega
            System.out.println("Localizando entrega com ID 373");
            queryEntrega.setParameter("id", "373");
            entrega = queryEntrega.getSingleResult();
            System.out.println(entrega);

            entrega.setData("01/01/2025");
            System.out.println("Data alterada");

            // Alteração do nome de um entregador
            System.out.println("Localizando entregador Maria");
            queryEntregador.setParameter("nome", "Maria");
            entregador = queryEntregador.getSingleResult();
            System.out.println(entregador);

            entregador.setNome("Tadeus");
            System.out.println("Nome do entregador alterado");

            manager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }

        Util.fecharBanco();
        System.out.println("\nFim da aplicação");
    }

    // =================================================
    public static void main(String[] args) {
        new Alterar();
    }
    // =================================================
}


