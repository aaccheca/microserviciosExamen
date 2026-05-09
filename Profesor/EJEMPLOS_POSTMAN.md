# 🧪 Ejemplos de Pruebas - Profesor Service (Postman)

## 📋 Colección de Requests

---

### 1️⃣ POST - Crear Profesor
**URL:** `POST http://localhost:8082/api/profesores`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ingeniería de Software",
  "telefono": "1234567890"
}
```

**Respuesta esperada (201 Created):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ingeniería de Software",
  "telefono": "1234567890"
}
```

---

### 2️⃣ GET - Listar Todos (con Paginación)
**URL:** `GET http://localhost:8082/api/profesores?page=0&size=10`

**Respuesta esperada (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "nombre": "Juan Carlos",
      "apellido": "Pérez García",
      "email": "juan.perez@universidad.edu",
      "especialidad": "Ingeniería de Software",
      "telefono": "1234567890"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 1,
  "empty": false
}
```

---

### 3️⃣ GET - Buscar por Nombre
**URL:** `GET http://localhost:8082/api/profesores/search?nombre=Juan`

**Respuesta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "email": "juan.perez@universidad.edu",
    "especialidad": "Ingeniería de Software",
    "telefono": "1234567890"
  }
]
```

---

### 4️⃣ GET - Buscar por Email
**URL:** `GET http://localhost:8082/api/profesores/search?email=juan.perez@universidad.edu`

**Respuesta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "email": "juan.perez@universidad.edu",
    "especialidad": "Ingeniería de Software",
    "telefono": "1234567890"
  }
]
```

---

### 5️⃣ GET - Buscar por Nombre o Email
**URL:** `GET http://localhost:8082/api/profesores/search?nombre=Juan&email=universidad`

**Respuesta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "email": "juan.perez@universidad.edu",
    "especialidad": "Ingeniería de Software",
    "telefono": "1234567890"
  }
]
```

---

### 6️⃣ GET - Obtener por ID
**URL:** `GET http://localhost:8082/api/profesores/1`

**Respuesta esperada (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez García",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ingeniería de Software",
  "telefono": "1234567890"
}
```

**Error (404 Not Found):**
```json
{
  "timestamp": "2026-05-09T12:00:00.123456",
  "status": 404,
  "message": "Profesor no encontrado con ID: 999"
}
```

---

### 7️⃣ PUT - Actualización Completa
**URL:** `PUT http://localhost:8082/api/profesores/1`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ingeniería de Software Avanzado",
  "telefono": "9876543210"
}
```

**Respuesta esperada (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ingeniería de Software Avanzado",
  "telefono": "9876543210"
}
```

---

### 8️⃣ PATCH - Actualización Parcial
**URL:** `PATCH http://localhost:8082/api/profesores/1`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON) - Solo campos a modificar:**
```json
{
  "especialidad": "Ciencias de la Computación",
  "telefono": "5551234567"
}
```

**Respuesta esperada (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "email": "juan.perez@universidad.edu",
  "especialidad": "Ciencias de la Computación",
  "telefono": "5551234567"
}
```

---

### 9️⃣ DELETE - Eliminar Profesor
**URL:** `DELETE http://localhost:8082/api/profesores/1`

**Respuesta esperada (204 No Content):**
*(Sin cuerpo de respuesta)*

---

## ❌ Ejemplos de Errores de Validación

### Error 400 - Validación Fallida
**Escenario:** Enviar email inválido en POST

```json
{
  "timestamp": "2026-05-09T12:05:00.123456",
  "status": 400,
  "message": "Error de validación en los datos de entrada",
  "errors": {
    "email": "El email debe tener un formato válido",
    "nombre": "El nombre debe tener entre 2 y 50 caracteres"
  }
}
```

### Error 409 - Email Duplicado
**Escenario:** Intentar crear profesor con email existente

```json
{
  "timestamp": "2026-05-09T12:06:00.123456",
  "status": 409,
  "message": "Ya existe un profesor con el email: juan.perez@universidad.edu"
}
```

### Error 400 - Parámetro Inválido
**Escenario:** Enviar ID no numérico

```json
{
  "timestamp": "2026-05-09T12:07:00.123456",
  "status": 400,
  "message": "Error en el formato del parámetro 'id': abc"
}
```

---

## 📊 Script SQL Inicial (H2 Console)

Puedes acceder a la consola H2 en: `http://localhost:8082/h2-console`

**Configuración para conectar:**
- JDBC URL: `jdbc:h2:mem:profesor_db`
- User Name: `sa`
- Password: *(vacío)*

**SQL para ver datos:**
```sql
SELECT * FROM profesores;
```

---

## 🎯 Casos de Prueba Completos

### 📝 **Caso 1: Flujo Completo CRUD**
1. POST crear profesor
2. GET todos (verificar creación)
3. GET por ID
4. PATCH actualizar especialidad
5. GET por ID (verificar cambio)
6. PUT actualizar todo
7. GET por ID (verificar cambio)
8. DELETE eliminar
9. GET por ID (debe dar 404)

### 🔍 **Caso 2: Búsquedas**
1. Crear 3 profesores:
   - "María García" - maria@edu.com
   - "María López" - maria.lopez@edu.com
   - "Carlos Ruiz" - carlos@edu.com
2. Buscar por nombre "María" → debe devolver 2
3. Buscar por email "maria@edu.com" → debe devolver 1
4. Buscar por nombre "María" y email "edu.com" → debe devolver 2

### ⚠️ **Caso 3: Validaciones**
1. POST sin nombre → 400
2. POST email inválido → 400
3. POST email duplicado → 409
4. PATCH con email duplicado → 409

---

## 🚀 Comandos Rápidos

### Iniciar Eureka Server:
```bash
cd eurekaserver
mvn spring-boot:run
```

### Iniciar Profesor Service:
```bash
cd Profesor
mvn spring-boot:run
```

### Compilar:
```bash
cd Profesor
mvn clean install
```

### Ejecutar Tests:
```bash
cd Profesor
mvn test
```

---

## 📌 Notas Importantes

- **Puerto Profesor Service:** 8082
- **Puerto Eureka Server:** 8761
- **Base de Datos:** H2 en memoria (no requiere instalación)
- **Consola H2:** http://localhost:8082/h2-console
- **Eureka Dashboard:** http://localhost:8761
- **Formato JSON:** Todos los requests y responses usan JSON
- **Paginación:** Por defecto `page=0`, `size=10`

---

*Documento generado automáticamente - Microservicio Profesor*
