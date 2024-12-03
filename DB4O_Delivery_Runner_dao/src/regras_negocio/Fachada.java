package regras_negocio;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOEntrega;
import daodb4o.DAOEntregador;
import daodb4o.DAOPedido;
import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;


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

	public static Pedido localizarPedido(String idPedido) throws Exception {
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
	
	public static Entrega localizarEntrega(String idEntrega) throws Exception {
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			throw new Exception("Entrega inexistente:" + idEntrega);
		}
		return e;
	}


	public static void criarPedido(String idPedido, String dataPedido, double valor, String descricao) throws Exception {
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
	
	public static void criarEntrega(String idEntrega, String dataEntrega, String endereco, String entregador, String pedido) throws Exception {
		DAO.begin();
		
		try {
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
			e.setPedido(p);
		}
		
		
		daoentrega.create(e);
		DAO.commit();
	}

	public static void alterarEntregadorDeEntrega(String idEntrega, String entregador) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + idEntrega);
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


	public static void alterarDataEntrega(String idEntrega, String dataEntrega) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + idEntrega);
		}

		if (e.getdataEntrega() != null) {
			try {
				LocalDate.parse(dataEntrega, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				e.setDataEntrega(dataEntrega);
			} catch (DateTimeParseException en) {
				DAO.rollback();
				throw new Exception("Alterar data - formato data invalido:" + dataEntrega);
			}
		}

		daoentrega.update(e);
		DAO.commit();
	}
	
	public static void alterarEnderecoEntrega(String idEntrega, String endereco) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Alterar entrega - entrega inexistente:" + idEntrega);
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
	
	public static void excluirPedido(String idPedido) throws Exception {
		DAO.begin();
		Pedido p = daopedido.read(idPedido);
		if (p == null) {
			DAO.rollback();
			throw new Exception("Excluir pedido - id inexistente:" + idPedido);
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
	
	public static void excluirEntrega(String idEntrega) throws Exception {
		DAO.begin();
		Entrega e = daoentrega.read(idEntrega);
		if (e == null) {
			DAO.rollback();
			throw new Exception("Excluir entrega - id inexistente:" + idEntrega);
		}

		daoentrega.delete(e); 
		DAO.commit();
	}



	public static List<Pedido> listarPedidos() {
		List<Pedido> result = daopedido.readAll();
		return result;
	}

	public static List<Entregador> listarEntregadores() {
		List<Entregador> result = daoentregador.readAll();
		return result;
	}

	public static List<Entrega> listarEntregas() {
		List<Entrega> result = daoentrega.readAll();
		return result;
	}
	

	public static List<Pedido> consultarPedidos(String pedidos) {
		List<Pedido> result;
		if (pedidos.isEmpty())
			result = daopedido.readAll();
		else
			result = daopedido.readAll(pedidos);
		return result;
	}


	public static List<Entregador> consultarEntregadores(String entregadores) {
		List<Entregador> result;
		if (entregadores.isEmpty())
			result = daoentregador.readAll();
		else
			result = daoentregador.readAll(entregadores);
		return result;
	}
	
	public static List<Entrega> consultarEntregas(String entregas) {
		List<Entrega> result;
		if (entregas.isEmpty())
			result = daoentrega.readAll();
		else
			result = daoentrega.readAll(entregas);
		return result;
	}

	public static List<Entregador> consultarPorNEntregas(int n) {
		List<Entregador> result;
		DAO.begin();
		result = daoentregador.readByNEntregas(n);
		DAO.commit();
		return result;
	}
	
	public static List<Entrega> consultarEntregaPorData(String data) {
		List<Entrega> result;
		result = daoentrega.readByData(data);
		return result;
	}

	public static boolean temDataDiferente(String data) {
		return daoentrega.dataEhDiferente(data);
	}

	public static List<Pedido> consultarPedidoPorValor(double valor) {
		return daopedido.readByValor(valor);
	}
	




}
