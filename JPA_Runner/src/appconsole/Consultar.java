package appconsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class Consultar {

    public Consultar() {
        try {
            Fachada.inicializar();

            System.out.println("\n---Consultar pedidos que tenham '17yv84' no seu codigo");
            for (Pedido p : Fachada.listarPedidos("17yv84")) {  
                System.out.println(Fachada.localizarPedido(p.getCodigoPedido()));
            }

            System.out.println("\n---Consultar entregadores com nome contendo 'An'");
            for (Entregador e : Fachada.listarEntregadores("An")) {  
                System.out.println(Fachada.localizarEntregador(e.getNome()));
            }

            System.out.println("\n---Consultar entregas que tenham código = 3bdg73");
            for (Entrega e : Fachada.listarEntregas("3bdg73")) {  
                System.out.println(Fachada.localizarEntrega(e.getCodigoEntrega()));
            }

            System.out.println("\n---Consultar entregas com data 14/03/2025");
            for (Entrega e : Fachada.consultarEntregaPorData(LocalDate.parse("14/03/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                System.out.println(Fachada.localizarEntrega(e.getCodigoEntrega()));
            }
            //Verificar lógica
            System.out.println("\n---Consultar entregadores com mais de 1 entrega");
            for (Entregador e : Fachada.consultarPorNEntregas(1)) {
                System.out.println(Fachada.localizarEntregador(e.getNome()));
            }

            System.out.println("\n---Consultar pedidos com valor de 100 reais");
            for (Pedido p : Fachada.consultarPedidoPorValor(100.00)) {
                System.out.println(Fachada.localizarPedido(p.getCodigoPedido()));
            }

        } catch (Exception e) {
            System.out.println("Exceção: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("\nFim da aplicação");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}