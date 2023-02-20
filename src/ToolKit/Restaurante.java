package ToolKit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Consola.App;




public class Restaurante {
	
	public List<Ingrediente> Ingredientes = new ArrayList<>();
	public List<ProductoMenu> ListaMenu = new ArrayList<>();
	public List<Combo> ListaCombos = new ArrayList<>();
	
	
	public Map<String, Pedido> Pedidos = new HashMap<>();
	
	private Pedido elPedidoActual = null;
	ProductoAjustado ajustadoActual;
	Combo comboActual;
 
	

	// ************************************************************************
	// Constructor
	// ************************************************************************
	
	//Incializar el constructor del Restaurante con el mismo nombre
	
	public Restaurante() {
		
	}
	
	// ************************************************************************
	// Métodos para cargar los archivos
	// ************************************************************************
	
	public void cargarInformacionRestaurante(File archivoIngredientes,File archivoMenu,File archivoCombos )
	throws FileNotFoundException, IOException {
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
	}
	
	public void cargarIngredientes(File archivoIngredientes) throws FileNotFoundException, IOException {
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(archivoIngredientes));	
			String linea = br.readLine();
			
			while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
			{
				//Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				String unIngrediente = partes[0];
				int elPrecio = Integer.parseInt(partes[1]);
				
				//Si el ingrediente no existe, lo agrega a la lista de Ingredientes
				Ingrediente elIngrediente = buscarIngrediente(Ingredientes, unIngrediente);
				if (elIngrediente == null)
				{
					elIngrediente = new Ingrediente(unIngrediente,elPrecio);
					Ingredientes.add(elIngrediente);
				}
				
				linea = br.readLine(); // Leer la siguiente línea
				
			}			
	}
	
	
	
	public void cargarMenu(File archivoMenu) throws FileNotFoundException, IOException {
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(archivoMenu));	
			String linea = br.readLine();
			
			while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
			{
				//Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				String unMenu = partes[0];
				int elPrecio = Integer.parseInt(partes[1]);
				
				//Crear un nuevo objeto del Menu
				ProductoMenu elMenu  = new ProductoMenu(unMenu,elPrecio);
				ListaMenu.add(elMenu);
				
				linea = br.readLine(); // Leer la siguiente línea
				
			}			
		
	}
	
	public void cargarCombos(File archivoCombos) throws FileNotFoundException, IOException {
		// Abrir el archivo y leerlo línea por línea usando un BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(archivoCombos));	
			String linea = br.readLine();
			
			while (linea != null) // Cuando se llegue al final del archivo, linea tendrá el valor null
			{
				//Separar los valores que estaban en una línea
				String[] partes = linea.split(";");
				String unCombo = partes[0];
				double elDescuento = Double.parseDouble(partes[1].replace("%",""))/100;
				String nombreComboPeq = partes[2];
				String papas = partes[3];
				String gaseosa = partes[4];

				
				//Inicializar variable para usar el for
				int preciobase = 0;
				
				for (ProductoMenu elem: ListaMenu) {
					String nombre = elem.getNombre();
					if (nombre.equals(nombreComboPeq) || nombre.equals(papas) || nombre.equals(gaseosa)) {
						int precio = elem.getPrecio();
						preciobase += precio;
					}
				}
				
				//Crear un nuevo objeto del Combo 
				Combo elCombo  = new Combo(unCombo,elDescuento,nombreComboPeq, papas, gaseosa, preciobase);
				ListaCombos.add(elCombo);
				
				linea = br.readLine(); // Leer la siguiente línea
				
			}			
		
	}
	
	
	
	//Método auxiliar para observar si ya existe el producto dentro de la lista
	
		private Ingrediente buscarIngrediente(List<Ingrediente> Ingredientes, String nombreIngrediente)
		{
			Ingrediente elIngrediente = null;

			for (int i = Ingredientes.size() - 1; i >= 0 && elIngrediente == null; i--)
			{
				Ingrediente unIngrediente = Ingredientes.get(i);
				if (unIngrediente.equals(nombreIngrediente))
				{
					elIngrediente = unIngrediente;
					return elIngrediente;
				}
			}

			return elIngrediente;
			
		}
	
	// ************************************************************************
	// Getters
	// ************************************************************************
	
	
	
	//Modificación: No se recibe la ListaMenu si no la posicion del productoBase que se quiere modificar
	public Object getProductoMenu(int iBase) {
		
		boolean encontrado = false;
	    int index = 0;
	    ProductoMenu resultado = ListaMenu.get(0);
	    
	    while (!encontrado && index < ListaMenu.size()) {
	    	ProductoMenu objetoActual = ListaMenu.get(index);
	        if (index+1 == iBase) {
	            encontrado = true;
	            resultado = objetoActual;
	        }
	        index++;
	    }
	    
	    if (!encontrado) {
	    	String resultadoStr = "Producto no disponible, intente con uno de los mostrados en el menú";
	    	return resultadoStr;
	    }
	    
	    return resultado;
	}
	
	
	//Modificación: No se recibe la ListaMenu si no el ingrediente que se quiere modificar
	public Object getIngredientes(int iIngrediente) {
		boolean encontrado = false;
	    int index = 0;
	    Ingrediente resultado = Ingredientes.get(0);
	    
	    while (!encontrado && index < Ingredientes.size()) {
	    	Ingrediente objetoActual = Ingredientes.get(index);
	        if (index+1 == iIngrediente) {
	            encontrado = true;
	            resultado = objetoActual;
	        }
	        index++;
	    }
	    
	    if (!encontrado) {
	    	String resultadoStr = "Ingrediente no disponible, intente con uno de los mostrados en el menú";
	    	return resultadoStr;
	    }
		
	    return resultado;
	}
	
	
	//Modificación: Se agregó una función para encontrar el objeto de un combo
	
	public Object getCombo(int icomboBase) {
		boolean encontrado = false;
	    int index = 0;
	    Combo resultado = ListaCombos.get(0);
	    
	    while (!encontrado && index < ListaCombos.size()) {
	    	Combo objetoActual = ListaCombos.get(index);
	        String nombreActual = objetoActual.getNombre(); //nombre de el objeto de la clase ingrediente actual
	        if (index+1 == icomboBase) {
	            encontrado = true;
	            resultado = objetoActual;
	        }
	        index++;
	    }
	    
	    if (!encontrado) {
	    	String resultadoStr = "Combo no disponible, intente con uno de los mostrados en el menú";
	    	return resultadoStr;
	    }
		
	    return resultado;
	}
	
	// ************************************************************************
	// Funciones del pedido
	// ************************************************************************
	
	
	public void iniciarpedido(String nombreCliente, String direccionCliente) {
		if (elPedidoActual == null) {
			elPedidoActual = new Pedido(nombreCliente, direccionCliente);
		}
		
		else {
			System.out.println("Ya hay un pedido en curso, terminelo antes de iniciar uno nuevo");
		}
	
	}
	
	//Modificación: Crear una funcion para crear un nuevo productoajustado
	
	public void crearProductoAjustado(ProductoMenu Base) {
		ajustadoActual = new ProductoAjustado(Base);
	}
	
	//Modificación: Crear una función para añadir un nuevo ingrediente a producto ajustado
	
	public void añadirIngProdAjus(Ingrediente ingrediente) {
		ajustadoActual.AddIngrediente(ingrediente);
		
	}
	
	//Modificación: Crear una función para eliminar un  ingrediente a producto ajustado
	
	public void eliminarIngProdAjus(Ingrediente ingrediente) {
		ajustadoActual.RemoveIngrediente(ingrediente);
		
	}
	//Modificación: Crear una función que devuelve el producto ajustado actual
	
	public ProductoAjustado getProductoAjustadoActual() {
		return ajustadoActual;
	}
	
	
	//Modificación: Agregar item a Combo
	public void agregarItemaCombo(Object object, ProductoMenu itemCombo, int i) {
		Object ComboO = getCombo(i);
		if (ComboO instanceof Combo) {
			Combo Combo = (Combo) ComboO;
			Combo.agregaritemACombo(itemCombo);
		}
		
		else if (ComboO instanceof String) { //en caso de que el producto dentro de menu no esté 
			String novalido = (String) ComboO;
			System.out.println(novalido);
		}
		
	}
	
	public Pedido getPedidoEnCurso() {
		return elPedidoActual;
	}
	
	public void cerraryGuardarPedido() {
		Pedidos.put(Integer.toString(elPedidoActual.getidPedido()),elPedidoActual);
		elPedidoActual = null;
		
		
	}
	
	
	
	
	//Modificación: mostrar todos los productos y combos disponibles
	
		public void getMenuProductos() {
			System.out.println("Productos Individuales Disponibles: \n");
			int numero = 1;
			for (Producto elem: ListaMenu) {
				
				System.out.println(numero + elem.getNombre() + " $" + Integer.toString(elem.getPrecio()));
				numero +=1;
			}
			
			System.out.println("\n");
			
			System.out.println("Combos Disponibles: \n");
			numero = 1;
			for (Producto elem: ListaCombos) {
				System.out.println(numero + " " + elem.getNombre() + " $" + Integer.toString(elem.getPrecio()));
				numero += 1;
				
			}
		}
		
		public void getMenuIngredientes() {
			System.out.println("Ingredientes Disponibles: \n");
			int numero = 1;
			for (Ingrediente elem: Ingredientes) {
				
				System.out.println(numero + elem.getName() + " $" + Integer.toString(elem.getCostoAdicional()));
				numero +=1;
			}
		}
}
	

