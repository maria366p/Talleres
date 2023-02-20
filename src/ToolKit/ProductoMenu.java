package ToolKit;

import java.util.List;

public class ProductoMenu implements Producto{
	private String nombre;
	private int precioBase;
	
	//Recuperar variable listamenu de ProductoMenu
	
	Restaurante Restaurante = new Restaurante();
	List<ProductoMenu> ListaMenu = Restaurante.ListaMenu;
	
	
	
	public ProductoMenu(String unProducto, Integer precio) {
		this.nombre= unProducto;
		this.precioBase= precio;
	}
	
	
	
	
	//Getters
	public String getNombre() {
		return nombre;
	}
	
	public int getPrecio() {
		return precioBase;
	}


	//TO-DO: función generar texto Factura
	public String generarTextoFactura() {
		return nombre + "  " + precioBase;
	}
	
	
}
