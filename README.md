# myHotel Backend Test

## Descripción del proyecto

Backend REST (Spring Boot) del proyecto **myHotel backendTest**. Incluye:

- **API de vehículos**: CRUD y búsqueda de vehículos.
- **API de mantenimientos**: registros de mantenimiento asociados a vehículos por patente.
- **API de analytics HR**: consultas analíticas sobre empleados, departamentos, salarios y países (datos del dump `myhotel`).

Tecnologías: Java 21, Spring Boot 3.5, Spring Data JPA, Flyway (MySQL), MySQL 8.

---

## Requisitos previos

- **Git**
- **Java 21** (JDK 21)
- **Maven 3.6+** (o usar el wrapper del proyecto: `./mvnw` / `mvnw.cmd`)
- **Docker** y **Docker Compose** (para levantar MySQL)
- **Postman** (opcional, para probar la API con la colección incluida)

---

## Setup rápido

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd myhotel-api
```

### 2. Levantar la base de datos con Docker Compose

Desde la raíz del proyecto:

```bash
docker compose up -d
```

Esto levanta MySQL 8.0 en el puerto **3307** (mapeo `3307:3306`), con:

- Base de datos: `myhotel`
- Usuario: `app_user`
- Contraseña: `app_pass_123`

### 3. Compilar

**Linux / macOS:**

```bash
./mvnw clean compile
```

**Windows (CMD/PowerShell):**

```bash
mvnw.cmd clean compile
```

O con Maven instalado globalmente:

```bash
mvn clean compile
```

### 4. Ejecutar la aplicación

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

O:

```bash
mvn spring-boot:run
```

La API queda disponible en **http://localhost:8080**. No hay `context-path` configurado, las rutas base son las indicadas en [Listado de endpoints](#listado-de-endpoints).

---

## Migraciones Flyway

- **Dependencias:** `flyway-core` y `flyway-mysql` en `pom.xml`.
- **Configuración:** en `src/main/resources/application.yaml` está `spring.flyway.enabled: true`. Flyway usa por defecto el directorio `src/main/resources/db/migration` y el mismo datasource que la aplicación.
- **Cuándo se ejecutan:** al arrancar la aplicación (`spring-boot:run`). Se ejecutan automáticamente antes de que JPA/Hibernate valide el esquema (`ddl-auto: validate`).
- **Logs:** en la salida estándar del proceso Spring Boot. Buscar líneas con `flyway` o "Migrating schema" si se desea ver qué migraciones se aplican.
- **Migraciones existentes:**

| Versión | Archivo | Descripción |
|:--------|:--------|:-----------|
| **V1** | `V1__VEHICLE_ALT.sql` | Crea la tabla `VEHICLE` con campos para autos y camiones |
| **V2** | `V2__MAINTENANCE_RECORD_ALT.sql` | Crea la tabla `maintenance_record` con relación a vehículos |
| **V3** | `V3__SEED_VEHICLE_MAINTENANCE_RECORD_UPD.sql` | Inserta datos de prueba: 50 vehículos (35 autos + 15 camiones) y sus mantenimientos |
| **V4** | `V4__myhotel_dump.sql` | Dump completo de datos HR: empleados, departamentos, países, regiones, jobs, locations, job_history |


---

## Configuración

- **Archivo:** `src/main/resources/application.yaml`
- **Puerto:** `server.port: 8080`
- **Context-path:** no configurado (raíz `/`).
- **Base de datos:**
  - URL: `jdbc:mysql://localhost:3307/myhotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
  - Usuario: `app_user`
  - Contraseña: `app_pass_123`


### Conectarse a la base de datos desde la terminal

Esta conexión sirve para entrar a MySQL y ejecutar consultas SQL directamente, se usa para realizar el **punto 2** de la prueba técnica (consultas sobre empleados, departamentos y países). Comando:

```bash
docker exec -it backend-test-mysql mysql -u app_user -p myhotel
```

Contraseña: `app_pass_123`. Para salir: `exit;`.

A continuación, las **consultas logradas por punto**:

**Punto 1 – Segmentos de sueldo**
- Segmento A (salario &lt; 3500):  
  SELECT COUNT(*) FROM employees WHERE salary < 3500;
- Segmento B (3500 ≤ salario &lt; 8000):  
  SELECT COUNT(*) FROM employees WHERE salary >= 3500 AND salary < 8000;
- Segmento C (salario ≥ 8000):  
  SELECT COUNT(*) FROM employees WHERE salary >= 8000;

**Punto 2 – Segmentos por departamento**
- Segmento A:  
  SELECT d.department_name AS departamento, COUNT(*) FROM departments d JOIN employees e ON d.department_id = e.department_id WHERE e.salary < 3500 GROUP BY departamento;
- Segmento B:  
  SELECT d.department_name AS departamento, COUNT(*) FROM departments d JOIN employees e ON d.department_id = e.department_id WHERE e.salary >= 3500 AND salary < 8000 GROUP BY departamento;
- Segmento C:  
  SELECT d.department_name AS departamento, COUNT(*) FROM departments d JOIN employees e ON d.department_id = e.department_id WHERE e.salary >= 8000 GROUP BY departamento;

**Punto 3 – Empleados con salario máximo por departamento**
SELECT d.department_name, e.*
FROM departments d
JOIN employees e ON d.department_id = e.department_id
WHERE e.salary = (SELECT MAX(e2.salary) FROM employees e2 WHERE e.department_id = e2.department_id);

**Punto 4 – Gerentes contratados hace más de 15 años**
SELECT * FROM employees
WHERE hire_date < DATE_SUB(CURRENT_DATE(), INTERVAL 15 YEAR)
  AND (job_id LIKE '%_MAN' OR job_id LIKE '%_MGR' OR job_id LIKE '%_PRES' OR job_id LIKE '%_VP');


**Punto 5 – Salario promedio de departamentos con más de 10 empleados**
SELECT d.department_name, AVG(e.salary)
FROM employees e
JOIN departments d ON e.department_id = d.department_id
GROUP BY d.department_name
HAVING COUNT(*) > 10;

**Punto 6 – Métricas por país**
SELECT c.country_name AS pais,
       COUNT(*) AS total_empleados,
       MAX(e.salary) AS salario_maximo,
       MIN(e.salary) AS salario_minimo,
       AVG(e.salary) AS salario_promedio,
       AVG(TIMESTAMPDIFF(YEAR, e.hire_date, CURDATE())) AS promedio_antiguedad
FROM employees e
JOIN departments d ON e.department_id = d.department_id
JOIN locations l ON l.location_id = d.location_id
JOIN countries c ON c.country_id = l.country_id
GROUP BY c.country_name;

Estas consultas son la base de los endpoints de **analytics HR** (`/api/v1/analytics/...`) de la API.

---

## Probar la API

### Colección Postman

En el repositorio está la colección:

**`MyHotel Backend Test API.postman_collection.json`** (en la raíz del proyecto).

1. Abrí Postman.
2. **Import** → **File** → elegí `MyHotel Backend Test API.postman_collection.json`.
4. Ejecutá las peticiones directamente, la API no requiere autenticación.


## Listado de endpoints

### Analytics HR (`/api/v1/analytics`)

| Método | Ruta (relativa a base) | Descripción |
|--------|------------------------|-------------|
| GET | `/api/v1/analytics/segmentos-sueldo` | Segmentos de sueldo |
| GET | `/api/v1/analytics/segmentos-por-departamento` | Segmentos por departamento |
| GET | `/api/v1/analytics/empleados-max-salario-por-departamento` | Empleados con mayor sueldo por departamento |
| GET | `/api/v1/analytics/gerentes-15-anios` | Gerentes contratados hace más de 15 años |
| GET | `/api/v1/analytics/avg-salario-deptos-10-plus` | Salario promedio de departamentos con más de 10 empleados |
| GET | `/api/v1/analytics/metricas-por-pais` | Métricas por país |

### Vehículos (`/api/vehicles`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/vehicles` | Listado paginado (query: `offset`, `limit`) |
| GET | `/api/vehicles/{id}` | Vehículo por ID |
| GET | `/api/vehicles?plate={plate}` | Vehículo por patente |
| GET | `/api/vehicles/search` | Búsqueda (query: `brand`, `model`, `yearFrom`, `yearTo`, `dtype`, `plateLike`, `offset`, `limit`) |
| POST | `/api/vehicles` | Crear vehículo (body JSON) |
| PUT | `/api/vehicles/{id}` | Actualizar vehículo (body JSON) |
| DELETE | `/api/vehicles/{id}` | Eliminación lógica |

### Mantenimientos (`/api/vehicles/{plate}/maintenances`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/vehicles/{plate}/maintenances` | Listado paginado por patente (`offset`, `limit`) |
| GET | `/api/vehicles/{plate}/maintenances/latest` | Último mantenimiento de la patente |
| POST | `/api/vehicles/{plate}/maintenances` | Crear mantenimiento (body JSON) |
| DELETE | `/api/vehicles/{plate}/maintenances/{id}` | Eliminación lógica del mantenimiento |


