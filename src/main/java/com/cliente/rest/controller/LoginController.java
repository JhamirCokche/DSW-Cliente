package com.cliente.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.cliente.rest.entity.Celular;
import com.cliente.rest.entity.Usuario;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private String REST_LOGIN="http://localhost:8090/login/";
	//private String REST_REGISTRO="http://localhost:8090/registro/";
	
	@GetMapping("/")
	public String login() {
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}

	
	
	@RequestMapping("/validar")
	public String validar(@RequestParam("usuario") String nom, @RequestParam("clave") String contr) {
		
		Usuario bean=null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Usuario> response=rt.getForEntity(REST_LOGIN+"buscar/"+nom+"/"+contr, Usuario.class);
			bean=response.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
			String cad ="" ;
		if(bean != null) {
		if(bean.getTipo_usuario().equals("admin")) {
			cad = "redirect:/celular/";
		}
		if(bean.getTipo_usuario().equals("cliente")) {
			
			cad = "redirect:/celular/catalogo";
		}
		}else
			cad ="redirect:/login/";
		
		return cad;
	}
	

}
