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
import com.formacionbdi.springboot.app.productos.models.entity.ProductoVending;
import com.formacionbdi.springboot.app.productos.models.entity.VentaPos;
import com.formacionbdi.springboot.app.productos.models.service.IAbonoTransbankService;
import com.formacionbdi.springboot.app.productos.models.service.IAuditoriaService;
import com.formacionbdi.springboot.app.productos.models.service.ICostosServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IDetalleVentaServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IEstadisticaVentaMes;
import com.formacionbdi.springboot.app.productos.models.service.IImagenClientes;
import com.formacionbdi.springboot.app.productos.models.service.IProductoDisponibleService;
import com.formacionbdi.springboot.app.productos.models.service.IProductoServicePos;
import com.formacionbdi.springboot.app.productos.models.service.IProductoVendingService;
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
	
	@Autowired
	private IProductoVendingService productoVendingService;

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
	
	// ============================================
	// ENDPOINTS PARA PRODUCTOS VENDING
	// ============================================
	
	/**
	 * Listar todos los productos vending
	 */
	@GetMapping("/api/productos/vending/listar")
	public ResponseEntity<List<ProductoVending>> listarProductosVending() {
		System.out.println("/api/productos/vending/listar");
		try {
			List<ProductoVending> productos = productoVendingService.findAll();
			return ResponseEntity.ok(productos);
		} catch (Exception e) {
			System.err.println("Error al listar productos vending: " + e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * Listar productos vending habilitados
	 */
	@GetMapping("/api/productos/vending/habilitados")
	public ResponseEntity<List<ProductoVending>> listarProductosVendingHabilitados() {
		System.out.println("/api/productos/vending/habilitados");
		try {
			List<ProductoVending> productos = productoVendingService.findByHabilitadoTrue();
			return ResponseEntity.ok(productos);
		} catch (Exception e) {
			System.err.println("Error al listar productos vending habilitados: " + e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * Consultar producto vending por ID
	 */
	@GetMapping("/api/productos/vending/{id}")
	public ResponseEntity<?> consultarProductoVending(@PathVariable Long id) {
		System.out.println("/api/productos/vending/" + id);
		try {
			return productoVendingService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(404)
					.body(null));
		} catch (Exception e) {
			System.err.println("Error al consultar producto vending: " + e.getMessage());
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * Actualizar imagen de producto vending por ID
	 * Soporta emojis y imágenes codificadas en base64 (MEDIUMTEXT/CLOB hasta 16 MB)
	 * @param id ID del producto
	 * @param request JSON con el campo "imagen"
	 */
	@PostMapping("/api/productos/vending/{id}/imagen")
	public ResponseEntity<?> actualizarImagenProductoVending(
			@PathVariable Long id, 
			@RequestBody java.util.Map<String, String> request) {
		System.out.println("/api/productos/vending/" + id + "/imagen");
		try {
			// Verificar que el producto existe
			if (!productoVendingService.existsById(id)) {
				return ResponseEntity.status(404)
					.body("{\"error\": \"Producto no encontrado\", \"id\": " + id + "}");
			}
			
			// Obtener la imagen del request
			String nuevaImagen = request.get("imagen");
			if (nuevaImagen == null || nuevaImagen.trim().isEmpty()) {
				return ResponseEntity.status(400)
					.body("{\"error\": \"El campo 'imagen' es requerido\"}");
			}
			
			// Validar tamaño (16 MB = 16777215 caracteres para MEDIUMTEXT)
			if (nuevaImagen.length() > 16777215) {
				return ResponseEntity.status(413) // Payload Too Large
					.body("{\"error\": \"Imagen demasiado grande\", \"tamaño_actual\": " + nuevaImagen.length() + ", \"maximo\": 16777215}");
			}
			
			// Logging del tamaño de la imagen
			double sizeKB = nuevaImagen.length() / 1024.0;
			double sizeMB = sizeKB / 1024.0;
			String tipoImagen = nuevaImagen.startsWith("data:image") ? "Base64" : "Emoji/Texto";
			System.out.println(String.format("Actualizando imagen del producto %d - Tipo: %s, Tamaño: %.2f KB (%.2f MB)", 
				id, tipoImagen, sizeKB, sizeMB));
			int filasActualizadas = productoVendingService.updateImagenById(id, nuevaImagen);
			
			if (filasActualizadas > 0) {
				// Obtener el producto actualizado
				ProductoVending productoActualizado = productoVendingService.findById(id).orElse(null);
				return ResponseEntity.ok(productoActualizado);
			} else {
				return ResponseEntity.status(500)
					.body("{\"error\": \"No se pudo actualizar la imagen\"}");
			}
		} catch (Exception e) {
			System.err.println("Error al actualizar imagen: " + e.getMessage());
			return ResponseEntity.status(500)
				.body("{\"error\": \"Error al actualizar la imagen\", \"mensaje\": \"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * Crear nuevo producto vending
	 */
	@PostMapping("/api/productos/vending/crear")
	public ResponseEntity<?> crearProductoVending(@RequestBody ProductoVending producto) {
		System.out.println("/api/productos/vending/crear");
		try {
			ProductoVending nuevoProducto = productoVendingService.save(producto);
			return ResponseEntity.status(201).body(nuevoProducto);
		} catch (Exception e) {
			System.err.println("Error al crear producto vending: " + e.getMessage());
			return ResponseEntity.status(500)
				.body("{\"error\": \"Error al crear el producto\", \"mensaje\": \"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * Actualizar producto vending SIN modificar la imagen
	 * Para actualizar la imagen usar: POST /api/productos/vending/{id}/imagen
	 */
	@PostMapping("/api/productos/vending/{id}/actualizar")
	public ResponseEntity<?> actualizarProductoVending(
			@PathVariable Long id, 
			@RequestBody ProductoVending producto) {
		System.out.println("/api/productos/vending/" + id + "/actualizar");
		try {
			if (!productoVendingService.existsById(id)) {
				return ResponseEntity.status(404)
					.body("{\"error\": \"Producto no encontrado\", \"id\": " + id + "}");
			}
			
			// Obtener el producto existente para preservar la imagen
			ProductoVending productoExistente = productoVendingService.findById(id).orElse(null);
			if (productoExistente == null) {
				return ResponseEntity.status(404)
					.body("{\"error\": \"Producto no encontrado\", \"id\": " + id + "}");
			}
			
			// Preservar la imagen existente (no se actualiza por este endpoint)
			String imagenOriginal = productoExistente.getImagen();
			
			// Actualizar el producto
			producto.setId(id);
			producto.setImagen(imagenOriginal); // Mantener imagen original
			
			ProductoVending productoActualizado = productoVendingService.save(producto);
			
			System.out.println(String.format("Producto %d actualizado (imagen preservada: %s)", 
				id, imagenOriginal != null && imagenOriginal.length() > 50 ? "Base64" : "Emoji"));
			
			return ResponseEntity.ok(productoActualizado);
		} catch (Exception e) {
			System.err.println("Error al actualizar producto vending: " + e.getMessage());
			return ResponseEntity.status(500)
				.body("{\"error\": \"Error al actualizar el producto\", \"mensaje\": \"" + e.getMessage() + "\"}");
		}
	}
	   
	
	
	
}
