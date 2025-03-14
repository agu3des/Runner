package appconsole;

import regras_negocio.Fachada;

public class DeletarTudo
{

    public DeletarTudo() {
        Fachada.inicializar();
        try {
            // Excluindo pedidos
            Fachada.excluirPedido("17yv84");
            System.out.println("Apagou o pedido com ID 17yv84");

            Fachada.excluirPedido("2753pk");
            System.out.println("Apagou o pedido com ID 2753pk");

            Fachada.excluirPedido("9xy123");
            System.out.println("Apagou o pedido com ID 9xy123");

            Fachada.excluirPedido("d4z687");
            System.out.println("Apagou o pedido com ID d4z687");

            Fachada.excluirPedido("a1b2c3");
            System.out.println("Apagou o pedido com ID a1b2c3");
        	
            // Excluindo entregadores
            Fachada.excluirEntregador("João");
            System.out.println("Apagou o entregador João");

            Fachada.excluirEntregador("Ana");
            System.out.println("Apagou o entregador Ana");

            Fachada.excluirEntregador("Tadeus");
            System.out.println("Apagou o entregador Tadeus");
            
            Fachada.excluirEntregador("Carlos");
            System.out.println("Apagou o entregador Carlos");



            // Excluindo entregas
            Fachada.excluirEntrega("158");
            System.out.println("Apagou a entrega com ID 158");

            Fachada.excluirEntrega("249");
            System.out.println("Apagou a entrega com ID 249");

            Fachada.excluirEntrega("373");
            System.out.println("Apagou a entrega com ID 373");

            Fachada.excluirEntrega("416");
            System.out.println("Apagou a entrega com ID 416");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("Fim do programa");
    }

    // =================================================
    public static void main(String[] args) {
        new DeletarTudo();
    }
}
