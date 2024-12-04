# **Parcial 2 Eventos

## **Descripción General**
Esta aplicación consta de tres ejercicios diferentes, cada uno implementado como un módulo funcional. La app utiliza principios de diseño modernos (Material Design), acceso a Firestore, y se estructura siguiendo principios SOLID para garantizar un desarrollo limpio y mantenible.

---

## **Ejercicio 1: Gestión de Horarios**
### **Funcionalidad**
Permite al usuario gestionar horarios de clases a lo largo de la semana:
- Listar todas las clases existentes por día.
- Añadir nuevas clases, especificando el día, hora de inicio, y hora de fin.
- Ver qué clase toca en este momento.

### **Detalles Técnicos**
- **Firestore:** Base de datos utilizada para guardar y recuperar las clases.
- **UI:** 
  - Diseño con pestañas para navegar entre días de la semana.
  - Botón flotante para añadir nuevas clases.
- **Componentes Principales:**
  - `ViewScheduleActivity`: Muestra las clases diarias.
  - `AddClaseActivity`: Formulario para añadir una nueva clase.
  - `QueTocaActivity`: Pantalla para mostrar la clase actual.

---

## **Ejercicio 2: Lista de Eventos**
### **Funcionalidad**
Permite al usuario gestionar un listado de eventos:
- Listar todos los eventos disponibles.
- Registrar un nuevo evento con los campos:
  - Nombre, descripción, dirección, precio, fecha y aforo.
- Al seleccionar un evento, el usuario puede ver más detalles.

### **Detalles Técnicos**
- **Firestore:** Base de datos utilizada para almacenar los eventos.
- **UI:**
  - `RecyclerView` para listar los eventos.
  - Botón flotante para añadir nuevos eventos.
- **Componentes Principales:**
  - `ListEventosActivity`: Pantalla para listar los eventos.
  - `RegistroEventoActivity`: Formulario para añadir nuevos eventos.

---

## **Ejercicio 3: Farmacias de Zaragoza**
### **Funcionalidad**
Permite al usuario buscar y visualizar farmacias en Zaragoza:
- Listar todas las farmacias recuperadas de Firestore.
- Al seleccionar una farmacia, se muestra un mapa con su ubicación.
- Posibilidad de añadir farmacias directamente desde el mapa.

### **Detalles Técnicos**
- **Google Maps API:** Para mostrar el mapa y marcar las ubicaciones de las farmacias.
- **Firestore:** Base de datos para almacenar y recuperar información sobre las farmacias.
- **UI:**
  - `RecyclerView` para listar las farmacias.
  - Mapa interactivo con marcadores para farmacias.
- **Componentes Principales:**
  - `PharmacyListActivity`: Lista las farmacias.
  - `PharmacyDetailActivity`: Detalles y ubicación de la farmacia.
  - `PharmacyMapActivity`: Muestra el mapa con las farmacias y permite añadir nuevas.

---
https://github.com/paatriksirbu/Parcial2Eventos.git
