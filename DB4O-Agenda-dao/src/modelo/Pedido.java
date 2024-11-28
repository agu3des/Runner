package modelo;


public class Pedido {
	
	private int id;
	private String idPedido;
    private String datapedido;	
    private double valor;
	private String descricao;

	
	public Pedido(String id) {
		this.idPedido = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(String id) {
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
	public void setValor(double valor) {
		this.valor = valor ; 
	}
	
	
    public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String novaDescricao) {
		this.descricao = novaDescricao;
	}

	
	public String toString() {
		String texto = "Id:" + idPedido + " DataPedido: " +  getDataPedido() + ", Valor: " + getValor()  + ", Descrição: " + getDescricao() + ", Entrega: " + getEntrega();

		return texto;
	}
}