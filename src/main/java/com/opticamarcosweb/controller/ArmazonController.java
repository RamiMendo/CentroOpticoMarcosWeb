package com.opticamarcosweb.controller;

import com.opticamarcosweb.exceptions.EntidadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.opticamarcosweb.model.Armazon;
import com.opticamarcosweb.service.ArmazonService;

import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/armazon")
public class ArmazonController {
	
	@Autowired
	private ArmazonService armazonService;

	@GetMapping
	@Operation(summary="Devuelve un listado de Armazones", description="No tiene parámetros de entrada, no está paginada y devuelve todos los armazones de bd", tags= {"Armazones"})
	private @ResponseBody List<Armazon> getListaArmazones() {
		return armazonService.getAllArmazones();
	}

	@GetMapping(path = "/paginado")
	@Operation(summary = "", description = "", tags = {"Armazones"})
	private ResponseEntity<Page<Armazon>> getPaginadoArmazon(@RequestParam Integer page, @RequestParam Integer size) {
		Pageable pagina = PageRequest.of(page, size);
		Page<Armazon> armazonPage = armazonService.getAllArmazonesPaginado(pagina);

		return new ResponseEntity<>(armazonPage, HttpStatus.OK);
	}

	@GetMapping(value="/{id}")
	@Operation(summary="Devuelve un armazon apartir de ID ingresado", description="Tiene parámetros de entrada y devuelve el armazon a partir del ID ingresado", tags= {"Armazones"})
	private @ResponseBody Optional<Armazon> getArmazonById(@PathVariable("id") Integer id) {
		return armazonService.getArmazonById(id);
	}
	
	@PostMapping(consumes="application/json")
	@Operation(summary="Agrega un armazon", description="Debe enviar el objeto armazon a ingresar", tags= {"Armazones"})
	private @ResponseBody Armazon addArmazon(@RequestBody Armazon armazon) {
		return armazonService.addArmazon(armazon);
	}
	
	@DeleteMapping(path="/{id}", produces="application/json")
	@Operation(summary="Borra un armazon de la Base de Datos", description="Debe enviar el id del armazon a borrar", tags= {"Armazones"})
	private ResponseEntity<Void> deleteArmazon(@PathVariable("id") Integer id) throws EntidadException {
		armazonService.deleteArmazon(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path="/", produces="application/json")
	@Operation(summary="Permite modificar un armazon", description="Debe enviar el objeto a modificar, no se puede modificar el id", tags= {"Armazones"})
	public @ResponseBody Armazon updateArmazon(Armazon armazon) {
		return armazonService.updateArmazon(armazon);
	}
	
}
