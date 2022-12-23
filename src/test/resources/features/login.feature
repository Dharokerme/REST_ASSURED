Feature: Inicio de sesion
  Como un usuario registrado en el sistema
  necesido validar que las operaciones de logeo y disponibilidad al sitio web
  para poder tener seguridad en el perfil de los usuarios

  Scenario: login exitoso
    Given el usuario esta en la pagina de inicio de sesion con el correo de usuario y la contrasena
    When el usuario presiona el boton de inicio de sesion
    Then el usuario deberia ver un codigo de repuesta exitoso y un token de respuesta