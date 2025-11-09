// ========================================
// EJEMPLOS DE USO - GEOLOCALIZACIÓN
// ========================================

// ========================================
// OPCIÓN 1: Geolocation API del navegador (GPS)
// ========================================
// Usar en tu aplicación web frontend (JavaScript/TypeScript)

function obtenerUbicacionYRegistrarAuditoria() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            async (position) => {
                const auditoria = {
                    hostorigen: await obtenerIP(), // Función auxiliar para obtener IP
                    modulo: window.location.pathname,
                    accionrealizada: "Acceso al sistema",
                    usuario: "admin", // Obtener del contexto de usuario
                    detalles: JSON.stringify({
                        navegador: navigator.userAgent,
                        resolucion: `${window.screen.width}x${window.screen.height}`
                    }),
                    latitud: position.coords.latitude,
                    longitud: position.coords.longitude,
                    ciudad: null, // Se puede obtener con reverse geocoding
                    region: null,
                    pais: null
                };

                // Enviar al backend
                await registrarAuditoria(auditoria);
            },
            (error) => {
                console.error("Error obteniendo ubicación GPS:", error);
                // Fallback: usar geolocalización por IP
                registrarAuditoriaConIP();
            }
        );
    } else {
        console.log("Geolocalización no soportada");
        registrarAuditoriaConIP();
    }
}

// ========================================
// OPCIÓN 2: Geolocalización por IP (Backend o Frontend)
// ========================================

// A) Desde Frontend (llamada directa a API de geolocalización)
async function obtenerUbicacionPorIP() {
    try {
        // Opción 1: ip-api.com (gratis, 45 req/min)
        const response = await fetch('http://ip-api.com/json/');
        const data = await response.json();
        
        return {
            hostorigen: data.query, // IP
            latitud: data.lat,
            longitud: data.lon,
            ciudad: data.city,
            region: data.regionName,
            pais: data.country
        };
    } catch (error) {
        console.error("Error obteniendo ubicación por IP:", error);
        return null;
    }
}

// Uso:
async function registrarAuditoriaConIP() {
    const ubicacion = await obtenerUbicacionPorIP();
    
    const auditoria = {
        hostorigen: ubicacion.hostorigen,
        modulo: "/api/productos/listar-ventas",
        accionrealizada: "Consulta de ventas",
        usuario: "admin",
        detalles: JSON.stringify({ filtros: { fecha: "2025-11-09" } }),
        latitud: ubicacion.latitud,
        longitud: ubicacion.longitud,
        ciudad: ubicacion.ciudad,
        region: ubicacion.region,
        pais: ubicacion.pais
    };

    await registrarAuditoria(auditoria);
}

// ========================================
// FUNCIÓN PARA REGISTRAR EN EL BACKEND
// ========================================
async function registrarAuditoria(auditoria) {
    try {
        const response = await fetch('http://localhost:8001/api/productos/registrar-auditoria', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(auditoria)
        });

        const result = await response.json();
        console.log('Auditoría registrada:', result);
        return result;
    } catch (error) {
        console.error('Error al registrar auditoría:', error);
    }
}

// ========================================
// FUNCIÓN AUXILIAR: Obtener IP del cliente
// ========================================
async function obtenerIP() {
    try {
        const response = await fetch('https://api.ipify.org?format=json');
        const data = await response.json();
        return data.ip;
    } catch (error) {
        return 'unknown';
    }
}

// ========================================
// OPCIÓN 3: Reverse Geocoding (Coordenadas a Ciudad)
// ========================================
// Convertir coordenadas GPS a nombre de ciudad/país
async function coordenadasACiudad(latitud, longitud) {
    try {
        // Usando OpenStreetMap Nominatim (gratis, sin API key)
        const url = `https://nominatim.openstreetmap.org/reverse?lat=${latitud}&lon=${longitud}&format=json`;
        const response = await fetch(url, {
            headers: {
                'User-Agent': 'MiAppPos/1.0' // Requerido por Nominatim
            }
        });
        const data = await response.json();
        
        return {
            ciudad: data.address.city || data.address.town || data.address.village,
            region: data.address.state,
            pais: data.address.country
        };
    } catch (error) {
        console.error("Error en reverse geocoding:", error);
        return null;
    }
}

// ========================================
// EJEMPLO COMPLETO: GPS + Reverse Geocoding
// ========================================
async function registrarAuditoriaCompleta() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            async (position) => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                
                // Obtener nombre de ciudad/país desde coordenadas
                const ubicacion = await coordenadasACiudad(lat, lon);
                
                const auditoria = {
                    hostorigen: await obtenerIP(),
                    modulo: window.location.pathname,
                    accionrealizada: "Acceso al sistema",
                    usuario: "admin",
                    detalles: JSON.stringify({
                        navegador: navigator.userAgent,
                        precision: position.coords.accuracy + " metros"
                    }),
                    latitud: lat,
                    longitud: lon,
                    ciudad: ubicacion?.ciudad || null,
                    region: ubicacion?.region || null,
                    pais: ubicacion?.pais || null
                };

                await registrarAuditoria(auditoria);
            },
            async (error) => {
                console.error("Error GPS:", error.message);
                // Fallback a geolocalización por IP
                await registrarAuditoriaConIP();
            }
        );
    }
}

// ========================================
// SERVICIOS DE GEOLOCALIZACIÓN RECOMENDADOS
// ========================================

/*
1. ip-api.com
   - Gratis: 45 req/minuto
   - Sin API key
   - URL: http://ip-api.com/json/{ip}

2. ipgeolocation.io
   - Gratis: 1000 req/día
   - Requiere API key
   - Más preciso

3. ipapi.co
   - Gratis: 1000 req/día
   - Sin API key en plan gratis
   - URL: https://ipapi.co/{ip}/json/

4. MaxMind GeoIP2 (Recomendado para producción)
   - De pago pero muy preciso
   - Base de datos local (más rápido)
   - Incluye Java SDK

5. OpenStreetMap Nominatim (Reverse Geocoding)
   - Gratis
   - Requiere User-Agent header
   - Límite de 1 req/segundo
*/

// ========================================
// EJEMPLO DE LLAMADA DESDE REACT/ANGULAR/VUE
// ========================================

// React Hook
function useAuditoria() {
    const registrar = async (accion, detalles) => {
        const ubicacion = await obtenerUbicacionPorIP();
        
        const auditoria = {
            hostorigen: ubicacion.hostorigen,
            modulo: window.location.pathname,
            accionrealizada: accion,
            usuario: localStorage.getItem('usuario'), // Ejemplo
            detalles: JSON.stringify(detalles),
            latitud: ubicacion.latitud,
            longitud: ubicacion.longitud,
            ciudad: ubicacion.ciudad,
            region: ubicacion.region,
            pais: ubicacion.pais
        };

        return await registrarAuditoria(auditoria);
    };

    return { registrar };
}

// Uso en componente:
// const { registrar } = useAuditoria();
// registrar("Login exitoso", { timestamp: Date.now() });

// ========================================
// CURL PARA PROBAR EL ENDPOINT
// ========================================
/*
curl -X POST http://localhost:8001/api/productos/registrar-auditoria \
  -H "Content-Type: application/json" \
  -d '{
    "hostorigen": "192.168.1.100",
    "modulo": "/api/productos/listar-ventas",
    "accionrealizada": "Consulta de ventas del día",
    "usuario": "admin",
    "detalles": "{\"filtros\": {\"fecha\": \"2025-11-09\"}}",
    "latitud": -33.4489,
    "longitud": -70.6693,
    "ciudad": "Santiago",
    "region": "Región Metropolitana",
    "pais": "Chile"
  }'
*/
