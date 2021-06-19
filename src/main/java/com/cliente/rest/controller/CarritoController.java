package com.cliente.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boleta")
public class CarritoController {

	@RequestMapping("/")
	public String verBoleta() {
		return "Carrito";
	}  
}
