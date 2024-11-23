package modelo;


public class Pedido {
	
	private int idPedido;
    private String datapedido;	
    private double valor;
	private String descricao;

	//incluir valor e descrição??
	public Pedido(int id) {
		this.idPedido = id;
	}
	
	
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int id) {
		this.idPedido = id;
	}
	
	
	public String getDataPedido() {
		return datapedido;
	}
	public void setDataPedido(String dt) {
		datapedido = dt ; 
	}
	
	
	public double getValor() {
		return valor;
	}
	public void setValor(double novoValor) {
		this.valor = novoValor;
	}
	
	
    public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String novaDescricao) {
		this.descricao = novaDescricao;
	}

	
	public String toString() {
		String texto = "id" + idPedido+" DataPedido=" +  getDataPedido() + ", Valor=" + getValor()  + ", Descrição=" + getDescricao();

		return texto;
	}
}