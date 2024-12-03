package appconsole;

import regras_negocio.Fachada;

public class Alterar {

	public Alterar(){
		Fachada.inicializar();
		//alteracao 1
		try {
			Fachada.alterarEntregadorDeEntrega("158", "Ana"); 
			System.out.println("Entregador alterado");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//alteracao 2
		try {
			Fachada.alterarEnderecoEntrega("249", "Avenida João Machado, 246");
			System.out.println("Endereco alterado");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//alteracao 3
		try {
			Fachada.alterarDataEntrega("373", "01/01/2025");
			System.out.println("Data alterada");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//alteracao 4
		try {
			Fachada.alterarNomeEntregador("Maria", "Tadeus");
			System.out.println("Nome do entregador alterado");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}
