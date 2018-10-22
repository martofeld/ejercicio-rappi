## Capas de la aplicacion
1. Capa de persistencia
- Se utilizo (room)[https://developer.android.com/topic/libraries/architecture/room] como abstraccion sobre una db SQLite de android para almacenar los generos y la configuracion. Generando de esta manera una cache de ambas.

2. Capa de red
- Se utilizo (retrofit)[https://square.github.io/retrofit/] para implementar los distintos requests. Retrofit permite tener una cache local para poder funcionar (por un breve periodo de tiempo) sin internet
- La paginacion de implemento utilizando (Paging)[https://developer.android.com/topic/libraries/architecture/paging/]

3. Capa de negocio
- Dependiendo la parte de la logica se puede encontrar en
    - `ConfigurationManager`: Encargado de obtener la configuracion de la api ya sea de la cache local o del servidor
    - `GenresManager`: Similar al `ConfigurationManager` pero maneja los generos
    - `ViewModel`: Encargados de coordinar los requests y mediante la utilizacion de LiveData informar a la vista que se obtuvo un resultado
    
4. Capa de vista
- Las vistas son manejadas por las activities o por los fragments, dependiendo el caso
    - `MainActivity`: Contiene un view pager que contiene 3 fragments, cada uno encargado de manejar su lista.
    - `DetailActivity`: No utiliza fragments, muestra en mayor detalle una pelicula
    
5. Asincronismo
- Utilizando un (EventBus)[https://github.com/greenrobot/EventBus] se puede enviar eventos a traves de la aplicacion, aislando a los componentes sobre el origen de dicho evento
- Mediante el uso LiveData, se evita el manejo de threads ya que funciona de manera similar a RxJava. Uno 'observa' objeto y cuando su valor percibe un cambio informa a los observers
- Para operaciones mas simples en background se utilizo un AsyncTask

6. Tests
- Los tests estan escritos utilizando (JUnit 5)[https://junit.org/junit5/docs/current/user-guide/] en su mayoria junto con (MockK)[https://mockk.io/] para los mocks.
- En caso de tener que testear algo de android se utilizo (Robolectric)[https://github.com/robolectric/robolectric] junto con JUnit 4

## Preguntas
1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?
Es la idea de que cada objeto debe ser responsable de una sola funcion, permitiendo asi tener objetos lo mas atomicos posibles que dependan de otros objetos para hacer tareas que no sepan como. De esta manera el codigo se vuelve mas simple y mas testeable.

2. Qué características tiene, según su opinión, un “buen” código o código limpio?
Un buen codigo es aquel se se puede entender con la necesidad minima de comentarios. Debe tener buenos nombres de variables, metodos y clases para facilitar la comprension. Los metodos deberian ser cortos y simples e idealmente no deberian tener 'side-effects'.