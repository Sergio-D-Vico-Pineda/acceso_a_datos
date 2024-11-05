
// ProductoVicoPinedaSergio.java
public class Producto {

	private String nombre;
	private double precio;
	private int cantidadStock;
	private double precioTotal;

	// Constructor de nombre, precio y cantidadStock

	public Producto(String nombre, double precio, int cantidadStock) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidadStock = cantidadStock;
	}

	// getters y setters de nombre, precio y cantidadStock

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setCantidadStock(int cantidadStock) {
		this.cantidadStock = cantidadStock;
	}

	public int getCantidadStock() {
		return this.cantidadStock;
	}

	/*
	 * método para modificar cantidadStock que devuelve un boolean y que recibe un
	 * valor booleano y un valor entero
	 * el valor booleano recibido, si es true suma el valor entero a cantidadStock,
	 * si es false, lo restará.
	 * Se deberá considerar que no podemos tener stock negativo, por lo que si la
	 * resta diera menos de 0 debemos devolver
	 * false, en cualquier otro caso devolverá true
	 */

	public boolean modCantidadStock(boolean sumar, int cantidad) {
		boolean positivo = true;

		if (sumar) {
			// sumar
			this.cantidadStock += cantidad;
		} else {
			// restar
			if ((this.cantidadStock - cantidad) < 0) {
				positivo = false;
			} else {
				this.cantidadStock -= cantidad;
			}
		}

		return positivo;
	}

	/*
	 * método mostrarInfo que no devuelve nada y deberá imprimir el nombre del
	 * producto, su precio, la cantidad en stock
	 * restante y el total al que equivale la suma de todos los artículos. No se
	 * podrá calcular el precio total del total
	 * del stock de este producto en este método, sino que se hará en un método a
	 * parte.
	 */

	public void mostrarInfo() {
		System.out.println("Nombre: " + this.nombre);
		System.out.println("Precio: " + this.precio);
		System.out.println("Cantidad de stock: " + this.cantidadStock);
		calcularTotal();
		System.out.println("Precio total: " + this.precioTotal);
	}

	/*
	 * método calcularTotal no devuelve nada, pero modifica precioTotal contenga el
	 * valor total en inventario del producto
	 */

	public void calcularTotal() {
		this.precioTotal = this.precio * this.cantidadStock;
	}

	public double getPrecioTotal() {
		calcularTotal();
		return this.precioTotal;
	}

	/*
	 * método actualizarPrecio no debe devolver nada, pero recibe un double que será
	 * nuestro porcentaje por el cual se
	 * aplicará el porcentaje al producto, tanto si es negativo (menor valor) como
	 * si es positivo(mayor valor) a continuación
	 * os dejo una fórmula ejemplo: nuevoPrecio = precioAntiguo +
	 * (precioAntiguo*(porcentaje/100))
	 */

	public void actualizarPrecio(double porcentaje) {
		this.precio = this.precio + (this.precio * (porcentaje / 100));

		// this.precio = this.precio * (100 + porcentaje)
	}
}