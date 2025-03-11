package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import daojpa.DAO;
import daojpa.DAOEntrega;
import daojpa.DAOEntregador;
import daojpa.DAOPedido;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import modelo.Pessoa;

public class Fachada {
	private Fachada() {
	}

	private static DAOPedido daopedido = new DAOPedido();
	private static DAOEntregador daoentregador = new DAOEntregador();
	private static DAOEntrega daoentrega = new DAOEntrega();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}

	public static Pedido localizarPedido(String codigoPedido) throws Exception {
		Pedido p = daopedido.read(codigoPedido);
		if (p == null) {
			throw new Exception("Pedido inexistente:" + codigoPedido);
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

	public static Entrega localizarEntrega(String codigoEntrega) throws Exception {
		Entrega e = daoentrega.read(codigoEntrega);
		if (e == null) {
			throw new Exception("Entrega inexistente:" + codigoEntrega);
		}
		return e;
	}

	public static void criarPedido(String codigoPedido, LocalDate dataPedido, double valor, String descricao) throws Exception {
		DAO.begin();

		Pedido p = daopedido.read(codigoPedido);
		if (p != null) {
			DAO.rollback();
			throw new Exception("Criar pedido - pedido ja existe:" + codigoPedido);
		}

		p = new Pedido(codigoPedido);
		p.setDataPedido(dataPedido);
		p.setValor(valor);
		p.setDescricao(descricao);

		daopedido.create(p);
		DAO.commit();
	}

	public static void criarEntregador(String nome) throws Exception {
		DAO.begin();

		Entregador en = daoentregador.read(nome);
		if (en != null) {
			DAO.rollback();
			throw new Exception("Criar entregador - nome ja existe:" + nome);
		}

		Entregador e = new Entregador(nome);
		e.setNome(nome);

		daoentregador.create(e);

		DAO.commit();
	}

	public static void criarEntrega(String codigoEntrega, LocalDate dataEntrega, String endereco, String entregador,
			String pedido) throws Exception {
		DAO.begin();

		Entrega e = daoentrega.read(codigoEntrega);
		if (e != null) {
			DAO.rollback();
			throw new Exception("Criar entrega - pedido ja existe:" + codigoEntrega);
		}
		e = new Entrega(codigoEntrega);
		e.setDataEntrega(dataEntrega);
		e.setEndereco(endereco);

		Entregador en = daoentregador.read(entregador);
		if (en != null && en.getEntregas().size() < 5) {
			en.adicionar(e);
		} else {
			DAO.rollback();
			throw new Exception("Criar entrega - entregador não existe:" + entregador);
		}
		e.setEntregador(en);

		Pedido p = daopedido.read(pedido);
		if (p != null) {
			e.adicionar(p);
		} else {
			DAO.rollback();
			throw new Exception("Criar entrega - pedido não existe:" + pedido);
		}
		p.setEntrega(e);

		daoentrega.create(e);
		DAO.commit();
	}

	public static void alterarEntregadorDeEntrega(String codigoEntrega, String entregador) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(codigoEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + codigoEntrega);
		}

		Entregador entregadorAntigo = e.getEntregador();
		if (entregadorAntigo != null) {
			entregadorAntigo.remover(e);
		}

		Entregador en = daoentregador.read(entregador);

		if (en != null && en.getEntregas().size() < 5) {
			en.adicionar(e);
		} else {
			DAO.rollback();
			throw new Exception("Criar entrega - entregador inválido:" + entregador);
		}

		e.setEntregador(en);

		daoentrega.update(e);
		daoentregador.update(en);
		daoentregador.update(entregadorAntigo);
		DAO.commit();
	}

	public static void alterarDataEntrega(String codigoEntrega, LocalDate dataEntrega) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(codigoEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + codigoEntrega);
		}

		if (e.getDataEntrega() != null) {
			try {
				e.setDataEntrega(dataEntrega);
			} catch (DateTimeParseException en) {
				DAO.rollback();
				throw new Exception("Alterar data - formato data invalido:" + dataEntrega);
			}
		}

		daoentrega.update(e);
		DAO.commit();
	}

	public static void alterarEnderecoEntrega(String codigoEntrega, String endereco) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(codigoEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + codigoEntrega);
		}

		if (e.getEndereco() != null) {
			e.setEndereco(endereco);
		}

		daoentrega.update(e);
		DAO.commit();
	}

	public static void alterarNomeEntregador(String nome, String novoNome) throws Exception {
		DAO.begin();
		Entregador e = daoentregador.read(nome);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar nome - nome inexistente:" + nome);
		}
		e.setNome(novoNome);
		daoentregador.update(e);
		DAO.commit();
	}

	public static void excluirPedido(String codigoPedido) throws Exception {
		DAO.begin();
		Pedido p = daopedido.read(codigoPedido);
		if (p == null) {
			DAO.rollback();
			throw new Exception("Excluir pedido - id inexistente:" + codigoPedido);
		}

		daopedido.delete(p);
		DAO.commit();
	}

	public static void excluirEntregador(String nome) throws Exception {
		DAO.begin();
		Entregador e = daoentregador.read(nome);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Excluir entregador - nome inexistente:" + nome);
		}

		daoentregador.delete(e);
		DAO.commit();
	}

	public static void excluirEntrega(String codigoEntrega) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(codigoEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Excluir entrega - id inexistente:" + codigoEntrega);
		}

		daoentrega.delete(e);
		DAO.commit();
	}

	public static List<Pedido> listarPedidos() {
		DAO.begin();
		List<Pedido> result = daopedido.readAll();
		DAO.commit();
		return result;
	}
	
	public static List<Pedido> listarPedidos(String codigoPedido) {
		List<Pedido> result;
		DAO.begin();
		result = daopedido.readByCodigo(codigoPedido);
		DAO.commit();
		return result;
	}

	public static List<Entregador> listarEntregadores() {
		DAO.begin();
		List<Entregador> result = daoentregador.readAll();
		DAO.commit();
		return result;
	}

	public static List<Entrega> listarEntregas() {
		DAO.begin();
		List<Entrega> result = daoentrega.readAll();
		DAO.commit();
		return result;
	}

	public static List<Entrega> listarEntregas(String codigoEntrega) {
		List<Entrega> result;
		DAO.begin();
		result = daoentrega.readByCodigo(codigoEntrega);
		DAO.commit();
		return result;
	}
	
	public static List<Pedido> consultarPedidos(String pedidos) {
		List<Pedido> result;
		if (pedidos.isEmpty())
			result = daopedido.readAll();
		else
			result = daopedido.readAll();
		return result;
	}

	public static List<Entregador> consultarEntregadores(String entregadores) {
		List<Entregador> result;
		if (entregadores.isEmpty())
			result = daoentregador.readAll();
		else
			result = daoentregador.readAll();
		return result;
	}
	
	public static List<Entregador> listarEntregadores(String nome) {
		List<Entregador> result;
		DAO.begin();
		result = daoentregador.readLikeNome(nome);
		DAO.commit();
		return result;
	}


	public static List<Entrega> consultarEntregas(String entregas) {
		List<Entrega> result;
		if (entregas.isEmpty())
			result = daoentrega.readAll();
		else
			result = daoentrega.readAll();
		return result;
	}

	public static List<Entregador> consultarPorNEntregas(int n) {
		List<Entregador> result;
		DAO.begin();
		result = daoentregador.readByNEntregas(n);
		DAO.commit();
		return result;
	}
	
	public static List<Entrega> consultarPorNPedidos(int n) {
		List<Entrega> result;
		DAO.begin();
		result = daoentrega.readByNPedidos(n);
		DAO.commit();
		return result;
	}

	public static List<Entrega> consultarEntregaPorData(LocalDate data) {
		List<Entrega> result;
		result = daoentrega.readByData(String.valueOf(data));
		return result;
	}

	public static boolean temDataDiferente(LocalDate data) {
		return daoentrega.dataEhDiferente(String.valueOf(data));
	}

	public static List<Pedido> consultarPedidoPorValor(double valor) {
		return daopedido.readByValor(valor);
	}

}
