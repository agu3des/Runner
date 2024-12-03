package appconsole;


import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;


public class Consultar {

	public Consultar(){

		try {
			Fachada.inicializar();
			System.out.println("\nConsultar pedidos que tenham 17 no seu id");
			for(Pedido p : Fachada.consultarPedidos("17")) 
				System.out.println(p);

			System.out.println("\n------------------------------------------");
			
			System.out.println("\nConsultar entregadores com nome com An");
			for(Entregador e : Fachada.consultarEntregadores("An")) 
				System.out.println(e);
			
			System.out.println("\n------------------------------------------");

			System.out.println("\nConsultar entregas que tenham 1 no seu id");
			for(Entrega e : Fachada.consultarEntregas("1")) 
				System.out.println(e);
			
			System.out.println("\n------------------------------------------");
			
			System.out.println("\nConsultar entregas com a data 06/12/2024");
			for(Entrega e : Fachada.consultarEntregaPorData("06/12/2024")) 
				System.out.println(e);

			System.out.println("\n------------------------------------------");
			
			System.out.println("\nConsultar por N > 1 entregas" );
			for(Entregador e : Fachada.consultarPorNEntregas(1) ) 
				System.out.println(e);
			
			System.out.println("\n------------------------------------------");
			
			System.out.println("\nConsultar por pedidos com valor de 100 reais" );
			for(Pedido p : Fachada.consultarPedidoPorValor(100.00) ) 
				System.out.println(p);

			System.out.println("\n------------------------------------------");
			
			System.out.println("\nTem data de entrega diferente da data de pedido?\n"+
					Fachada.temDataDiferente("11/01/2025") );



		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
}

