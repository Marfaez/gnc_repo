package be.gnc.myapp.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import be.gnc.myapp.entities.Client;
import be.gnc.myapp.service.ClientDataRepository;
import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class ClientControl {
	
	
	@Autowired
	ClientDataRepository clientDataRepository;
	
	@GetMapping("/")
	public String home(@RequestParam(required = false, defaultValue="World") String name, ModelMap modelMap) {

		if(name==null || name.isEmpty())
			name = "World";
		modelMap.put("name",  name);
		System.out.println(name);
		return "pages/Home";
	}
	
    @RequestMapping(path = "/clientAdd", method = RequestMethod.GET) 
     public String createClient(Model model) { 
         model.addAttribute("client", new Client()); 
         return "pages/clientEdit"; 
     } 
 
 
     @RequestMapping(path = "clientSave", method = RequestMethod.POST) 
     public String saveClient(Client client) { 
         clientDataRepository.save(client); 
         return "redirect:/clients"; 
     } 
     
     @RequestMapping(path = "/clientDelete/{id}", method = RequestMethod.GET)
     public String deleteClient (long id) { 
         clientDataRepository.deleteById(id);
         return "redirect:/clients"; 
     } 

	
	@GetMapping(value = "/clients")
	public String showAllClients(Model model) {
		Iterable<Client>  clientList = clientDataRepository.findAll();
		model.addAttribute("clients", clientDataRepository.findAll());
		return "pages/clients";
	}

	@GetMapping(value = "/clientEdit/{id}")
	public String showClient(@PathVariable long id, Model model) throws Exception {
		Optional<Client>  client = clientDataRepository.findById(id);
		if(!client.isPresent())
			throw new Exception("client not found id : " + id);
		model.addAttribute("client", client);
		return "pages/clientEdit";

	}

}
