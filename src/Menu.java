import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private static int escolha = 0;
    private static double valor = 0.0;

    private static int selecionarEscolhaMenu() {
        try {
            escolha = new Scanner(System.in).nextInt();
            return escolha;
        } catch (InputMismatchException e) {
            return 0;
        }
    }

    private static boolean escolherValor() {
        System.out.println("Digite um valor que deseja converter:");

        try {
            valor = new Scanner(System.in).nextDouble();
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Digite um valor válido.");
            return false;
        }
    }

    public static boolean menuIniciar() {
        System.out.print(
            """
            Escolha uma opção:
            1 - Conventer moedas
            2 - Sair
            
            Digite um número para prosseguir:
            """
        );

        return switch (selecionarEscolhaMenu()) {
            case 1 -> {
                while(menuConversorOrigem());
                yield true;
            }
            case 2 -> false;
            default -> {
                System.out.println("Digite uma opção válida.");
                yield true;
            }
        };
    }

    private static void printSelecionarMoeda() {
        System.out.print(
                """
                Escolha uma opção:
                1 - Peso argentino      (ARS)
                2 - Boliviano boliviano (BOB)
                3 - Real brasileiro     (BRL)
                4 - Peso chileno        (CLP)
                5 - Peso colombiano     (COP)
                6 - Dólar americano     (USD)
                """
        );
    }

    private static boolean menuConversorOrigem() {
        if(!escolherValor()) return true;

        printSelecionarMoeda();
        System.out.print(
            """
            7 - Voltar
            
            Digite um número para prosseguir:
            """
        );

        return switch (selecionarEscolhaMenu()) {
            case 1 -> {
                while(menuConversorDestino("ARS"));
                yield false;
            }
            case 2 -> {
                while(menuConversorDestino("BOB"));
                yield false;
            }
            case 3 -> {
                while(menuConversorDestino("BRL"));
                yield false;
            }
            case 4 -> {
                while(menuConversorDestino("CLP"));
                yield false;
            }
            case 5 -> {
                while(menuConversorDestino("COP"));
                yield false;
            }
            case 6 -> {
                while(menuConversorDestino("USD"));
                yield false;
            }
            case 7 -> false;
            default -> {
                System.out.println("Digite uma opção válida.");
                yield true;
            }
        };
    }

    private static boolean menuConversorDestino(String origem) {
        ApiRequest apiRequest = new ApiRequest();
        String destino;

        printSelecionarMoeda();
        System.out.println("Digite um número para prosseguir:");

        switch (selecionarEscolhaMenu()) {
            case 1:
                destino = "ARS";
                break;
            case 2:
                destino = "BOB";
                break;
            case 3:
                destino = "BRL";
                break;
            case 4:
                destino = "CLP";
                break;
            case 5:
                destino = "COP";
                break;
            case 6:
                destino = "USD";
                break;
            default:
                System.out.println("Digite uma opção válida.");
                return true;
        };

        Optional<Double> moedaEquivalenteUnidade = apiRequest.obterMoedaEquivalente(origem, destino);

        if(moedaEquivalenteUnidade.isEmpty()) {
            System.out.println("Resultado da busca inválido!");
            return true;
        }

        double moedaDestinoEquivalente = valor * moedaEquivalenteUnidade.get();

        System.out.printf(
            """
            Resultado da conversão:
            %s = %.2f (Valor na moeda de entrada)
            %s = %.2f (Valor de saída equivalente)
            """,
            origem,
            valor,
            destino,
            moedaDestinoEquivalente
        );

        return false;
    }
}
