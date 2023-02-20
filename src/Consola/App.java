package Consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import ToolKit.Combo;
import ToolKit.Ingrediente;
import ToolKit.ProductoMenu;
import ToolKit.Restaurante;




public class App {
	
    //public static void main(String[] args) throws Exception {
    	
		/**
		 * Este es el restaurante que se usará para hacer la mayoría de las
		 * operaciones de la aplicación. Este restaurante también contiene toda la
		 * información sobre los menus después de que se cargue desde un archivo.
		 */
		Restaurante restaurante = new Restaurante();
    	
		/**
    	 * Ejecuta la aplicación: le muestra el menú al usuario y la pide que ingrese
    	 * una opción, y ejecuta la opción seleccionada por el usuario. Este proceso se
    	 * repite hasta que el usuario seleccione la opción de abandonar la aplicación.
    	 */
    	public void ejecutarAplicacion()
    	{
    		System.out.println("Bienvenido a Randy's\n");
    		boolean continuar = true;
    		while (continuar)
    		{
	    		try
	    		{
		    		mostrarMenu();
		    		int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
		    		if (opcion_seleccionada == 1) {
			    		ejecutarCargarTodo();
			    		ejecutarInformacionCliente();
		    		}
		    	
		    		else if (opcion_seleccionada == 2 && restaurante != null) {
		    			ejecutarAñadirPedido();
		    			
		    		}
		    		else if (opcion_seleccionada == 3 && restaurante != null) {
		    			
		    		}
		    		else if (opcion_seleccionada == 4)
		    		{
		    			System.out.println("Saliendo de la aplicación ...");
		    			continuar = false;
		    		}
		    		else if (restaurante == null)
		    		{
		    			System.out.println("Para poder ejecutar esta opción primero debe cargar un archivo de menus.");
		    		}
		    		else
		    		{
		    			System.out.println("Por favor seleccione una opción válida.");
		    		}
	    		}
    		catch (NumberFormatException e)
    		{
    			System.out.println("Debe seleccionar uno de los números de las opciones.");
    		}
    	}
    }

    	
    	
    	public void mostrarMenu()
    	{
    	System.out.println("\nOpciones de la aplicación\n");
    	System.out.println("1. Iniciar un nuevo pedido");
    	System.out.println("2. Añadir un nuevo producto");
    	System.out.println("3. Consultar un pedido dado su id");
    	System.out.println("4. Salir de la aplicación\n");
    	}
    	
    	/**
    	* Modificación: Este método hace la carga de los archivos.
    	*/
    	
    	private void ejecutarCargarTodo()
    	{
    	System.out.println("\n" + "Cargar el archivo con la información del restaurante" + "\n");
    	try
    	{
    	File archivoIngredientes = new File("Data/ingredientes.txt");
    	File archivoMenu = new File("Data/menu.txt");
    	File archivoCombos = new File("Data/combos.txt");
    	restaurante.cargarInformacionRestaurante(archivoIngredientes,archivoMenu,archivoCombos);
    	}
    	catch (FileNotFoundException e)
    	{
    	System.out.println("ERROR: el archivo indicado no se encontró.");
    	}
    	catch (IOException e)
    	{
    	System.out.println("ERROR: hubo un problema leyendo el archivo.");
    	System.out.println(e.getMessage());
    	}
    	}

    	private void ejecutarInformacionCliente() {
    		//Informacion personal
    		String nombreCliente = input("Por favor digite su nombre completo");
    		String direccionCliente = input("Por favor digite su dirección de residencia");
    		restaurante.iniciarpedido(nombreCliente, direccionCliente);
    	}

    	
    	private void ejecutarAñadirPedido() {
    		boolean abrir = true;
    		boolean añadir;
    		boolean eliminar;
    		
    		while(abrir){
    			boolean ajustar = false;
    			restaurante.getMenuProductos();
    			int classType = Integer.parseInt(input("¿Desea ordenar un Producto Individual o un Combo? "
    												+ "Seleccione una opción\n1.Producto Individual\n2.Combo"));
    			//Producto Individual
    			if (classType ==1) {
    				int i = Integer.parseInt(input("Seleccione un número de un producto para añadir\n"));
    				int preguntaAñadir = Integer.parseInt(input("Desea añadir más ingredientes\n1.Si\n2.No\n"));
    				if (preguntaAñadir==1) {
    					añadir = true;
    					ajustar = true;
    					//Asignar como ProductoMenu o String lo que devuelva el metodo de la clase restaurante
    					Object BaseO = restaurante.getProductoMenu(i);
    					if (BaseO instanceof ProductoMenu) {
    						ProductoMenu Base = (ProductoMenu) BaseO;
    						restaurante.crearProductoAjustado(Base);
    					}
    					
    					else if (BaseO instanceof String) { //en caso de que el producto dentro de menu no esté 
    						String novalido = (String) BaseO;
    						System.out.println(novalido);
    					}
    				}
    				
    				else {
    					añadir = false;}
    				
    				System.out.println(añadir);
    				
    				//Añadir ingredientes
    				while (añadir) {
    					restaurante.getMenuIngredientes();
    					int iIngrediente = Integer.parseInt(input("Seleccione un número de un producto para añadir\n"));
    					Object ingO = restaurante.getIngredientes(iIngrediente);
    					if (ingO instanceof ProductoMenu) {
    						Ingrediente ingrediente = (Ingrediente) ingO;
    						restaurante.añadirIngProdAjus(ingrediente);
    					}
    					
    					else if (ingO instanceof String) { //en caso de que el producto dentro de menu no esté 
    						String novalido = (String) ingO;
    						System.out.println(novalido);
    					}
    					
    					int preguntaAñadirExtra = Integer.parseInt(input("Desea añadir más ingredientes\n1.Si\n2.No\n"));
    					if (preguntaAñadirExtra == 1) {
    						añadir = true;
    					}
    					else if(preguntaAñadirExtra ==2) {
    						añadir = false;
    					}
    				}
    				
    				//Eliminar ingredientes
    				
    				int preguntaEliminar = Integer.parseInt(input("Desea eliminar ingredientes\n1.Si\n2.No\n"));
    				if (preguntaEliminar == 1) {
    					eliminar = true;
    					if(!ajustar) {
    						//Asignar como ProductoMenu o String lo que devuelva el metodo de la clase restaurante
        					Object BaseO = restaurante.getProductoMenu(i);
        					if (BaseO instanceof ProductoMenu) {
        						ProductoMenu Base = (ProductoMenu) BaseO;
        						restaurante.crearProductoAjustado(Base);
        					}
        					
        					else if (BaseO instanceof String) { //en caso de que el producto dentro de menu no esté 
        						String novalido = (String) BaseO;
        						System.out.println(novalido);
        					}
    					}
    					
    				}
    				
    				else {
    					eliminar = false;
    				}
    				
    				System.out.println("eliminar : " + eliminar);
    				
    				while (eliminar) {
    					restaurante.getMenuIngredientes();
    					int iEliminar = Integer.parseInt(input("\nSeleccione un número de un ingrediente para eliminar\\n "));
    					//Asignar como Ingrediente o String lo que devuelva el metodo de la clase restaurante
    					Object ingO = restaurante.getIngredientes(iEliminar);
    					if (ingO instanceof Ingrediente) {
    						Ingrediente ingrediente = (Ingrediente) ingO;
    						restaurante.eliminarIngProdAjus(ingrediente);
    					}
    					
    					else if (ingO instanceof String) { //en caso de que el producto dentro de menu no esté 
    						String novalido = (String) ingO;
    						System.out.println(novalido);
    					}
    					
    					int continuarEliminando = Integer.parseInt(input("Desea eliminar más ingredientes\n1.Si\n2.No\n"));
    					if (continuarEliminando ==1) {
    						eliminar = true;
    					}
    					else {
    						eliminar = false;
    					}
    				}
    			if (ajustar) {
    				restaurante.getPedidoEnCurso().agregarProducto(restaurante.getProductoAjustadoActual());
    			}
    			else {
    				//Asignar como ProductoMenu o String lo que devuelva el metodo de la clase restaurante
					Object BaseO = restaurante.getProductoMenu(i);
					if (BaseO instanceof ProductoMenu) {
						ProductoMenu Base = (ProductoMenu) BaseO;
						restaurante.getPedidoEnCurso().agregarProducto(Base);
						
					}
					
					else if (BaseO instanceof String) { //en caso de que el producto dentro de menu no esté 
						String novalido = (String) BaseO;
						System.out.println(novalido);
					}
    				
    			}
    			}
    			
    			else if (classType ==2) {
    				int i = Integer.parseInt(input("Seleccione un número de un producto para añadir\n"));
    				int preguntaAñadir = Integer.parseInt(input("Desea añadir más productos\n1.Si\n2.No\n"));
    				if (preguntaAñadir==1) {
    					añadir = true;
    					ajustar = true;
    					//Asignar como ProductoMenu o String lo que devuelva el metodo de la clase restaurante
    					Object ComboO = restaurante.getCombo(i);
    					if (ComboO instanceof Combo) {
    						Combo Combo = (Combo) ComboO;
    					}
    					
    					else if (ComboO instanceof String) { //en caso de que el producto dentro de menu no esté 
    						String novalido = (String) ComboO;
    						System.out.println(novalido);
    					}
    				}
    				
    				else {
    					añadir = false;}
    				//Añadir ingredientes
    				while (añadir) {
    					restaurante.getMenuProductos();
    					int iProducto = Integer.parseInt(input("Seleccione un número de un producto para añadir\n"));
    					Object productoO = restaurante.getProductoMenu(iProducto);
    					if (productoO instanceof ProductoMenu) {
    						ProductoMenu producto = (ProductoMenu) productoO;
    						restaurante.agregarItemaCombo(restaurante.getCombo(i), producto, i);
    					}
    					
    					else if (productoO instanceof String) { //en caso de que el producto dentro de menu no esté 
    						String novalido = (String) productoO;
    						System.out.println(novalido);
    					}
    					
    					int preguntaAñadirExtra = Integer.parseInt(input("Desea añadir más productos? \n1.Si\n2.No\n"));
    					if (preguntaAñadirExtra == 1) {
    						añadir = true;
    					}
    					else if(preguntaAñadirExtra ==2) {
    						añadir = false;
    					}
    				}
    				
    				//Agregar Combo a pedido
    				//Asignar como Combo o String lo que devuelva el metodo de la clase restaurante
					Object ComboO = restaurante.getCombo(i);
					if (ComboO instanceof Combo) {
						Combo Combo = (Combo) ComboO;
						restaurante.getPedidoEnCurso().agregarProducto(Combo);
						
					}
					
					else if (ComboO instanceof String) { //en caso de que el producto dentro de menu no esté 
						String novalido = (String) ComboO;
						System.out.println(novalido);
					}
    			}
    		int cerrar = Integer.parseInt(input("Ordenar algo más?\n1.Si\n2.No\n"));
    		if (cerrar == 1) {
    			abrir = true;
    		}
    		
    		else {
    			abrir = false;
    			System.out.println("\nOrden Cerrada.\n");
    			System.out.println(restaurante.getPedidoEnCurso().generarTextoFactura());
    			restaurante.cerraryGuardarPedido();
    			
    		}
    		}
    			
    			
    		
    		
    				
    	}
    
    	
    	/**
    	* Este método sirve para imprimir un mensaje en la consola pidiéndole
    	* información al usuario y luego leer lo que escriba el usuario.
    	* 
    	* @param mensaje El mensaje que se le mostrará al usuario
    	* @return La cadena de caracteres que el usuario escriba como respuesta.
    	*/
    	public String input(String mensaje)
    	{
	    	try
	    	{
		    	System.out.print(mensaje + ": ");
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    	return reader.readLine();
	    	}
	    	catch (IOException e)
	    	{
		    	System.out.println("Error leyendo de la consola");
		    	e.printStackTrace();
	    	}
	    	return null;
    	}
    	
    	/**
    	 * Este es el método principal de la aplicación, con el que inicia la ejecución
    	 * de la aplicación
    	 * 
    	 * @param args Parámetros introducidos en la línea de comandos al invocar la
    	 *             aplicación
    	 */
    	public static void main(String[] args)
    	{
    		App consola = new App();
    		consola.ejecutarAplicacion();
    	}


}
