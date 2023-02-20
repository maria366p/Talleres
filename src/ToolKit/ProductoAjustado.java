package ToolKit;

import java.util.ArrayList;

public class ProductoAjustado implements Producto{
	
	ProductoMenu Base;
	ArrayList<Ingrediente> agregados;
	ArrayList<Ingrediente> eliminados;
	
	
	public ProductoAjustado(ProductoMenu Base) {
		this.Base = Base;
		this.agregados = new ArrayList<Ingrediente>();
		this.eliminados = new ArrayList<Ingrediente>();
		
		
	}
	
	public ArrayList<Ingrediente> RemoveIngrediente(Ingrediente NoDeseado) {
		eliminados.add(NoDeseado);
		return eliminados;
	}
	
	public ArrayList<Ingrediente> AddIngrediente(Ingrediente Deseado) {
		agregados.add(Deseado);
		return eliminados;
	}
	

	@Override
	public int getPrecio() {
		int Precio = Base.getPrecio(); //Precio inicial del producto
		for (Ingrediente Deseado: agregados) {
			int precioIngDeseado = Deseado.getCostoAdicional();
			Precio += precioIngDeseado;
			
		}
		return Precio;
	}

	@Override
	public String getNombre() {
		return Base.getNombre();
	}

	@Override
	public String generarTextoFactura() {
		String StringFactura = "Producto principal:\n" + Base.getNombre()+ "  " + Base.getPrecio() + "\n";
		if (agregados.size() != 0) {
			StringFactura += " Adicion de: \n" ;
			for (Ingrediente elem: agregados) {
				StringFactura = StringFactura + elem.getName() + "   " + elem.getCostoAdicional() + "\n";
			}
		}
		if (eliminados.size() != 0) {
			StringFactura += " Ingredientes eliminados: \n";
			for (Ingrediente elem: eliminados) {
				StringFactura = StringFactura + elem.getName() + "   " + elem.getCostoAdicional() + "\n";
			}
		}

			
	
		return StringFactura;
		
	}
	
}
