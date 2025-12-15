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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.AbonoTransbank;
import com.formacionbdi.springboot.app.productos.models.entity.Auditoria;
import com.formacionbdi.springboot.app.productos.models.entity.CostosPos;
import com.formacionbdi.springboot.app.productos.models.entity.DetalleVentaPos;
import com.formacionbdi.springboot.app.productos.models.dto.EstadVentasDTO;
import com.formacionbdi.springboot.app.productos.models.entity.ImagenCliente;
import com.formacionbdi.springboot.app.productos.models.entity.ProductoDisponible;
import com.formacionbdi.springboot.app.productos.models.entity.ProductoPos;
import com.formacionbdi.springboot.app.productos.models.entity.VentaPos;
import com.formacionbdi.springboot.app.productos.models.service.IAbonoTransbankService;
import com.formacionbdi.springboot.app.productos.models.service.IAuditoriaService;
import com.formacionbdi.springboot.app.productos.models.service.ICostosServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IDetalleVentaServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IEstadisticaVentaMes;
import com.formacionbdi.springboot.app.productos.models.service.IImagenClientes;
import com.formacionbdi.springboot.app.productos.models.service.IProductoDisponibleService;
import com.formacionbdi.springboot.app.productos.models.service.IProductoServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IVentaServicePos;

@CrossOrigin
@RestController
public class ProductoController {
	
	@Autowired
	private IProductoServicePos productoServicePos;
	
	@Autowired
	private IProductoDisponibleService productoDisponibleService;
	
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
	
	@Autowired
	private IAuditoriaService auditoriaService;
	
	@Autowired
	private IAbonoTransbankService abonoTransbankService;

	@GetMapping("/api/productos/listar-productos")
	public ResponseEntity<List<ProductoPos>> listarProductos(){
		System.out.println("/listarProductos");
		return ResponseEntity.ok(productoServicePos.findAllProductsPos());
	}
	
	@GetMapping("/api/productos/productos-disponibles")
	public ResponseEntity<List<ProductoDisponible>> listarProductosDisponibles(){
		System.out.println("/productos-disponibles");
		return ResponseEntity.ok(productoDisponibleService.findAllProductosDisponibles());
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
	
	
	
	@PostMapping("/api/productos/actualizar-costo")
	public ResponseEntity<Object> updateCostos(@RequestBody int idcosto){
		return ResponseEntity.ok(
			 costosServicePos.actualizarCostoPos(idcosto));
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


	@PostMapping("/api/productos/agregar-costos")
	public ResponseEntity<Object> addCostosPos(@RequestBody CostosPos costos){
		return ResponseEntity.ok(
			 costosServicePos.agregarCostosPos(costos.getFecha(), 
					 costos.getItem(),
					 costos.getCantidad(),
					 costos.getMontototal(),
					 costos.getDescripcion() ));
					 							  
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
	public ResponseEntity<List<EstadVentasDTO>> consultarEstadisticaMes(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		List<Object[]> raw = estadisticaVentaMes.consultarEstadisticaMesRaw(sfecha);
		List<EstadVentasDTO> result = raw.stream()
			.map(arr -> new EstadVentasDTO(
				arr[0] != null ? arr[0].toString() : null,
				arr[1] != null ? arr[1].toString() : null))
			.toList();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/api/productos/cons-estadis-mensual-por-prod/{sfecha}/{sproducto}")
	public ResponseEntity<List<EstadVentasDTO>> consultarEstadisticaMesProd(@PathVariable String sfecha, @PathVariable String sproducto){
		System.out.println("/cons-estadis-mensual");
		List<Object[]> raw = estadisticaVentaMes.consultarEstadisticaMesProdRaw(sfecha, sproducto);
		List<EstadVentasDTO> result = raw.stream()
			.map(arr -> new EstadVentasDTO(
				arr[0] != null ? arr[0].toString() : null,
				arr[1] != null ? arr[1].toString() : null))
			.toList();
		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/api/productos/cons-estadis-mensual-masvendido-cant/{sfecha}")
	public ResponseEntity<List<EstadVentasDTO>> consultarEstadisticaMesMasVendidoCant(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		List<Object[]> raw = estadisticaVentaMes.consultarEstadisticaMesMasVendidosCantidadRaw(sfecha);
		List<EstadVentasDTO> result = raw.stream()
			.map(arr -> new EstadVentasDTO(
				arr[0] != null ? arr[0].toString() : null,
				arr[1] != null ? arr[1].toString() : null))
			.toList();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/api/productos/cons-estadis-mensual-masvendido-monto/{sfecha}")
	public ResponseEntity<List<EstadVentasDTO>> consultarEstadisticaMesMasVendidoMonto(@PathVariable String sfecha){
		System.out.println("/cons-estadis-mensual");
		List<Object[]> raw = estadisticaVentaMes.consultarEstadisticaMesMasVendidosMontoRaw(sfecha);
		List<EstadVentasDTO> result = raw.stream()
			.map(arr -> new EstadVentasDTO(
				arr[0] != null ? arr[0].toString() : null,
				arr[1] != null ? arr[1].toString() : null))
			.toList();
		return ResponseEntity.ok(result);
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
	
	@PostMapping("/api/productos/eliminar-venta/{idcorrelativo}")
	public ResponseEntity<Object> eliminarVenta(@PathVariable Long idcorrelativo){
		System.out.println("/eliminar-venta/" + idcorrelativo);
		try {
			ventaServicePos.eliminarVentaPos(idcorrelativo);
			return ResponseEntity.ok().body("{\"mensaje\": \"Venta eliminada correctamente\", \"idcorrelativo\": " + idcorrelativo + "}");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\": \"Error al eliminar la venta\", \"mensaje\": \"" + e.getMessage() + "\"}");
		}
	}
	
	@PostMapping("/api/productos/registrar-auditoria")
	public ResponseEntity<Object> registrarAuditoria(@RequestBody Auditoria auditoria){
		System.out.println("/registrar-auditoria - Módulo: " + auditoria.getModulo() + 
						   " - Ubicación: " + auditoria.getCiudad() + ", " + auditoria.getPais());
		try {
			auditoriaService.insertarAuditoria(
				auditoria.getHostorigen(),
				auditoria.getModulo(),
				auditoria.getAccionrealizada(),
				auditoria.getUsuario(),
				auditoria.getDetalles(),
				auditoria.getLatitud(),
				auditoria.getLongitud(),
				auditoria.getCiudad(),
				auditoria.getRegion(),
				auditoria.getPais(),
				auditoria.getDataprocesada()
			);
			return ResponseEntity.ok().body("{\"mensaje\": \"Auditoría registrada correctamente con ubicación geográfica\"}");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("{\"error\": \"Error al registrar auditoría\", \"mensaje\": \"" + e.getMessage() + "\"}");
		}
	}
	
	@GetMapping("/api/abonos/transbank/{mes}")
	public ResponseEntity<List<AbonoTransbank>> consultarAbonosTransbank(@PathVariable String mes){
		System.out.println("/api/abonos/transbank/" + mes);
		try {
			List<AbonoTransbank> abonos = abonoTransbankService.consultarAbonosPorMes(mes);
			return ResponseEntity.ok(abonos);
		} catch (Exception e) {
			System.err.println("Error al consultar abonos: " + e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}
	   
	
	
	
}
