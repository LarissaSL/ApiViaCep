import excecoes.ErroFormatoCep;
import modelos.Endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner leitura = new Scanner(System.in);
        int escolha = 0;
        String cep = "";
        List<Endereco> listaDeEnderecos = new ArrayList<>();
        Endereco endereco;


        do {
            System.out.println("\n|    Buscador de Endereços    |");
            System.out.println(
                    """
                    Digite 1 - Para Buscar um Endereço
                    Digite 2 - Para Sair
                    Insira sua Escolha: """
            );
            escolha = leitura.nextInt();
            leitura.nextLine();

            switch (escolha){
                case 1:

                    try {
                        System.out.println("Digite o CEP para buscarmos o endereço: ");
                        cep = leitura.nextLine();
                        endereco = new Endereco(cep);
                        endereco = endereco.buscar();
                        listaDeEnderecos.add(endereco);

                    } catch (ErroFormatoCep error) {
                        System.out.println(error.getMessage());
                        break;
                    }
                    System.out.println("\nEndereço pesquisado: " + endereco.toString());

                    break;
                case 2:
                    System.out.println("\nEndereços pesquisados: ");
                    listaDeEnderecos.forEach(item-> System.out.println(item));
                    Endereco.gravarListaEmJSON(listaDeEnderecos);

                    System.out.println("\nObrigada por utilizar o Site, confira os CEP´s pesquisados no arquivo cep.json");
                    break;
                default:
                    System.out.println("Insira uma escolha válida, por favor\n");
            }
        } while (escolha != 2);

    }
}
