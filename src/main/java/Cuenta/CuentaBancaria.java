package Cuenta;


public class CuentaBancaria {
    private String titular;
    private double saldo;
    private String entidad;
    private String oficina;
    private String numCuenta;
    public static final int MAXLENGTH=100;
    public static final int minLENGTH=10;
    /** 
     *Titular Nombre del titular de la cuenta(entre 10 y 100 caracteres )
     *Entidad,Número de la entidad (4 dígitos numéricos)
     *Oficina, Número del código de la oficina (4 dígitos numéricos)
     *DC, Los dos dígitos de control
     *numCuenta, Número de la cuenta (10 dígitos numéricos)
     
     */
        public CuentaBancaria(String titular, String entidad, String oficina, String DC,String numCuenta) throws Exception{
            // Comprobamos la longitud de la cadena que introduciremos del titular
            if(titular.length()<minLENGTH || titular.length()>MAXLENGTH)
                throw new IllegalArgumentException("Solo se permitirá una cadena de caracteres entre 10 y 100 caracteres");
            //Comparo los siguientes valores y si no se introduce correctamente lanzará la expceción.
            if(!entidad.equals(String.valueOf(Integer.parseInt(entidad))))
                throw new IllegalArgumentException("Numero de entidad inválido");
            if(!oficina.equals(String.valueOf(Integer.parseInt(oficina))))
                throw new IllegalArgumentException("Numero de oficina inválido");
            if(!numCuenta.equals(String.valueOf(Integer.parseInt(numCuenta))))
                throw new IllegalArgumentException("Número de cuenta no válido");
            
                

            //Ahora comprobamos el CCC con el metodo
            System.out.println(titular);
            System.out.println(entidad + " length: "+ entidad.length());
            System.out.println(oficina + " length: " + oficina.length());
            System.out.println(DC + " length: " + DC.length());
            System.out.println(numCuenta + " length: " + numCuenta.length());

            if(!comprobarCCC(entidad+oficina+DC+numCuenta))
                throw new IllegalArgumentException("El codigo de cuenta del cliente es inválido");
            //Compruebo si los datos de control (DC)son validos
            //if(!DC.equals(obtenerDigitosControl(entidad,oficina,numCuenta)))
                //throw new IllegalArgumentException("Los digitos de control no son validos");
            this.titular=titular;
            this.entidad=entidad;
            this.numCuenta=numCuenta;
            this.saldo=0;
            
            
            
    }
    //Pondremos un constructor al cual solo se introducirá el titular y codigo de cuenta.
    public CuentaBancaria(String titular,String CCC) throws Exception{
        this (titular,CCC.substring(0, 4),CCC.substring(4, 8),CCC.substring(8, 10),CCC.substring(10, 20));
        
    }
    //Meteremos un metodo set para introducir la cadena recibida del titular
    public void setTitular(String titular)throws Exception{
        //Comprobamos la longitud de la cadena
        if(titular.length()>=minLENGTH && titular.length()<=MAXLENGTH){
            this.titular=titular;
            
        }else{
            throw new IllegalArgumentException("Solo se permiten de 10 a 100 caracteres en el nombre del titular");
            
        }

            
        }
        
    //Para obtener el dato que posee titular pondremos un metodo get
    public String getTitular(){
        return titular;    
    }
    
    //Otro metodo get para el saldo
    public double getSaldo(){
        return saldo;
        
    }
    
    //Ahora para la entidad
    public String getEntidad(){
        return entidad;
    }
    
    //Metodo que devuelva el codigo oficina
    public String getOficina(){
        return oficina;
    }
    //Metodo get para solo el nº de cuenta
    public String getNumeroCuenta(){
        return numCuenta;
        
    }
    
    /**Ahora faltan algunos metodos para poder "gestionar la cuenta"
     * añadire primero el de ingreso e iré haciendo los demas añadiendo un comentario
     * por cada metodo simplificando su funcion
     */
    public void ingresar(double cantidad)throws Exception{
        if(cantidad>=0){
            saldo+=cantidad;
        }else{
            throw new IllegalArgumentException("Solo se permiten valores positivos");
        }
    }
    //Ahora el metodo apra retirar saldo
    public void retirar(double cantidad)throws Exception{
        if(cantidad>=0){
            if(cantidad>=getSaldo()){
                saldo-=cantidad;
            }else{
                throw new IllegalArgumentException("El saldo es inferior a la cantidad deseada");
            }
        }else{
            throw new IllegalArgumentException("Solo se permite un valor positivo");
        }
    }
    //Ahora comprobare CCC
    
    public static boolean comprobarCCC(String CCC){
        boolean si=false;
        int valor;
        CCC=CCC.trim();
        //12345678901234567890
        //Ahora compruebo si el CCC tiene 20 digitos
        //si los tiene devolvera true en un if despues
        System.out.println(CCC.length());
        System.out.println(CCC);
        if (CCC.length()==20){
            si=true;
            for(int i=0;i<CCC.length();i++){
                valor=CCC.charAt(i)-48;
                if(valor<0 || valor>10 ){
                    si=false;
                    System.out.println(CCC);
                }
            }
            
        }
        if(si){
            return true;
        }else{
            return false;
        }
        
    }
    
    /**
     * Ahora el metdo que le pasaremos la entidad,oficina y nº cuenta
     * para obtener los digitos de control de la cuenta
     */
    
    public static String obtenerDigitosControl(String entidad,String oficina,String numCuenta){
        String DC="";
        int[] num_serie={1,2,4,8,5,10,9,7,3,6}; 
        //añadimos dos ceros para poder hacer los calculos
        String entidadOfi="0"+entidad+oficina;
        String numC=numCuenta;
        //Aqui incio dos variables que me haran falta
        int digCont1=0;
        int digCont2=0;
        /*
        for(int x=0;x<num_serie.length;x++){
            digCont1+=num_serie[x]*(entidadOfi.charAt(x)-48);
            digCont2+=num_serie[x]*(numC.charAt(x)-48);
        }
        */

        for(int x=0;x<entidadOfi.length();x++){
            digCont1+=num_serie[x]*(entidadOfi.charAt(x)-48);
        }

        for(int x=0;x<numC.length();x++){
            digCont2+=num_serie[x]*(numC.charAt(x)-48);
        }

        //le restamos a 11 el resto de la division 
        digCont1=11-(digCont1%11);
        digCont2=11-(digCont2%11);
        
        // Comprobamos si nos ha dado 11(sera 0) o 10 (sera 1)
        digCont1=digCont1==11?0:digCont1==10?1:digCont1;
        digCont2=digCont2==11?0:digCont2==10?1:digCont2;
        //ahora toca unir los valores en el DC
        DC=String.valueOf(digCont1)+String.valueOf(digCont2);
        return DC;
    }
    /**
     * Ahora hare un metodo para sobreescribir la funcion toString para contener
     * el titular,cod y saldo actual
     */
    public String toString(){
        return titular+" CC: "+numCuenta+"El saldo es: "+saldo;
    }
}
