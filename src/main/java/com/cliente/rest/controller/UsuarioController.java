package com.cliente.rest.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cliente.rest.entity.Celular;
import com.cliente.rest.entity.Usuario;
import com.google.gson.Gson;



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
	
	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod,@RequestParam("nombre") String nom, @RequestParam("apellido") String ape,
			@RequestParam("usuario") String usu,@RequestParam("clave") String cla,
								@RequestParam("direccion") String direc,@RequestParam("correo") String cor,
								@RequestParam("numero") String num, @RequestParam("tipo") String tipusu,
								RedirectAttributes redirect) {
		try {
			Usuario bean=new Usuario();
			bean.setId_usuario(cod);
			bean.setNom_usuario(nom);
			bean.setApe_usuario(ape);
			bean.setUser(usu);
			bean.setDirec_usuario(direc);
			bean.setCorreo_usuario(cor);
			bean.setNum_usuario(num);
			bean.setTipo_usuario(tipusu);
			//json
			Gson gson=new Gson();
			String json=gson.toJson(bean);
			//
			RestTemplate rt=new RestTemplate();
			//
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request=new HttpEntity<String>(json,headers);
			
			//validar
			if(cod==0) {
					rt.postForObject(REST_USUARIO+"registrar", request, String.class);
					redirect.addFlashAttribute("MENSAJE","Usuario registrado");
			}
			else {
				rt.put(REST_USUARIO+"actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE","Usuario actualizado");
			}
			
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en el registro");
			e.printStackTrace();
		}
		return "redirect:/usuario/";
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Usuario buscar(@RequestParam("codigo") int cod) {
		Usuario bean=null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Usuario> response=rt.getForEntity(REST_USUARIO+"buscar/"+cod, Usuario.class);
			bean=response.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
		
	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("codigo") int cod,RedirectAttributes redirect) {
		try {
			RestTemplate rt=new RestTemplate();
			rt.delete(REST_USUARIO+"eliminar/"+cod);
			redirect.addFlashAttribute("MENSAJE","usuario eliminado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la eliminaci�n");
			e.printStackTrace();
		}
		
		return "redirect:/usuario/";
	}
	
	@RequestMapping("/buscarUsu")

	public  String buscarporUsuario(@RequestParam("usuario") String cod, Model Modelo) {
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Usuario[]> response=rt.getForEntity(REST_USUARIO+"buscarUsu/"+cod, Usuario[].class);
			
			Modelo.addAttribute("usuarios",response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "usuario";
	}
	
}
