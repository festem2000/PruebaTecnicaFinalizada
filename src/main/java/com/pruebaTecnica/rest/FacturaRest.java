package com.pruebaTecnica.rest;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pruebaTecnica.dao.FacturaDAO;
import com.pruebaTecnica.model.Account;

@RestController
@RequestMapping(path="/home", method= RequestMethod.POST)
public class FacturaRest {
	
	// Variable de FacturaDAO que contiene la logica
	private FacturaDAO facturaDAO = new FacturaDAO();
	
	/**
	 *  Retorna un json de la lista de las facturas
	 * @return Lista de las facturas
	 */
	@GetMapping("/listar")
	public List<Account> getFacturas(){
		return facturaDAO.getAccounts();
	}
	
	
	/**
	 *  Generar una factura dada la información del POST 
	 *  
	 * @param account Es un JSON que se envia por parametro
	 * 
	 * @return Retorna la factura generada 
	 * 
	 */
	@PostMapping(path="/generarFactura", consumes = "application/json")
	public Account generarFactura(@RequestBody Account account){
		
		account = facturaDAO.addAccount(account);
		
		return account;
		
	}
	
	
	/**
	 *  Modificar una factura dada la información del POST 
	 *  
	 * @param account  Es un JSON que se envia por parametro
	 * 
	 * @param id Es el id de la factura para buscarla en el sistema
	 * 
	 * @return Retorna un string informando si se actualizo o no
	 */
	@PostMapping(path="/editarFactura/{id}", consumes = "application/json")
	public String editarFactura(@RequestBody Account account, @PathVariable("id") Integer id){
		
		boolean verificarEdit = false;
		account.setId(id);
		if(facturaDAO.findAccount(id) != null) {
			verificarEdit = facturaDAO.editAccount(account);	
		}
		
		if(verificarEdit) {
			return "Se edito correctamnete la factura #" +id;
		}
		return "No se pudo editar la factura #" +id;
	}
	
	/**
	 * 	Eliminar una factura por medio de la id e la factura
	 * @param id Id de la factura
	 * @return Un mensaje de si se realizo o no la eliminación de la factura
	 */
	@SuppressWarnings("unused")
	@PostMapping(path="/eliminarFactura/{id}", consumes = "application/json")
	public String eliminarFactura(@PathVariable("id") Integer id){
		

		String verificarDelete = facturaDAO.deleteAccount(id);	
		
		if(verificarDelete.equals("exito"))
			return "Se elimino tu pedido sin ningun cobro adicional";
		else if(verificarDelete != null) 
			return "Se elimino tu pedido pero se genero un cobro adicional por la cancelación \n Debes de pagar: " + verificarDelete;
		else
			return "No se pudo elimin la factura #" +id;
	}
	
	
	
}
