## Ejecutar el proyecto

### Prerrequisitos
- Java JDK 11 o superior instalado  
- Tener la variable `JAVA_HOME` apuntando al directorio del JDK  
- (Opcional) Powershell en Windows o terminal Bash en Linux/Mac

---

### 1. Compilar todo el código

<details>
<summary><strong>En Windows PowerShell</strong></summary>

```bash

# Compila todos los .java de forma recursiva
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)

```

Para SRP:
java -cp out srp.Main

Para OCP:
java -cp out ocp.Main

Para LSP:
java -cp out lsp.Main

Para ISP:
java -cp out isp.Main

Para DIP:
java -cp out dip.Main


Single Responsibility Principle (SRP)
Cómo se aplicó
Para cumplir el SRP, la clase monolítica UserManager –que validaba, persistía y enviaba notificaciones– se dividió en cuatro componentes especializados:

EmailValidator: valida el formato del correo electrónico.

PasswordValidator: valida la longitud y criterio de la contraseña.

UserRepository: se encarga exclusivamente de la persistencia (guardar usuario).

EmailService: gestiona el envío de correos de bienvenida.

La nueva UserManager sólo orquesta el flujo (invoca validadores, repositorio y servicio de correo), pero no contiene lógica de validación ni de acceso a datos.

Problemas que se resolvieron

Acoplamiento excesivo: antes, todo cambio en la validación o en el envío de correos requería modificar UserManager. Ahora, cada responsabilidad vive en su propia clase y sólo esa clase se modifica si evoluciona su lógica.

Difícil testabilidad: al estar todo mezclado, era casi imposible aislar casos de prueba para validación, persistencia o notificación. Con SRP, cada componente puede probarse unitariamente.

Baja mantenibilidad: la lógica amontonada dificultaba entender o extender funcionalidades. Tras la refactorización, el código es más legible y modular.

Riesgo de errores al extender: al añadir nuevos comportamientos (p. ej., otro tipo de validación o cambiar la forma de almacenar datos), antes se corría el riesgo de romper otras tareas; ahora basta con crear o actualizar la clase correspondiente.

Este diseño ha dejado cada módulo más cohesionado, facilitando su evolución y reduciendo la probabilidad de errores en el futuro.


Open-Closed Principle (OCP)
Cómo se aplicó

Se definió la interfaz Notification con el método send(String message).

Se crearon clases concretas que implementan esa interfaz: EmailNotification, SMSNotification, PushNotification (y un ejemplo anónimo de FaxNotification).

NotificationService dejó de contener if/else sobre el tipo; ahora recibe cualquier instancia de Notification y delega en su método send.

Para añadir un nuevo canal de notificación basta con implementar Notification (sin tocar NotificationService), cumpliendo la idea de “abrir para extensión, cerrar para modificación”.

Problemas que se resolvieron

Condicionales acoplados: antes, cada vez que se agregaba un nuevo tipo había que editar NotificationService, exponiéndolo a errores y regresiones.

Baja extensibilidad: incorporar un nuevo medio (SMS, Push, Fax, etc.) requería alterar lógica existente. Ahora solo se añade una clase independiente.

Difícil testabilidad: los tests de envío estaban concentrados en una única clase con múltiples ramas. Tras la refactorización, cada notificación se prueba aisladamente.

Mantenibilidad y legibilidad: el código quedó más limpio, modular y fácil de comprender; cada clase aborda un único comportamiento.



Liskov Substitution Principle (LSP)
Cómo se aplicó

Se redefinió la jerarquía de Animal para que la clase base solo contenga el método común makeSound().

Se creó la interfaz Walkable con el método walk().

Dog extiende Animal e implementa Walkable, por lo que puede emitir sonido y caminar.

Fish extiende únicamente Animal (no implementa Walkable), pues no corresponde que tenga walk().

En el código cliente (Main), al iterar sobre Animal, se verifica con instanceof Walkable antes de invocar walk(). De este modo, cualquier Animal sustituye a la base sin riesgo de lanzar excepciones inesperadas.

Problemas que se resolvieron

Excepciones en tiempo de ejecución: antes, Fish.walk() lanzaba UnsupportedOperationException, rompiendo la sustitución. Ahora no existe ese método en su contrato.

Violación de contrato: cada subclase solo provee los comportamientos que realmente puede cumplir, respetando el contrato de la clase o interfaz que implementa.

Mayor seguridad al extender: es posible añadir nuevas subclases de Animal sin preocuparse de implementar métodos inaplicables ni de afectar a otras.

Claridad y previsibilidad: el cliente sabe que solo aquellos animales que implementan Walkable pueden caminar, eliminando chequeos de errores inesperados y favoreciendo un código más robusto.



Interface Segregation Principle (ISP)
Cómo se aplicó

Se dividió la interfaz Device original en dos interfaces más pequeñas y específicas:

Switchable con los métodos turnOn() y turnOff().

Rechargeable con el método charge().

Phone implementa ambas interfaces (Switchable y Rechargeable), ya que puede encenderse, apagarse y cargarse.

DisposableCamera implementa solo Switchable, porque no es recargable y por tanto no está obligada a definir charge().

Problemas que se resolvieron

Implementaciones forzadas y excepciones: antes la cámara desechable tenía que proveer charge() y lanzaba UnsupportedOperationException. Ahora ya no existe ese método innecesario en su contrato.

Acoplamiento innecesario: las clases sólo dependen de los métodos que verdaderamente usan, reduciendo el riesgo de llamadas inválidas.

Mayor claridad semántica: está explícito en el tipo de cada objeto si puede recargarse o no; el diseño comunica mejor las capacidades de cada dispositivo.

Facilidad de evolución: si en el futuro se agrega otro comportamiento (por ejemplo, Connectable para dispositivos con Bluetooth), basta con crear otra interfaz, sin tocar las existentes ni forzar métodos en clases que no correspondan.



Dependency Inversion Principle (DIP)
Cómo se aplicó

Se creó la interfaz PaymentMethod con el método processPayment(double amount), que representa la abstracción de cualquier forma de pago.

Se implementaron clases concretas que cumplen esa interfaz: CreditCardPayment, PayPalPayment (y se podría añadir fácilmente CryptoPayment, etc.).

PaymentProcessor dejó de instanciar directamente CreditCardPayment; en su lugar recibe en el constructor cualquier PaymentMethod. De este modo, el módulo de alto nivel (PaymentProcessor) y los módulos de bajo nivel (CreditCardPayment, PayPalPayment…) dependen de la misma abstracción.

En Main, se demuestra la inyección de dependencias creando distintos PaymentProcessor con diferentes PaymentMethod, sin cambiar nada dentro de PaymentProcessor.

Problemas que se resolvieron

Acoplamiento fuerte: antes PaymentProcessor dependía de una clase concreta (CreditCardPayment), por lo que incorporar otro medio de pago implicaba modificarlo. Ahora basta con implementar la interfaz.

Difícil extensión: agregar un nuevo método de pago no obliga a tocar la lógica de PaymentProcessor, evitando posibles regresiones.

Baja testabilidad: al depender de una implementación concreta era complicado probar PaymentProcessor aislado. Con DIP, se pueden inyectar mocks de PaymentMethod para pruebas unitarias.

Claridad de responsabilidades: PaymentProcessor solo orquesta flujos de pago, y cada PaymentMethod se encarga de su propia lógica, respetando la separación de conceptos y facilitando el mantenimiento.