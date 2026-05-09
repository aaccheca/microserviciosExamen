# Microservicios con Spring Boot y Eureka



## 📋 Descripción del Proyecto

Este proyecto contiene tres microservicios implementados con Spring Boot siguiendo una arquitectura por capas:

1. **Eureka Server** - Servidor de descubrimiento de servicios (Netflix Eureka)
2. **Estudiante Service** - Microservicio para la gestión de estudiantes
3. **Profesor Service** - Microservicio para la gestión de profesores

---

## 🏗️ Arquitectura del Microservicio Estudiante

El microservicio `Estudiante` sigue una **arquitectura por capas** con la siguiente estructura:

```
src/main/java/com/example/Estudiante/
├── controller/          # Capa de presentación (REST endpoints)
│   └── StudentController.java
├── service/             # Capa de negocio (interface)
│   ├── StudentService.java
│   └── impl/           # Implementaciones del service
│       └── StudentServiceImpl.java
├── repository/          # Capa de persistencia (DAO)
│   └── StudentRepository.java
├── entity/             # Modelo de datos JPA
│   └── Student.java
├── dto/                # Objetos de transferencia de datos
│   ├── StudentDTO.java
│   └── StudentResponseDTO.java
├── exception/          # Manejo de excepciones personalizadas
│   ├── GlobalExceptionHandler.java
│   ├── StudentNotFoundException.java
│   ├── StudentAlreadyExistsException.java
│   ├── ErrorResponse.java
│   └── ValidationErrorResponse.java
├── validation/         # Validaciones (usando Bean Validation)
├── config/             # Configuraciones
│   └── AppConfig.java
└── EstudianteApplication.java  # Clase principal
```

### 📊 Diagrama de Capas

```
[REQUEST] → [Controller] → [Service] → [Repository] → [Database]
                ↓              ↓              ↓
            [DTO/Entity]  [DTO/Entity]  [Entity/JPA]
                ↓              ↓              ↓
           [Validation] [Business]    [SQL Queries]
                ↓              ↓              ↓
           [Exception Handling ←────────┘
```

### 🔄 Flujo de Datos

1. **Controller Layer**: Recibe peticiones HTTP, valida entrada con `@Valid`, maneja responses
2. **Service Layer**: Contiene la lógica de negocio, orquesta operaciones, usa transacciones
3. **Repository Layer**: Acceso a datos con Spring Data JPA, métodos CRUD
4. **Entity Layer**: Modelo de dominio persistente con anotaciones JPA
5. **DTO Layer**: Objetos para transferir datos (separación de concerns)
6. **Exception Layer**: Manejo centralizado de errores con respuestas HTTP apropiadas

## ⚙️ Requisitos Técnicos Cumplidos

✅ **Arquitectura por capas** - Separación clara de responsabilidades  
✅ **Manejo de JSON** - Serialización/deserialización automática con Spring Web  
✅ **Entidad JPA** - `Student` con anotaciones y validaciones  
✅ **Inyección de dependencias** - Uso de `@Service`, `@Repository`, `@Autowired` constructor  
✅ **Nombres claros y buenas prácticas** - Convenciones Java, paquete organization  
✅ **Bean Validation** - Validaciones en entidades y DTOs (`@NotBlank`, `@Email`, etc.)  
✅ **Exception Handling** - `@RestControllerAdvice` centralizado  
✅ **DTO Pattern** - Separación de modelo interno y externo  
✅ **ModelMapper** - Mapeo automático entre entidades y DTOs  

## 🔗 Endpoints Implementados

### GET /api/students
Lista todos los estudiantes registrados.
```bash
curl -X GET http://localhost:8081/api/students
```

### GET /api/students/{id}
Obtiene un estudiante por su ID.
```bash
curl -X GET http://localhost:8081/api/students/1
```

### POST /api/students
Crea un nuevo estudiante.
```bash
curl -X POST http://localhost:8081/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "email": "juan@email.com",
    "career": "Ingeniería",
    "age": 22
  }'
```

## 🚀 Instrucciones de Ejecución

### Prerrequisitos
- Java 21 o superior
- Maven 3.8+
- Puertos disponibles: 8761 (Eureka), 8081 (Estudiante)

### 1. Iniciar Eureka Server
```bash
cd eurekaserver
mvn spring-boot:run
```
El dashboard estará disponible en: http://localhost:8761

### 2. Iniciar Microservicio Estudiante
```bash
cd Estudiante
mvn spring-boot:run
```
El servicio se registrará automáticamente en Eureka.

### 3. Verificar Registro en Eureka
1. Abre http://localhost:8761
2. Busca `ESTUDIANTE-SERVICE` en la lista de instancias
3. Verifica que el status sea `UP`

### 4. Probar Endpoints
```bash
# Crear estudiante
curl -X POST http://localhost:8081/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"María García","email":"maria@email.com","career":"Medicina","age":23}'

# Listar estudiantes
curl -X GET http://localhost:8081/api/students

# Obtener por ID
curl -X GET http://localhost:8081/api/students/1
```

## 📦 Dependencias Principales

- **Spring Boot 3.5.14** - Framework base
- **Spring Web** - REST controllers
- **Spring Data JPA** - Persistencia
- **H2 Database** - Base de datos en memoria (dev)
- **Spring Cloud Netflix Eureka Client/Server** - Service discovery
- **ModelMapper 3.2.0** - Mapeo DTO ↔ Entity
- **Lombok** (opcional) - Reduce boilerplate code

## 🗄️ Base de Datos

Se utiliza H2 en memoria (modo desarrollo):
- **URL**: `jdbc:h2:mem:estudiante_db`
- **Consola H2**: http://localhost:8081/h2-console
- **Credenciales**:
  - Usuario: `sa`
  - Contraseña: (vacío)
  - JDBC URL: `jdbc:h2:mem:estudiante_db`

## 📝 Evidencia del Uso del Skill

Este proyecto fue desarrollado aplicando buenas prácticas de:

1. **Arquitectura limpia**: Separación de responsabilidades en capas
2. **Principios SOLID**: Especialmente Single Responsibility y Dependency Inversion
3. **Patrones de diseño**: DTO, Repository, Service Layer, Exception Handling
4. **Código limpio**: Nombres descriptivos, métodos pequeños,单一 responsabilidad
5. **Validación robusta**: Bean Validation en DTOs y entidades
6. **Manejo de errores**: Excepciones personalizadas y responses estructurados
7. **Documentación**: Código autodescriptivo, nombres claros
8. **Microservicios**: Service discovery con Eureka, configuración centralizada

## 🔍 Características Adicionales

- **Validaciones** automáticas con `@Valid`
- **Mapeo automático** Entity ↔ DTO con ModelMapper
- **Transacciones** declarativas con `@Transactional`
- **Respuestas HTTP apropiadas**: 200, 201, 404, 409, 400, 500
- **Logging** configurado por paquete
- **Perfiles** listos para desarrollo/producción

## 📁 Estructura del Proyecto

```
microserviciosExamen/
├── eurekaserver/           # Servidor Eureka
│   └── src/main/java/com/example/eurekaserver/
│       └── EurekaserverApplication.java
├── Estudiante/             # Microservicio Estudiante
│   └── src/main/java/com/example/Estudiante/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── exception/
│       ├── validation/
│       └── config/
└── README.md
```

## ✅ Validación del Proyecto

Para verificar que todo funciona correctamente:

1. **Eureka Dashboard**: http://localhost:8761 → Ver `ESTUDIANTE-SERVICE`
2. **H2 Console**: http://localhost:8081/h2-console → Conectar y ver tabla `students`
3. **API Health**: `curl http://localhost:8081/actuator/health` (si actuator está configurado)
4. **CRUD Operations**: Probar los 3 endpoints descritos

---

## 🏗️ Arquitectura del Microservicio Profesor

El microservicio `Profesor` sigue una **arquitectura por capas** con la siguiente estructura:

```
src/main/java/com/example/Profesor/
├── controller/          # Capa de presentación (REST endpoints)
│   └── ProfesorController.java
├── service/             # Capa de negocio (interface + implementación)
│   ├── ProfesorService.java
│   └── ProfesorServiceImpl.java
├── repository/          # Capa de persistencia (DAO)
│   └── ProfesorRepository.java
├── model/entity/        # Modelo de datos JPA
│   └── Profesor.java
├── dto/                # Objetos de transferencia de datos
│   ├── ProfesorCreateDTO.java
│   ├── ProfesorUpdateDTO.java
│   ├── ProfesorPatchDTO.java
│   └── ProfesorResponseDTO.java
├── exception/          # Manejo de excepciones personalizadas
│   ├── GlobalExceptionHandler.java
│   ├── ProfesorNotFoundException.java
│   └── EmailExistsException.java
└── ProfesorApplication.java  # Clase principal
```

### 📊 Diagrama de Capas

```
[REQUEST] → [Controller] → [Service] → [Repository] → [Database]
                ↓              ↓              ↓
            [DTO/Entity]  [DTO/Entity]  [Entity/JPA]
                ↓              ↓              ↓
           [Validation] [Business]    [SQL Queries]
                ↓              ↓              ↓
           [Exception Handling ←────────┘
```

### 🔄 Flujo de Datos

1. **Controller Layer**: Recibe peticiones HTTP, valida entrada con `@Valid`, maneja responses
2. **Service Layer**: Contiene la lógica de negocio, orquesta operaciones, usa transacciones
3. **Repository Layer**: Acceso a datos con Spring Data JPA, métodos CRUD
4. **Entity Layer**: Modelo de dominio persistente con anotaciones JPA
5. **DTO Layer**: Objetos para transferir datos (separación de concerns)
6. **Exception Layer**: Manejo centralizado de errores con respuestas HTTP apropiadas

---

## 🔗 Endpoints Profesor Service

### **Base URL:** `http://localhost:8082/api/profesores`

### 1. GET - Listar todos con Paginación
```http
GET /api/profesores?page=0&size=10
```
**Respuesta:** Página de profesores con metadatos de paginación

### 2. GET - Buscar por Nombre
```http
GET /api/profesores/search?nombre=Carlos
```
**Respuesta:** Lista de profesores que contienen "Carlos" en el nombre

### 3. GET - Buscar por Email
```http
GET /api/profesores/search?email=gomez@universidad.edu
```
**Respuesta:** Lista de profesores que contienen el email especificado

### 4. GET - Buscar por Nombre O Email
```http
GET /api/profesores/search?nombre=Carlos&email=gomez
```
**Respuesta:** Lista de profesores que coinciden con cualquiera de los criterios

### 5. GET - Obtener por ID
```http
GET /api/profesores/{id}
```
**Respuesta:** Profesor con el ID especificado

### 6. POST - Crear Profesor
```http
POST /api/profesores
Content-Type: application/json

{
  "nombre": "Carlos",
  "apellido": "Gómez",
  "email": "carlos.gomez@universidad.edu",
  "especialidad": "Ingeniería de Sistemas",
  "telefono": "1234567890"
}
```
**Respuesta:** Profesor creado con ID generado (201 Created)

### 7. PUT - Actualización Completa
```http
PUT /api/profesores/{id}
Content-Type: application/json

{
  "nombre": "Carlos Alberto",
  "apellido": "Gómez López",
  "email": "carlos.gomez@universidad.edu",
  "especialidad": "Ingeniería de Software",
  "telefono": "0987654321"
}
```
**Respuesta:** Profesor actualizado (todos los campos)

### 8. PATCH - Actualización Parcial
```http
PATCH /api/profesores/{id}
Content-Type: application/json

{
  "especialidad": "Ciencias de la Computación",
  "telefono": "5551234567"
}
```
**Respuesta:** Profesor actualizado (solo campos enviados)

### 9. DELETE - Eliminar Profesor
```http
DELETE /api/profesores/{id}
```
**Respuesta:** 204 No Content

---

## 🚀 Instrucciones de Ejecución

### Prerrequisitos
- Java 21 o superior
- Maven 3.8+
- Puertos disponibles: 8761 (Eureka), 8082 (Profesor)

### 1. Iniciar Eureka Server
```bash
cd eurekaserver
mvn spring-boot:run
```
El dashboard estará disponible en: http://localhost:8761

### 2. Iniciar Microservicio Profesor
```bash
cd Profesor
mvn spring-boot:run
```
El servicio se registrará automáticamente en Eureka.

### 3. Verificar Registro en Eureka
1. Abre http://localhost:8761
2. Busca `PROFESOR-SERVICE` en la lista de instancias
3. Verifica que el status sea `UP`

### 4. Probar Endpoints
```bash
# Crear profesor
curl -X POST http://localhost:8082/api/profesores \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María",
    "apellido": "García",
    "especialidad": "Matemáticas",
    "email": "maria@universidad.edu",
    "telefono": "1234567890"
  }'

# Listar profesores
curl http://localhost:8082/api/profesores?page=0&size=10

# Buscar por nombre
curl "http://localhost:8082/api/profesores/search?nombre=María"

# Actualizar parcialmente
curl -X PATCH http://localhost:8082/api/profesores/1 \
  -H "Content-Type: application/json" \
  -d '{"especialidad":"Física"}'
```

---

## 💾 Base de Datos H2 (En Memoria)

El microservicio utiliza H2 en memoria (no requiere instalación):

- **URL JDBC**: `jdbc:h2:mem:profesor_db`
- **Consola H2**: http://localhost:8082/h2-console
- **Usuario**: `sa`
- **Contraseña**: *(vacío)*
- **Tabla generada**: `profesores`

---

## 📦 Dependencias Principales

- **Spring Boot 3.5.14** - Framework base
- **Spring Web** - REST controllers
- **Spring Data JPA** - Persistencia
- **H2 Database** - Base de datos en memoria
- **Spring Cloud Netflix Eureka Client/Server** - Service discovery
- **Jakarta Validation** - Validación de datos

---

## ✅ Validaciones Implementadas

| Campo | Validación |
|-------|------------|
| `nombre` | @NotBlank, @Size(min=2, max=50) |
| `apellido` | @NotBlank, @Size(min=2, max=50) |
| `email` | @NotBlank, @Email, único en BD |
| `especialidad` | @NotBlank, @Size(min=3, max=100) |
| `telefono` | @Pattern(regexp="^[0-9]{9,15}$") |

---

## 🗄️ Entidad Profesor

```java
@Entity
@Table(name = "profesores")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min=2, max=50)
    private String nombre;

    @NotBlank @Size(min=2, max=50)
    private String apellido;

    @NotBlank @Email @Column(unique=true)
    private String email;

    @NotBlank @Size(min=3, max=100)
    private String especialidad;

    @Pattern(regexp="^[0-9]{9,15}$")
    private String telefono;
}
```

---

## 📝 Ejemplos JSON Completos (Postman)

Ver archivo: **[Profesor/EJEMPLOS_POSTMAN.md](./Profesor/EJEMPLOS_POSTMAN.md)**

Incluye:
- ✅ Requests para CRUD completo
- ✅ Búsquedas por nombre/email
- ✅ Ejemplos de errores de validación
- ✅ Casos de prueba completos

---

## 📁 Estructura del Proyecto

```
microserviciosExamen/
├── eurekaserver/          # Servidor Eureka (puerto 8761)
│   └── src/main/java/com/example/eurekaserver/
│       └── EurekaserverApplication.java
├── Estudiante/            # Microservicio Estudiante (puerto 8081)
│   └── src/main/java/com/example/Estudiante/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       └── exception/
├── Profesor/              # Microservicio Profesor (puerto 8082) ⭐ NUEVO
│   └── src/main/java/com/example/Profesor/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── model/entity/
│       ├── dto/
│       └── exception/
└── README.md
```

---

## 🎯 Características Implementadas

- ✅ **Arquitectura por capas** - Separación clara de responsabilidades
- ✅ **Paginación** - Soporte para `Pageable` en listados
- ✅ **Búsqueda** - Por nombre, email, o ambos combinados
- ✅ **Validaciones** - Jakarta Validation en DTOs
- ✅ **Manejo de excepciones** - GlobalExceptionHandler con responses consistentes
- ✅ **ResponseEntity** - Códigos HTTP apropiados (200, 201, 404, 409, 400, 500)
- ✅ **Eureka Client** - Registro automático en service discovery
- ✅ **H2 en memoria** - Sin necesidad de instalación externa
- ✅ **Código limpio y comentado** - Buenas prácticas Java
- ✅ **Maven** - Gestión de dependencias y build

---

## 🔍 Verificación

### Comandos para validar compilación:
```bash
cd Profesor
mvn clean compile    # Compilar
mvn test            # Ejecutar tests (si existen)
mvn spring-boot:run # Ejecutar aplicación
```

### URLs Importantes:
- **Eureka Dashboard**: http://localhost:8761
- **Profesor Service**: http://localhost:8082
- **H2 Console**: http://localhost:8082/h2-console
- ** health check**: http://localhost:8082/actuator/health (si actuator está habilitado)

---

## 📚 Recursos

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Cloud Netflix](https://spring.io/projects/spring-cloud-netflix)
- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)

---

*Microservicio Profesor implementado exitosamente con arquitectura limpia y todas las funcionalidades requeridas.*
#   m i c r o s e r v i c i o s E x a m e n 
 
 #   m i c r o s e r v i c i o s E x a m e n 
 
 
