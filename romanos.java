import java.util.Scanner;

public class romanos {

    public static int AlgarismoRomanoParaInteiro(String s) {
        int resultado = 0;

        for (int i = 0; i < s.length(); i++) {
            int atual = valorLetra(s.charAt(i));
            int proximo = (i + 1 < s.length()) ? valorLetra(s.charAt(i + 1)) : 0;

            if (atual < proximo) {
                resultado -= atual;
            } else {
                resultado += atual;
            }
        }

        return resultado;
    }

    private static int valorLetra(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default:  return 0;
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String algarismos = String.join("", args);
            int valor = AlgarismoRomanoParaInteiro(algarismos);
            System.out.println(valor);
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o algarismo: ");
        String algarismos = sc.nextLine();
        int valor = AlgarismoRomanoParaInteiro(algarismos);
        System.out.println(valor);
    }
}