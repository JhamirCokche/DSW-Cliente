package com.cliente.rest.entity;

import java.io.Serializable;

public class Carrito implements Serializable {

	private int codigo;
	private String producto;
	private String cantidad;
	private String preciototal;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPreciototal() {
		return preciototal;
	}
	public void setPreciototal(String preciototal) {
		this.preciototal = preciototal;
	}
	
	
	
	
}
