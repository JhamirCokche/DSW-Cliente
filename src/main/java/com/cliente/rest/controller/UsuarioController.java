package com.cliente.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cliente.rest.entity.Usuario;



@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	private String REST_USUARIO="http://localhost:8090/usuario/";

	
	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt=new RestTemplate();
			//acceder a la ruta "lista" del servicio de medicamento
			ResponseEntity<Usuario[]> response1=rt.getForEntity(REST_USUARIO+"lista", Usuario[].class);
			model.addAttribute("usuarios",response1.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usuario";
	}	
	
	
}
