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

import com.cliente.rest.entity.Marca;
import com.cliente.rest.entity.Usuario;
import com.google.gson.Gson;





	@Controller
	@RequestMapping("/marca")
	public class MarcaController {
		private String REST_MARCA="http://localhost:8090/marca/";

		
		@RequestMapping("/")
		public String index(Model model) {
			try {
				RestTemplate rt=new RestTemplate();
				//acceder a la ruta "lista" del servicio de medicamento
				ResponseEntity<Marca[]> response1=rt.getForEntity(REST_MARCA+"lista", Marca[].class);
				model.addAttribute("marcas",response1.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "marca";
		}	
	
		@RequestMapping("/guardar")
		public String guardar(@RequestParam("codigo") int cod,@RequestParam("descripcion") String des, @RequestParam("fecha") String fec,
									RedirectAttributes redirect) {
			try {
				Marca bean=new Marca();
				bean.setCodigo(cod);
				bean.setDescripcion(des);
				bean.setFecha(fec);
				
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
						rt.postForObject(REST_MARCA+"registrar", request, String.class);
						redirect.addFlashAttribute("MENSAJE","Marca registrada");
				}
				else {
					rt.put(REST_MARCA+"actualizar", request, String.class);
					redirect.addFlashAttribute("MENSAJE","Marca actualizada");
				}
				
			} catch (Exception e) {
				redirect.addFlashAttribute("MENSAJE","Error en el registro");
				e.printStackTrace();
			}
			return "redirect:/marca/";
		}
		
		@RequestMapping("/buscar")
		@ResponseBody
		public Marca buscar(@RequestParam("codigo") int cod) {
			Marca bean=null;
			try {
				RestTemplate rt=new RestTemplate();
				ResponseEntity<Marca> response=rt.getForEntity(REST_MARCA+"buscar/"+cod, Marca.class);
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
				rt.delete(REST_MARCA+"eliminar/"+cod);
				redirect.addFlashAttribute("MENSAJE","marca eliminada");
			} catch (Exception e) {
				redirect.addFlashAttribute("MENSAJE","Error en la eliminación");
				e.printStackTrace();
			}
			
			return "redirect:/marca/";
		}
		
		
		
}


