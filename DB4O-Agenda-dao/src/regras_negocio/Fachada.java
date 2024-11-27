package regras_negocio;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOEntrega;
import daodb4o.DAOEntregador;
import daodb4o.DAOPedido;
import modelo.Aluno;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import modelo.Pessoa;
import modelo.Telefone;

public class Fachada {
	private Fachada() {}

	private static DAOPedido daopedido = new DAOPedido();
	private static DAOEntregador daoentregador = new DAOEntregador();
	private static DAOEntrega daoentrega = new DAOEntrega();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Pedido localizarPedido(int idPedido) throws Exception {
		Pedido p = daopedido.read(idPedido);
		if (p == null) {
			throw new Exception("Pedido inexistente:" + idPedido);
		}
		return p;
	}
	public static Entregador localizarEntregador(String nome) throws Exception {
		Entregador e = daoentregador.read(nome);
		if (e == null) {
			throw new Exception("Entregador inexistente:" + nome);
		}
		return e;
	}
	
	public static Entrega localizarEntrega(int idEntrega) throws Exception {
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			throw new Exception("Entrega inexistente:" + idEntrega);
		}
		return e;
	}


	public static void criarPedido(int idPedido, String dataPedido, double valor, String descricao) throws Exception {
		DAO.begin();
		try {
			//verificar se tem que fazer verificacao do valor
			LocalDate.parse(dataPedido, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("Formato data invalido:" + dataPedido);
		}
		Pedido p = daopedido.read(idPedido);
		if (p != null) {
			DAO.rollback();
			throw new Exception("Criar pedido - pedido ja existe:" + idPedido);
		}
		p = new Pedido(idPedido);
		p.setDataPedido(dataPedido);
		p.setValor(valor);
		p.setDescricao(descricao);
		daopedido.create(p);
		DAO.commit();
	}

	public static void criarEntregador(int idEntregador, String nome, List<Entrega> entregas) throws Exception {
		DAO.begin();

		Entregador en = daoentregador.read(nome); 
		if (en != null) {
			DAO.rollback();
			throw new Exception("Criar entregador - nome ja existe:" + nome);
		}

		Entregador e = new Entregador(nome);
		e.setNome(nome);
		e.setEntregas(entregas);
		daoentregador.create(e);
		DAO.commit();
	}
	
	public static void criarEntrega(int idEntrega, String dataEntrega, String endereco, List<Entregador> entregadores, List<Pedido> pedidos) throws Exception {
		DAO.begin();
		try {
			//verificar se tem que fazer verificacao do valor
			LocalDate.parse(dataEntrega, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			DAO.rollback();
			throw new Exception("Formato data invalido:" + dataEntrega);
		}
		Entrega e = daoentrega.read(idEntrega);
		if (e != null) {
			DAO.rollback();
			throw new Exception("Criar entrega - pedido ja existe:" + idEntrega);
		}
		e = new Entrega(idEntrega);
		e.setDataEntrega(dataEntrega);
		e.setEndereco(endereco);
		e.setEntregadores(entregadores);
		e.setPedidos(pedidos);
		daoentrega.create(e);
		DAO.commit();
	}

	public static void alterarPessoa(String nome, String data, List<String> apelidos) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}

		p.setApelidos(apelidos);
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				p.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar pessoa - formato data invalido:" + data);
			}
		}

		daopessoa.update(p);
		DAO.commit();
	}

	public static void alterarAluno(String nome, String data, List<String>  apelidos, double nota) throws Exception {
		// permite alterar data, foto e apelidos
		DAO.begin();
		Aluno a = daoaluno.read(nome);
		if (a == null) {
			DAO.rollback();
			throw new Exception("alterar aluno - nome inexistente:" + nome);
		}

		a.setApelidos(apelidos);
		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				a.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar aluno - formato data invalido:" + data);
			}
		}
		a.setNota(nota);
		daopessoa.update(a);
		DAO.commit();
	}

	public static void alterarData(String nome, String data) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar pessoa - pessoa inexistente:" + nome);
		}

		if (data != null) {
			try {
				LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				p.setDtNascimento(data);
			} catch (DateTimeParseException e) {
				DAO.rollback();
				throw new Exception("alterar data - formato data invalido:" + data);
			}
		}

		daopessoa.update(p);
		DAO.commit();
	}

	public static void alterarNome(String nome, String novonome) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome); // usando chave primaria
		if (p == null) {
			DAO.rollback();
			throw new Exception("alterar nome - nome inexistente:" + nome);
		}
		p.setNome(novonome);
		daopessoa.update(p);
		DAO.commit();
	}

	public static void excluirPessoa(String nome) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("excluir pessoa - nome inexistente:" + nome);
		}

		// desligar a pessoa de seus telefones orfaos e apaga-los do banco
		for (Telefone t : p.getTelefones()) {
			daotelefone.delete(t); // deletar o telefone orfao
		}

		daopessoa.delete(p); // apagar a pessoa
		DAO.commit();
	}

	public static void criarTelefone(String nome, String numero) throws Exception {
		DAO.begin();
		Pessoa p = daopessoa.read(nome);
		if (p == null) {
			DAO.rollback();
			throw new Exception("criar telefone - nome inexistente" + nome + numero);
		}
		Telefone t = daotelefone.read(numero);
		if (t != null) {
			DAO.rollback();
			throw new Exception("criar telefone - numero ja cadastrado:" + numero);
		}
		if (numero.isEmpty()) {
			DAO.rollback();
			throw new Exception("criar telefone - numero vazio:" + numero);
		}

		t = new Telefone(numero);
		p.adicionar(t);
		daotelefone.create(t);
		DAO.commit();
	}

	public static void excluirTelefone(String numero) throws Exception {
		DAO.begin();
		Telefone t = daotelefone.read(numero);
		if (t == null) {
			DAO.rollback();
			throw new Exception("excluir telefone - numero inexistente:" + numero);
		}
		Pessoa p = t.getPessoa();
		p.remover(t);
		t.setPessoa(null);
		daopessoa.update(p);
		daotelefone.delete(t);
		DAO.commit();
	}

	public static void alterarNumero(String numero, String novonumero) throws Exception {
		DAO.begin();
		Telefone t1 = daotelefone.read(numero);
		if (t1 == null) {
			DAO.rollback();
			throw new Exception("alterar numero - numero inexistente:" + numero);
		}
		Telefone t2 = daotelefone.read(novonumero);
		if (t2 != null) {
			DAO.rollback();
			throw new Exception("alterar numero - novo numero ja existe:" + novonumero);
		}
		if (novonumero.isEmpty()) {
			DAO.rollback();
			throw new Exception("alterar numero - novo numero vazio:");
		}

		t1.setNumero(novonumero); // substituir
		daotelefone.update(t1);
		DAO.commit();
	}

	public static List<Pessoa> listarPessoas() {
		List<Pessoa> result = daopessoa.readAll();
		return result;
	}

	public static List<Aluno> listarAlunos() {
		List<Aluno> result = daoaluno.readAll();
		return result;
	}

	public static List<Telefone> listarTelefones() {
		List<Telefone> result = daotelefone.readAll();
		return result;
	}

	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	public static List<Pessoa> consultarPessoas(String caracteres) {
		List<Pessoa> result;
		if (caracteres.isEmpty())
			result = daopessoa.readAll();
		else
			result = daopessoa.readAll(caracteres);
		return result;
	}


	public static List<Telefone> consultarTelefones(String digitos) {
		List<Telefone> result;
		if (digitos.isEmpty())
			result = daotelefone.readAll();
		else
			result = daotelefone.readAll(digitos);
		return result;
	}

	public static List<Pessoa> consultarMesNascimento(String mes) {
		List<Pessoa> result;
		result = daopessoa.readByMes(mes);
		return result;
	}

	public static List<Pessoa> consultarPessoasNTelefones(int n) {
		List<Pessoa> result;
		DAO.begin();
		result = daopessoa.readByNTelefones(n);
		DAO.commit();
		return result;
	}

	public static boolean temTelefoneFixo(String nome) {
		return daopessoa.temTelefoneFixo(nome);
	}

	public static List<Pessoa> consultarApelido(String ap) {
		return daopessoa.consultarApelido(ap);
	}

}
