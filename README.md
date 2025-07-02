# 📕 App de libros autores 📖

Este proyecto con java y POO que consume datos de una api y luego los guarda en una base de datos, en base a esos datos almacenados, se realizan diferentes consultas.


---

## 📁 Estructura de Archivos

- `Paquete model`: creacion de clases y records para que puedan ser mapeados por medio de JPA a la DB
- `Paquete principal`: menu, metodos de las opciones del menu e inicio de repositorios
- `Paquete de repositorios`: Repositorios para libros y autores con consultas a la db 
- `Paquete servicios`: Clases para realizar consulta http a la api y conversiond de datos

---

## 🧰 Tecnologías utilizadas

- Java 17+
- `java.net.http.HttpClient` para peticiones HTTP.
- `jackson` para parsear JSON.
- `postgresql` para el uso de una base de datos para la persistencia
- API pública de [gutendex-API](https://gutendex.com/books/?search=).

---

## 📌 Ejemplo de uso
-- 1 - Buscar libro por titulo
-- 2 - Listar libros registrados
-- 3 - Listar autores registrados
-- 4 - Listar autores vivos en un determinado año
-- 5 - Listar libros por idiomas                   
-- 0 - Salir

Ingresa una opcion por teclado:
2
Procesando ...
| id | descargas | idiomas | título                      | autor_id |
|----|-----------|---------|-----------------------------|----------|
| 9  | 3012      | ru      | 1001 задача для умств       | 5        |
| 10 | 461       | en      | I and My Chimney            | 6        |
| 11 | 93457     | en      | Moby Dick; Or, The Whale    | 6        |
| 12 | 29617     | en      | My Life — Volume 1          | 7        |


---

## 📄 Licencia

Este proyecto se desarrolló con fines educativos y es de uso libre.
