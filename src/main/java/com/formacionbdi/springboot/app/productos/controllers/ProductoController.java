package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.CostosPos;
import com.formacionbdi.springboot.app.productos.models.entity.DetalleVentaPos;
import com.formacionbdi.springboot.app.productos.models.entity.EstadVentas;
import com.formacionbdi.springboot.app.productos.models.entity.ImagenCliente;
import com.formacionbdi.springboot.app.productos.models.entity.ProductoPos;
import com.formacionbdi.springboot.app.productos.models.entity.VentaPos;
import com.formacionbdi.springboot.app.productos.models.service.ICostosServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IDetalleVentaServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IEstadisticaVentaMes;
import com.formacionbdi.springboot.app.productos.models.service.IImagenClientes;
import com.formacionbdi.springboot.app.productos.models.service.IProductoServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IVentaServicePos;

@CrossOrigin
@RestController
public class ProductoController {
	
	@Autowired
	private IProductoServicePos productoServicePos;
	
	@Autowired
	private IVentaServicePos ventaServicePos;
	
	@Autowired
	private ICostosServicePos costosServicePos;
	
	@Autowired
	private IDetalleVentaServicePos detalleventaServicePos;
	
	@Autowired
	private IEstadisticaVentaMes estadisticaVentaMes;
	
	@Autowired
	private IImagenClientes imagenClienteService;

	@GetMapping("/api/productos/listar-productos")
	public ResponseEntity<List<ProductoPos>> listarProductos(){
		System.out.println("/listarProductos");
		return ResponseEntity.ok(productoServicePos.findAllProductsPos());
	}
	
	@GetMapping("/api/productos/consultar-producto/{idProd}")
	public ResponseEntity<ProductoPos> consultarProductos(@PathVariable int idProd){
		System.out.println("/consultar-producto");
		return ResponseEntity.ok(productoServicePos.consultarId(idProd));
	}

	@PostMapping("/api/productos/agregar-producto")
	public ResponseEntity<Object> addProductoPos(@RequestBody ProductoPos producto){
		return ResponseEntity.ok(
			 productoServicePos.agregarProductoPos(producto.getNombreproducto(), 
					 							   producto.getPrecio(),
					 							   producto.getFechacreacion(),
					 							   producto.getEstado(),
					 							   producto.getNroposicion(),
					 							   producto.getCantidaddisponible(),
					 							   null));
	}
	
	@PostMapping("/api/productos/actualizar-producto")
	public ResponseEntity<Object> updateProductoPos(@RequestBody ProductoPos producto){
		return ResponseEntity.ok(
			 productoServicePos.actualizarProductoPos(
					 							   producto.getIdproductos(),
					 							   producto.getNombreproducto(), 
					 							   producto.getPrecio(),
					 							   producto.getFechacreacion(),
					 							   producto.getEstado(),
					 							   producto.getNroposicion(),
					 							   producto.getCantidaddisponible(),
					 							   null));
	}
	
	@GetMapping("/api/productos/listar-ventas")
	public ResponseEntity<List<VentaPos>> listarVentas(){
		System.out.println("/listar-ventas");
		return ResponseEntity.ok(ventaServicePos.listarVentasPos());
	}
	
	@GetMapping("/api/productos/consultar-ventas/{sfecha}")
	public ResponseEntity<List<VentaPos>> consultarProductos(@PathVariable String sfecha){
		System.out.println("/consultar-ventas");
		return ResponseEntity.ok(ventaServicePos.consultarVenta(sfecha));
	}
	
	@GetMapping("/api/productos/consultar-costos/{sfecha}")
	public ResponseEntity<List<CostosPos>> consultarCostos(@PathVariable String sfecha){
		System.out.println("/consultar-costos" + "/" + sfecha);
		return ResponseEntity.ok(costosServicePos.consultarCostos(sfecha));
	}

	
	
	@PostMapping("/api/productos/insertar-venta")
	public ResponseEntity<Object> addVentaPos(@RequestBody VentaPos ventapos){
		return ResponseEntity.ok(
				ventaServicePos.insertaVentaPos(
					 ventapos.getIdventa(),
					 ventapos.getFechaventa(),
					 ventapos.getSecuencia(),
					 ventapos.getNroboleta(),
					 ventapos.getTotalarticulos(),
					 ventapos.getSubtotalventa(),
					 ventapos.getIva(),
					 ventapos.getTotalimporte(),
					 ventapos.getTipopago(),
					 ventapos.getComisiontbk(),
					 ventapos.getComunicacionpos(),
					 ventapos.getEstadotransbank(),
					 ventapos.getTrazastattransbk(), 
					 ventapos.getLongmsgtransbank()
            ));
	}
	
	
	@PostMapping("/api/productos/insertar-detalleventa")
	public ResponseEntity<Object> addDetalleVentaPos(@RequestBody DetalleVentaPos detalleventapos){
		return ResponseEntity.ok(
				detalleventaServicePos.insertarDetalleVentaPos(
						detalleventapos.getIdventa(),
						detalleventapos.getNombreproducto(),
						detalleventapos.getIdproducto(),
						detalleventapos.getCantidad(),
						detalleventapos.getPreciosubtotal()
            ));
	}
	
	
	@GetMapping("/api/productos/cons-estadis-mensual/{sfecha}")
	public ResponseEntity<List<EstadVentas>> consultarEstadisticaMes(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		return ResponseEntity.ok(estadisticaVentaMes.consultarEstadisticaMes(sfecha));
	}
	
	@GetMapping("/api/productos/cons-estadis-mensual-por-prod/{sfecha}/{sproducto}")
	public ResponseEntity<List<EstadVentas>> consultarEstadisticaMesProd(@PathVariable String sfecha, @PathVariable String sproducto){
		System.out.println("/cons-estadis-mensual");
		return ResponseEntity.ok(estadisticaVentaMes.consultarEstadisticaMesProd(sfecha, sproducto));
	}
	
	
	@GetMapping("/api/productos/cons-estadis-mensual-masvendido-cant/{sfecha}")
	public ResponseEntity<List<EstadVentas>> consultarEstadisticaMesMasVendidoCant(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		return ResponseEntity.ok(estadisticaVentaMes.consultarEstadisticaMesMasVendidoCant(sfecha));
	}
	
	@GetMapping("/api/productos/cons-estadis-mensual-masvendido-monto/{sfecha}")
	public ResponseEntity<List<EstadVentas>> consultarEstadisticaMesMasVendidoMonto(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		return ResponseEntity.ok(estadisticaVentaMes.consultarEstadisticaMesMasVendidoMonto(sfecha));
	}
	
	
	@GetMapping("/api/productos/consultar-imagencli/{sfecha}")
	public ResponseEntity<List<ImagenCliente>> consultarImagenCliente2(@PathVariable String sfecha){
		System.out.println("/consultar-imagencli/{sfecha}");
		return ResponseEntity.ok(imagenClienteService.consultarImagenCliente2(sfecha));
	}
	
	@GetMapping("/api/productos/consultar-imagencli")
	public ResponseEntity<List<ImagenCliente>> consultarImagenCliente(){
		System.out.println("/consultar-imagencli");
		return ResponseEntity.ok(imagenClienteService.consultarImagenCliente());
	}
	
	
	@PostMapping("/api/productos/insertar-imagencli")
	public ResponseEntity<Object> insertaImagenCliente(@RequestBody ImagenCliente imagenCliente){
		System.out.println("/insertar-imagencli");  //+ imagenCliente.getImagen());
		return ResponseEntity.ok(
				imagenClienteService.insertaImagenCliente(
						imagenCliente.getIdventa(),
						imagenCliente.getImagen(),
						imagenCliente.getRutaimagen()));
					 
	}
	   
	
	
	
}
