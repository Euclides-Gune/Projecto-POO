package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Validacao {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static String validarString(String msg) {
        String resposta = "";
        IO.print(msg);
        try {
            do {
                resposta = br.readLine();
                if(resposta.isEmpty() || soEspacos(resposta)) {
                    IO.print("Entrada inválida, não pode estar vazia!\n Introduza novamente: ");
                }
            } while(resposta.isEmpty() || soEspacos(resposta));
        } catch(IOException e) {
            IO.println(e.getMessage());
        }
        return resposta;
    }

    public static String validarSenha(String msg, int length) {
        IO.print(msg);
        String senha = " ";

        try {
            do {
                senha = br.readLine();
                if(senha.isEmpty() || senha.length() < length || soEspacos(senha))
                    IO.print("\nSenha inválida,  deve ter tamanho maior que "+length+"!\nDigite novamente: \n");
            } while(senha.isEmpty() || senha.length() < length || soEspacos(senha));
        } catch(IOException e) {
            IO.println(e.getMessage());
        }

        return senha;
    }

    public static int validarInt(String msg, int min, int max) {
        int resposta = 0;
        IO.print(msg);
        try {
            do {
                resposta = Integer.parseInt(br.readLine());
                if(resposta < min || resposta > max) {
                    IO.print("O valor introduzido deve estar entre "+min+" e "+max+"!\nDigite novamente: ");
                }
            } while(resposta < min || resposta > max);
        } catch(IOException e) {
            IO.println(e.getMessage());
        } catch(NumberFormatException e) {
            IO.println("A entrada deve ser um valor numérico!");
        }
        return resposta;
    }

    public static double validarDouble(String msg, double min, double max) {
        double resposta = 0;
        IO.print(msg);
        try {
            do {
                resposta = Double.parseDouble(br.readLine());
                if(resposta < min || resposta > max) {
                    IO.print("O valor introduzido deve estar entre "+min+" e "+max+"!\nDigite novamente: ");
                }
            } while(resposta < min || resposta > max);
        } catch(IOException e) {
            IO.println(e.getMessage());
        } catch(NumberFormatException e) {
            IO.println("A entrada deve ser um valor numérico!");
        }
        return resposta;
    }

    public static boolean soEspacos(String str) {
        int l = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ' ') {
                l++;
            }
        }
        return l == str.length();
    }

}
