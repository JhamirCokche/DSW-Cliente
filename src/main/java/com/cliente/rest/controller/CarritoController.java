package com.cliente.rest.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cliente.rest.entity.Celular;
import com.google.gson.Gson;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
	
	private String REST_CELULAR="http://localhost:8090/celular/";
	private String REST_CARRITO="http://localhost:8090/carrito/";
	

	@RequestMapping("/")
	public String verBoleta() {
		return "Carrito";
	}  
	
	@RequestMapping("/agregar/{codigo}")
	public String agregarCarrito(@PathVariable("codigo") int cod, RedirectAttributes redirect) {
	
			Celular bean=null;
			try {
				RestTemplate rt=new RestTemplate();
				ResponseEntity<Celular> response=rt.getForEntity(REST_CELULAR+"buscar/"+cod, Celular.class);
				bean=response.getBody();

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Gson gson=new Gson();
			String json=gson.toJson(bean);
			//
			RestTemplate rt=new RestTemplate();
			//
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request=new HttpEntity<String>(json,headers);
			
			rt.postForObject(REST_CARRITO+"registrar", request, String.class);
			redirect.addFlashAttribute("MENSAJE","Celular registrado");
		
		
		return "Carrito";
	}
}
