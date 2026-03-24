# Report API + Dashboard

Proyecto Spring Boot para generar reportes bancarios en PDF y Excel con JasperReports, dashboard web con Thymeleaf y persistencia en PostgreSQL (local con Docker o remoto con Supabase).

## 1. Qué incluye

- Spring Boot 2.7.18
- API REST de reportes
- Dashboard web con Tailwind + Thymeleaf
- Reporte Jasper de transacciones con filtros opcionales
- Exportación a PDF y Excel
- Modelo bancario completo con JPA
- Contador de visitas persistente en base de datos
- Configuración para deploy en Render con Docker

## 2. Stack técnico

- Java 17 (runtime)
- Maven
- Spring Boot Web
- Spring Data JPA
- Thymeleaf
- JasperReports
- PostgreSQL
- Docker / Docker Compose
- Supabase (opcional)
- Render (opcional)

## 3. Estructura principal

- src/main/java/com/example/report/controller
- src/main/java/com/example/report/model
- src/main/java/com/example/report/repository
- src/main/java/com/example/report/service
- src/main/resources/templates
- src/main/resources/reports
- db/init/01_schema.sql
- db/seed_data.sql
- docker-compose.yml
- render.yaml
- Dockerfile
- load_supabase.ps1

## 4. Funcionalidad implementada

### 4.1 Dashboard

Rutas web:

- GET /
- GET /reportes

El dashboard muestra:

- Total clientes
- Total cuentas
- Total transacciones
- Total préstamos
- Visitas acumuladas (persistidas en tabla VISITAS_PAGINA)

### 4.2 API REST

Base path:

- /api

Endpoints:

- GET /api/hello
- GET /api/status
- GET /api/reportes/transacciones/pdf
- GET /api/reportes/transacciones/excel

Filtros opcionales para reportes:

- numeroCuenta
- tipo: DEPOSITO, RETIRO, TRANSFERENCIA
- fechaInicio (yyyy-MM-dd)
- fechaFin (yyyy-MM-dd)

Ejemplos:

```http
GET /api/reportes/transacciones/pdf
GET /api/reportes/transacciones/pdf?tipo=DEPOSITO
GET /api/reportes/transacciones/pdf?numeroCuenta=0000000001&fechaInicio=2024-01-01&fechaFin=2024-12-31
GET /api/reportes/transacciones/excel?tipo=RETIRO&fechaInicio=2024-06-01&fechaFin=2024-12-31
```

## 5. Configuración de entorno

La app lee variables desde archivo .env (si existe), gracias a:

- spring.config.import=optional:file:.env[.properties]

Variables usadas:

- SUPABASE_DB_URL
- SUPABASE_DB_USER
- SUPABASE_DB_PASSWORD
- DB_POOL_MAX_SIZE (default 3)
- DB_POOL_MIN_IDLE (default 1)

Referencia:

- .env.example

## 6. Ejecutar en local (PostgreSQL con Docker)

### 6.1 Levantar base local

```powershell
docker compose up -d
```

Esto crea PostgreSQL y ejecuta automáticamente:

- db/init/01_schema.sql

### 6.2 Ejecutar aplicación

```powershell
mvn spring-boot:run
```

Aplicación en:

- http://localhost:8080/

## 7. Cargar esquema y datos en Supabase

1. Completa tu .env con credenciales reales.
2. Ejecuta:

```powershell
powershell -ExecutionPolicy Bypass -File .\load_supabase.ps1
```

El script:

- Aplica db/init/01_schema.sql
- Aplica db/seed_data.sql
- Verifica conteos

## 8. Deploy en Render (Docker)

El proyecto ya incluye:

- Dockerfile
- .dockerignore
- render.yaml

### 8.1 Variables requeridas en Render

- SUPABASE_DB_URL
- SUPABASE_DB_USER
- SUPABASE_DB_PASSWORD

Recomendación para SUPABASE_DB_URL (Transaction Pooler):

```text
jdbc:postgresql://<tu-host>.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0
```

### 8.2 Variables de pool sugeridas

- DB_POOL_MAX_SIZE=3
- DB_POOL_MIN_IDLE=1

### 8.3 Healthcheck

- /actuator/health

## 9. Base de datos

Tablas principales:

- CLIENTES
- SUCURSALES
- CUENTAS
- TRANSACCIONES
- PRESTAMOS
- PAGOS_PRESTAMOS
- VISITAS_PAGINA

## 10. Contador de visitas

Implementación:

- Entidad: VisitaPagina
- Repositorio: VisitaPaginaRepository
- Servicio: VisitaPaginaService
- Integración en DashboardController al entrar a /

Comportamiento:

- Cada ingreso a la página principal incrementa TOTAL_VISITAS
- El valor se muestra en el dashboard y queda persistido en DB

## 11. Problemas comunes

### Error: Schema-validation missing table

Causa:

- La DB no tiene el esquema actualizado.

Solución:

- Ejecutar scripts db/init/01_schema.sql y db/seed_data.sql
- En Supabase, usar load_supabase.ps1

### Error: MaxClientsInSessionMode max clients reached

Causa:

- Exceso de conexiones en pool/session mode de Supabase.

Solución:

- Usar Transaction Pooler (puerto 6543)
- Reducir pool Hikari (DB_POOL_MAX_SIZE=3, DB_POOL_MIN_IDLE=1)

## 12. Comandos útiles

Compilar sin tests:

```powershell
mvn -DskipTests compile
```

Empaquetar:

```powershell
mvn -DskipTests package
```

Liberar puerto 8080 en Windows PowerShell:

```powershell
$procIds = Get-NetTCPConnection -LocalPort 8080 -State Listen -EA SilentlyContinue | Select-Object -ExpandProperty OwningProcess -Unique
if ($procIds) {
  foreach ($procId in $procIds) {
    Stop-Process -Id $procId -Force -EA SilentlyContinue
  }
}
```

## 13. Estado actual

- API y dashboard funcionales
- Reportes PDF/Excel con filtros
- Contador persistente implementado
- Integración Supabase validada
- Configuración Docker y Render lista
