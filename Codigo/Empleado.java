public class Empleado {
  // Variables
  private String nombre;
  private String A_paterno;
  private String A_materno;
  private String telefono;
  private String correo_E;

  // Constructor
  public Empleado(String nombre, String A_paterno, String A_materno, String telefono, String correo_E) {
      this.nombre = nombre;
      this.A_paterno = A_paterno;
      this.A_materno = A_materno;
      this.telefono = telefono;
      this.correo_E = correo_E;
  }

  // Métodos Setters y Getters
  public void setNombre(String nombre) { this.nombre = nombre; }
  public void setA_paterno(String A_paterno) { this.A_paterno = A_paterno; }
  public void setA_materno(String A_materno) { this.A_materno = A_materno; }
  public void setTelefono(String telefono) { this.telefono = telefono; }
  public void setCorreo_E(String correo_E) { this.correo_E = correo_E; }

  public String getNombre() { return nombre; }
  public String getA_paterno() { return A_paterno; }
  public String getA_materno() { return A_materno; }
  public String getTelefono() { return telefono; }
  public String getCorreo_E() { return correo_E; }
}
