# CreditStore Backend

Este es el repositorio del backend para CreditStore, una aplicación web diseñada para ayudar a pequeños establecimientos comerciales a gestionar eficientemente las cuentas corrientes por crédito otorgadas a sus clientes.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Maven

## Requisitos previos

- JDK 17 o superior
- Maven 3.6.3 o superior
- MySQL 8.0 o superior

## Configuración del proyecto

1. Clona el repositorio:
   ```
   git clone https://github.com/Finanzas-SI84-CreditStore/CreditStore-API.git
   ```

2. Navega al directorio del proyecto:
   ```
   cd CreditStore-API
   ```

3. Configura la base de datos MySQL en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/creditstore
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Ejecuta la aplicación:
   ```
   mvn spring-boot:run
   ```

El servidor estará disponible en `http://localhost:8080`.

## Funcionamiento y Lógica de la Aplicación

CreditStore es una aplicación diseñada para gestionar créditos en pequeños establecimientos comerciales. A continuación, se detalla el funcionamiento principal y la lógica detrás de las operaciones clave:

### 1. Gestión de Clientes

- Los dueños de negocios pueden registrar nuevos clientes en el sistema.
- Cada cliente tiene un perfil que incluye información personal y un límite de crédito asignado.

### 2. Otorgamiento de Créditos

- Cuando un cliente realiza una compra a crédito, el sistema verifica que el monto no exceda el límite de crédito del cliente.
- Se registra la compra a crédito, incluyendo el monto, la fecha y los términos del crédito.

### 3. Cálculo de Intereses

El sistema utiliza diferentes fórmulas para calcular los intereses, dependiendo del tipo de tasa configurada:

- **Tasa Efectiva Anual (TEA)**:
  ```
  TEA = (1 + TNA / (N° × A / Frec))^(N°×A / Frec) - 1
  ```
  Donde:
    - TNA: Tasa Nominal Anual
    - N°×A: Número de días por año
    - Frec: Frecuencia de pago

- **Tasa Efectiva Mensual (TEM)**:
  ```
  TEM = (1 + TEA)^(Frec / N°×A) - 1
  ```

### 4. Planes de Pago

El sistema soporta diferentes planes de pago:

- **Pago a vencimiento**: Un único pago al final del período de crédito.
- **Pago mensual**: Pagos regulares durante un período determinado.

Para el cálculo de cuotas mensuales, se utiliza el método francés:

```
R = C × (TEP × (1 + TEP)^n) / ((1 + TEP)^n - 1)
```
Donde:
- R: Cuota a pagar
- C: Monto inicial
- TEP: Tasa efectiva del período
- n: Número de cuotas

### 5. Gestión de Pagos

- Los clientes pueden realizar pagos parciales o totales de sus deudas.
- El sistema actualiza el saldo de la deuda después de cada pago.

### 6. Cálculo de Intereses Moratorios

Si un cliente se retrasa en sus pagos, se aplica un interés moratorio:

```
IM = Capital × (Tasa de interés moratorio / 100) × (Días de mora / 360)
```

### 7. Generación de Reportes

El sistema puede generar varios tipos de reportes, incluyendo:
- Estado de cuenta de clientes
- Resumen de créditos otorgados
- Análisis de morosidad

### 8. Flujo de Trabajo Principal

1. El dueño del negocio registra un nuevo cliente y establece su límite de crédito.
2. Cuando el cliente realiza una compra a crédito, se registra en el sistema.
3. El sistema calcula las cuotas y los intereses según el plan de pago elegido.
4. Se generan fechas de pago para cada cuota.
5. El cliente realiza pagos, que se registran en el sistema.
6. Si hay retrasos, se calculan y aplican intereses moratorios.
7. El dueño del negocio puede generar reportes en cualquier momento para ver el estado de los créditos.

### 9. Seguridad

- La aplicación implementa autenticación y autorización para proteger los datos de los clientes.
- Solo usuarios autorizados pueden acceder a la información financiera y realizar operaciones.

## API Endpoints

A continuación se detallan los principales endpoints de la API:

- `POST /api/register`: Registrar un nuevo usuario
- `POST /api/login`: Iniciar sesión
- `GET /api/clients`: Obtener lista de clientes
- `POST /api/clients`: Crear un nuevo cliente
- `GET /api/credits`: Obtener lista de créditos
- `POST /api/credits`: Crear un nuevo crédito
- `POST /api/payments`: Registrar un pago
- `GET /api/reports`: Generar reportes

Para más detalles sobre los endpoints y sus parámetros, consulta la documentación de la API.

## Contribución

Si deseas contribuir al proyecto, por favor:

1. Haz un fork del repositorio
2. Crea una nueva rama (`git checkout -b feature/AmazingFeature`)
3. Haz commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Haz push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.