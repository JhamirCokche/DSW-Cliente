package com.cliente.rest.entity;
import java.io.Serializable;

public class Celular implements Serializable{
	
	private int id_celular;
	private String nombre;
	private int stock;
	private double precio;
	private Marca marca;
	
	
	
	
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public int getId_celular() {
		return id_celular;
	}
	public void setId_celular(int id_celular) {
		this.id_celular = id_celular;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
}
