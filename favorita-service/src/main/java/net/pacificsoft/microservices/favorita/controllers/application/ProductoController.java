package net.pacificsoft.microservices.favorita.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.repository.RutaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.pacificsoft.microservices.favorita.models.Producto;
import net.pacificsoft.microservices.favorita.models.Ruta;
import net.pacificsoft.microservices.favorita.repository.ProductoRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class ProductoController {
	
	@Autowired
	private ProductoRepository productoRepository;

        @Autowired
	private RutaRepository rutaRepository;
        
	@GetMapping("/producto")
	public ResponseEntity getAllProductos() {
		try{
                    List<Map<String, Object>> result = new ArrayList();
                    JSONObject json;
                    List<Producto> productos = productoRepository.findAll();
                    for (Producto p : productos){
                        json = new JSONObject();
                        json.put("id", p.getId());
                        json.put("name", p.getName());
                        json.put("temp_max", p.getTemp_max());
                        json.put("temp_min", p.getTemp_min());
                        json.put("temp_max_ideal", p.getTemp_max_ideal());
                        json.put("temp_min_ideal", p.getTemp_min_ideal());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/producto/{id}")
	public ResponseEntity getProductoById(
			@PathVariable(value = "id") Long productoId){
		if(productoRepository.existsById(productoId)){
                    Producto p = productoRepository.findById(productoId);
                    JSONObject json = new JSONObject();
                    json.put("id", p.getId());
                    json.put("name", p.getName());
                    json.put("temp_max", p.getTemp_max());
                    json.put("temp_min", p.getTemp_min());
                    json.put("temp_max_ideal", p.getTemp_max_ideal());
                    json.put("temp_min_ideal", p.getTemp_min_ideal());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Producto #" + productoId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/producto")
	public ResponseEntity createProducto(@Valid @RequestBody Producto producto) {
                try{
                    Producto p = productoRepository.save(producto);
                    JSONObject json = new JSONObject();
                    json.put("id", p.getId());
                    json.put("name", p.getName());
                    json.put("temp_max", p.getTemp_max());
                    json.put("temp_min", p.getTemp_min());
                    json.put("temp_max_ideal", p.getTemp_max_ideal());
                    json.put("temp_min_ideal", p.getTemp_min_ideal());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("New Furgon not created.",
                            HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/producto/{id}")
	public ResponseEntity updateProducto(
			@PathVariable(value = "id") Long productoId,
			@Valid @RequestBody Producto productoDetails){
            try{
                if(productoRepository.existsById(productoId)){
                    Producto producto = productoRepository.findById(productoId);
                    producto.setName(productoDetails.getName());
                    producto.setTemp_max(productoDetails.getTemp_max());
                    producto.setTemp_min(productoDetails.getTemp_min());
                    producto.setTemp_max_ideal(productoDetails.getTemp_max_ideal());
                    producto.setTemp_min_ideal(productoDetails.getTemp_min_ideal());
                    final Producto updatedProducto = productoRepository.save(producto);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Producto #" + productoId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Producto #" + productoId, HttpStatus.NOT_FOUND);
            }
	}
        
	@DeleteMapping("/producto/{id}")
	public ResponseEntity deleteProducto(
			@PathVariable(value = "id") Long productoId){
                if(productoRepository.existsById(productoId)){
                    Producto producto = productoRepository.findById(productoId);
                    if(producto.getRutas().size()>0){
                        Set<Ruta> ubFs = producto.getRutas();
                        for(Ruta r:ubFs){
                            r.setProducto(null);
                            productoRepository.save(r.getProducto());
                        }
                    }
                    productoRepository.delete(producto);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Producto #" + productoId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
