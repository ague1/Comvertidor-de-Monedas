package src;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class pricipal {


    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner lecture = new Scanner(System.in);

        String menu = """
                ~~~~~Sea Bienvenido al Convertidor de Monedas ~~~
                1.-Opciones de Moneda
                2.-Cambio de Moneda
                3.-Salir
                ~~~~~ Eliga una opticion ~~~~~~
                """ ;
        System.out.println(menu);
        try {
            int option = lecture.nextInt();//guarda el valor de la opcion que se ingreso
            lecture.nextLine();//Limapia
            while (option != 3) {
                System.out.println("Ingresa la moneda de Origen ");
                String currencyOrigin = lecture.nextLine().toUpperCase(); //Guarda el valor de moneda de origen

                String url = "https://v6.exchangerate-api.com/v6/96743b975dcde642438f6366/latest/" + currencyOrigin;


                HttpClient customer = HttpClient.newHttpClient();//Instancia para recibir y enviar informacion
                HttpRequest request = HttpRequest.newBuilder() //Inicio de construccion de consulta
                        .uri(URI.create(url))// toma la url para la solicitud
                        .build();//Finaliza la consulta, con informacion

                HttpResponse<String> response = customer.send(request, HttpResponse.BodyHandlers.ofString());//Aalmacena y : Envia la info de la consulta en cadena
                String json = response.body(); // contiene la informacion enviada

                Gson gson = new Gson(); // se declara la biblioteca
                dataMoneda data = gson.fromJson(json, dataMoneda.class);// se declara una intancia de la clase dataMoneda, tomando los parametros
                Map<String, Double> rates = data.conversion_rates();// Toma los  valores de tasas de monedas

                if (option == 1) {
                    System.out.println("Catalogo de Monedas");
                    for (String coins : rates.keySet()) { //Guarda en coin el conjunto de valores clave de las monedas
                        System.out.println("-" + coins);
                    }
                    System.out.println("~~");
                } else if (option == 2) {

                    System.out.println("Ingresa la moneda de cambio ");
                    String currencyChange = lecture.nextLine().toUpperCase();
                    System.out.println("Ingrese la Cantidad a Cambiar");
                    int amount = lecture.nextInt();

                    if (rates.containsKey(currencyChange)) { //Si contiene el valor de clave moneda de cambio
                        Double value = rates.get(currencyChange); //Toma el valor de tasa de esa moneda
                        System.out.println("Resultado : " + value * amount + " peso " + currencyChange);
                    } else {
                        System.out.println("Moneda no encontrada.");
                    }
                }
                System.out.println(menu);
                option = lecture.nextInt();
                lecture.nextLine();

            }

        }catch (InputMismatchException e) {
            System.out.println("Error: Debe de ser un numero");
        }catch ( JsonSyntaxException e) {
            System.out.println("Error: Ingreso una moneda No valida" );
        }
    }

}


