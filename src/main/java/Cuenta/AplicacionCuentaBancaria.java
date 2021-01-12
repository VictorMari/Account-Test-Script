
package Cuenta;
import java.io.*;
/**
 * 
 * @author Alejandro
 */
public class AplicacionCuentaBancaria {
    static BufferedReader dato = new BufferedReader(new InputStreamReader(System.in));
    static CuentaBancaria cuenta;    
    public static void main(String[] args) throws Exception {
        String titul,cc;
        boolean digi,volver;
        do{
            try{
                volver=false;
                // Solicitamos el nombre del titular (error de longitud capturado en la clase)
                System.out.println("Introduzca el nombre del titular de la cuenta:");
                titul=dato.readLine();
                do{
                    // Introducimos los 20 dígitos de la cuenta y comprobamos ¿son 20 y numéricos?
                    // En caso contrario lo volvemos a solicitar
                    digi=true;
                    System.out.println("Introduzca el Código de la Cuenta del Cliente (20 dígitos numéricos):");
                    cc=dato.readLine();
                    for(int i=0;i<cc.length();i++){
                        if(cc.charAt(i)-48<0 || cc.charAt(i)-48>9) digi=false;
                    }
                    if(!digi) System.err.println("Sólo dígitos numéricos (20 dígitos)");
                }while(cc.length()!=20 || !digi);
                // en caso de ser datos correctos creamos la clase cuenta
                cuenta=new CuentaBancaria(titul,cc);
            }catch(IOException ioe){
                System.err.println("Error en la entrada de datos");
                volver=true;
            }catch(IllegalArgumentException iae){
                System.err.println(iae);
                volver=true;
            }
        }while(volver);
        int opcion=0;
        do{
            try{
                System.out.println("  menu de opciones  ");
                System.out.println("_______________________________________________________");
                System.out.println("1- Ver el numero de cuenta al completo");
                System.out.println("2- Ver el titular de la cuenta");
                System.out.println("3- Ver el codigo de entidad");
                System.out.println("4- Ver el codigo de la oficina");
                System.out.println("5- Ver el numero de la cuenta");
                System.out.println("6- Ver los digitos de control de cuenta");
                System.out.println("7- Realizar ingreso");
                System.out.println("8- Retirar saldo en efectivo");
                System.out.println("9- Consultar el saldo");
                System.out.println("0- Salir.");
                System.out.println("_______________________________________________________");
                System.out.println("  Que desea hacer?  ");
                opcion=Integer.parseInt(dato.readLine());
                System.out.println("opcion:"+opcion);
                switch (opcion){
                    case 0:
                        break;
                    case 1:
                        System.out.printf("Nº de cuenta: %s-%s-%s-%s\n",cuenta.getEntidad(),cuenta.getOficina(),CuentaBancaria.obtenerDigitosControl(cuenta.getEntidad(), cuenta.getOficina(), cuenta.getNumeroCuenta()),cuenta.getNumeroCuenta());
                        break;
                    case 2:
                        System.out.printf("Titular: %s\n",cuenta.getTitular());
                        break;
                    case 3:
                        System.out.printf("Código de la entidad: %s\n", cuenta.getEntidad());
                        break;
                    case 4:
                        System.out.printf("Código de la oficina %s\n", cuenta.getOficina());
                        break;
                    case 5:
                        System.out.printf("Cuenta nº: %s\n", cuenta.getNumeroCuenta());
                        break;
                    case 6:
                        System.out.printf("Dígitos de control: %s\n", CuentaBancaria.obtenerDigitosControl(cuenta.getEntidad(), cuenta.getOficina(), cuenta.getNumeroCuenta()));
                        break;
                    case 7:
                        ingreso();
                        break;
                    case 8:
                        retirada();
                        break;
                    case 9:
                        System.out.printf("Saldo: %s €", cuenta.getSaldo());
                        break;
                    default:
                        System.out.println("Introduzca un valor entre 0 y 9");
                }
            }catch (NumberFormatException nfe){
                System.err.println("Sólo son válidos valores enteros entre 0 y 9");
            }catch (IOException ioe){
                System.err.println("Error en la entrada de datos");
            }catch (IllegalArgumentException iae){
                System.err.println(iae);
            }
        }while(opcion != 0);
    }
    

    
    /*
     * Metodo que solicita la cantidad a ingresar y comprueba que se han introducido
     * valores correctos
     */
    public static void ingreso() throws Exception{
        double dinero;
        try{
            System.out.println("¿Qué cantidad desea ingresar?");
            dinero=Double.parseDouble(dato.readLine());
            if(dinero<0) throw new IllegalArgumentException("Sólo números positivos");
            cuenta.ingresar(dinero);
        }catch(NumberFormatException nfe){
            System.err.println("Sólo son válidos valores numéricos.");
        }catch(IOException ioe){
            System.err.println("Error de entrada de datos");
        }catch(IllegalArgumentException iae){
            System.err.println(iae);
        }
        
    }
    
    public static void retirada() throws Exception{
        double dinero;
        try{
            System.out.println("¿Qué cantidad desea retirar?");
            dinero=Double.parseDouble(dato.readLine());
            if(dinero<0) throw new IllegalArgumentException("Sólo números positivos");
            cuenta.retirar(dinero);
        }catch(NumberFormatException nfe){
            System.err.println("Sólo son válidos valores numéricos.");
        }catch(IOException ioe){
            System.err.println("Error de entrada de datos");
        }catch(IllegalArgumentException iae){
            System.err.println(iae);
        }
        
    }
}