package appconsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import regras_negocio.Fachada;

public class Alterar {

	public Alterar() {
		Fachada.inicializar();
		// alteracao 1
		try {
			Fachada.alterarEntregadorDeEntrega("1deu58", "Ana");
			System.out.println("Entregador alterado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// alteracao 2
		try {
			Fachada.alterarEnderecoEntrega("3hk7t4", "Avenida João Machado, 246");
			System.out.println("Endereco alterado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// alteracao 3
		try {
			Fachada.alterarDataEntrega("24lhy9", LocalDate.parse("01/01/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			System.out.println("Data alterada");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// alteracao 4
		try {
			Fachada.alterarNomeEntregador("Maria", "Tadeus");
			System.out.println("Nome do entregador alterado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("fim do programa");
	}

	// =================================================
	public static void main(String[] args) {
		new Alterar();
	}
}
