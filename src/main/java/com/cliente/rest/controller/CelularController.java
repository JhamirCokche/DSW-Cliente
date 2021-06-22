package com.cliente.rest.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cliente.rest.entity.Marca;
import com.cliente.rest.entity.Celular;
import com.google.gson.Gson;

@Controller
@RequestMapping("/celular")
public class CelularController {
	private String REST_CELULAR="http://localhost:8090/celular/";
	private String REST_MARCA="http://localhost:8090/marca/";
	
	
	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt=new RestTemplate();
			//acceder a la ruta "lista" del servicio de medicamento
			ResponseEntity<Celular[]> response1=rt.getForEntity(REST_CELULAR+"lista", Celular[].class);
			ResponseEntity<Marca[]> response2=rt.getForEntity(REST_MARCA+"lista", Marca[].class);
			model.addAttribute("celulares",response1.getBody());
			model.addAttribute("marcas",response2.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "celular";
	}
	
	@RequestMapping("/catalogo")
	public String catalogo(Model model) {
		try {
			RestTemplate rt=new RestTemplate();
			//acceder a la ruta "lista" del servicio de medicamento
			ResponseEntity<Celular[]> response1=rt.getForEntity(REST_CELULAR+"lista", Celular[].class);
			ResponseEntity<Marca[]> response2=rt.getForEntity(REST_MARCA+"lista", Marca[].class);
			model.addAttribute("celulares",response1.getBody());
			model.addAttribute("marcas",response2.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catalogo";
	}
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod,@RequestParam("nombre") String nom,
								@RequestParam("stock") int stock,@RequestParam("precio") double pre,
								@RequestParam("marca") int codmarca,
								RedirectAttributes redirect) {
		try {
			Celular bean=new Celular();
			bean.setId_celular(cod);
			bean.setNombre(nom);
			bean.setStock(stock);
			bean.setPrecio(pre);
			Marca mar=new Marca();
			mar.setCodigo(codmarca);
			bean.setMarca(mar);
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
					rt.postForObject(REST_CELULAR+"registrar", request, String.class);
					redirect.addFlashAttribute("MENSAJE","Celular registrado");
			}
			else {
				rt.put(REST_CELULAR+"actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE","Celular actualizado");
			}
			
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en el registro");
			e.printStackTrace();
		}
		return "redirect:/celular/";
	}
	
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Celular buscar(@RequestParam("codigo") int cod) {
		Celular bean=null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Celular> response=rt.getForEntity(REST_CELULAR+"buscar/"+cod, Celular.class);
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
			rt.delete(REST_CELULAR+"eliminar/"+cod);
			redirect.addFlashAttribute("MENSAJE","Celular eliminado");
		} catch (Exception e) {
			redirect.addFlashAttribute("MENSAJE","Error en la eliminación");
			e.printStackTrace();
		}
		
		return "redirect:/celular/";
	}
	
}














