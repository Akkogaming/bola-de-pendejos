public class Empleado{

  //Variables
  private name;
  private firstSurname;
  private secondSurname;
  private phone;
  private eMail;

  //Methods
  public Empleado(String name,String firstSurname,String secondSurname,String eMail){
  }

  public Empleado(){}

  public void setName(String name){
    this.name=name;
  }

    public void setFirstSurname(String firstSurname){
    this.firstSurname=firstSurname;
  }

    public void setSecondSurname(String secondSurname){
    this.secondSurname=secondSurname;
  }

    public void setPhone(String phone){
    this.phone=phone;
  }

    public void setEmail(String eMail){
    this.eMail=eMail;
  }

  public String name(){
    return name;
  }

  public String firstSurname(){
    return firstSurname;
  }

  public String secondSurname(){
    return secondSurname;
  }
  
  public String getPhone(){
    return phone;
  }

  public String getEmail(){
    return eMail;
  }
}
