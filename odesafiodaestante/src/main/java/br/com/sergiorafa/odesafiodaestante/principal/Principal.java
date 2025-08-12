package br.com.sergiorafa.odesafiodaestante.principal;

import br.com.sergiorafa.odesafiodaestante.dto.DadosBusca;
import br.com.sergiorafa.odesafiodaestante.dto.LivroDTO;
import br.com.sergiorafa.odesafiodaestante.model.Autor;
import br.com.sergiorafa.odesafiodaestante.model.Livro;
import br.com.sergiorafa.odesafiodaestante.repository.AutorRepository;
import br.com.sergiorafa.odesafiodaestante.repository.LivroRepository;
import br.com.sergiorafa.odesafiodaestante.service.ConsumoApiGutendex;
import br.com.sergiorafa.odesafiodaestante.service.ConverteDados;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    private final String ENDERECO = "https://gutendex.com/";
    private final Scanner leitura = new Scanner(System.in);

    private final ConsumoApiGutendex consumoApi;
    private final ConverteDados conversor;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(ConsumoApiGutendex consumoApi, ConverteDados conversor,
                     LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumoApi = consumoApi;
        this.conversor = conversor;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                \n*****Bem Vindo ao Melhor site de Livros do Momento*****
                1 - Nome desejado do Livro
                2 - Listar livros salvos na Estante
                3 - Listar autores dos livros
                4 - Lista autores em um determinado ano
                5 - Listar livros em determinado idioma
          
                  0 - Sair
                ---------------------------------------------------
                """;
            System.out.println(menu);
            System.out.print("Escolha uma opção: ");

            while (!leitura.hasNextInt()) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                System.out.print("Escolha uma opção: ");
                leitura.next();
            }
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    nomeDesejadoDoLivro();
                    break;
                case 2:
                    listarLivrosSalvosNaEstante();
                    break;
                case 3:
                    listarAutoresDosLivros();
                    break;
                case 4:
                    listarAutoresEmUmDeterminadoAno();
                    break;
                case 5:
                    listarLivrosEmDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo da aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
  //Método nome dos livros
  private void nomeDesejadoDoLivro() {
      System.out.print("Digite o nome do livro: ");
      var nomeLivro = leitura.nextLine();
      var url = ENDERECO + "books/?search=" + nomeLivro.replace(" ", "%20");

      var json = consumoApi.obterDados(url);
      var dadosBusca = conversor.obterDados(json, DadosBusca.class);

      if (!dadosBusca.resultados().isEmpty()) {
          LivroDTO livroDTO = dadosBusca.resultados().get(0);

          System.out.println("Livro encontrado:");
          System.out.println("Título: " + livroDTO.titulo());

          // --- Adição para mostrar o idioma ---
          System.out.print("Idiomas: ");
          if (livroDTO.idiomas() != null && !livroDTO.idiomas().isEmpty()) {
              System.out.println(String.join(", ", livroDTO.idiomas()));
          } else {
              System.out.println("N/A");
          }

          System.out.print("Deseja salvar este livro na sua estante? (S/N): ");
          String resposta = leitura.nextLine().toUpperCase();

          if (resposta.equals("S")) {
              Livro livro = new Livro(livroDTO);
              livroRepository.save(livro);
              System.out.println("Livro '" + livro.getTitulo() + "' salvo com sucesso!");
          }
      } else {
          System.out.println("Nenhum livro encontrado com o nome '" + nomeLivro + "'.");
      }
  }
   // Método para listar os livros salvos na Estante.
    private void listarLivrosSalvosNaEstante() {
        List<Livro> livros = livroRepository.findAll();

        if (!livros.isEmpty()) {
            System.out.println("\n----------livros salvos----------");
            livros.stream()
                    .sorted(Comparator.comparing(Livro::getTitulo))
                    .forEach(System.out::println);
            System.out.println("---------------------");
        } else {
            System.out.println("Nenhum livro salvo no banco de dados ainda.");
        }
    }
   // Método para listar autores dos livros
    private void listarAutoresDosLivros() {
        System.out.println("Qual o nome do Autor para busca?");
        var nomeAutor = leitura.nextLine();
        List<Autor> autoresEncontrados = autorRepository.findByNomeContainingIgnoreCase(nomeAutor);

        if (!autoresEncontrados.isEmpty()) {
            System.out.println("\n--- Autores encontrados para '" + nomeAutor + "' ---");
            autoresEncontrados.stream()
                    .sorted(Comparator.comparing(Autor::getNome))
                    .forEach(a -> {
                        System.out.println("Nome: " + a.getNome());
                        System.out.println("Ano de Nascimento: " + a.getAnoNascimento());
                        System.out.println("Ano de Morte: " + a.getAnoMorte());

                        if (a.getLivro() != null) {
                            System.out.println("Livro: " + a.getLivro().getTitulo());
                        }

                        System.out.println("--------------------");
                    });
        } else {
            System.out.println("\n--- Nenhum autor encontrado para '" + nomeAutor + "'. ---");
            System.out.println("Verifique a ortografia ou tente um nome de autor diferente.");
        }
    }
    // Método para listar autores em um determinado ano.
    private void listarAutoresEmUmDeterminadoAno() {
        System.out.println("Como você deseja buscar um autor em um determinado ano?");
        System.out.println("1 - Exatamente em um ano");
        System.out.println("2 - A partir de um ano");
        System.out.println("3 - Em um intervalo de anos");
        System.out.print("Escolha uma opção: ");

        int subOpcao = 0;
        try {
            subOpcao = Integer.parseInt(leitura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Digite 1, 2 ou 3.");
            return;
        }

        List<Autor> autoresEncontrados = new ArrayList<>();

        switch (subOpcao) {
            case 1:
                int anoExato = getIntInput("Qual ano você deseja buscar?");
                autoresEncontrados = autorRepository.findByAnoNascimento(anoExato);
                break;
            case 2:
                int anoMinimo = getIntInput("A partir de qual ano você deseja buscar?");
                autoresEncontrados = autorRepository.findByAnoNascimentoGreaterThanEqual(anoMinimo);
                break;
            case 3:
                int anoInicio = getIntInput("Qual o ano de início do intervalo?");
                int anoFim = getIntInput("Qual o ano de fim do intervalo?");
                autoresEncontrados = autorRepository.findByAnoNascimentoBetween(anoInicio, anoFim);
                break;
            default:
                System.out.println("Opção inválida. Retornando ao menu principal.");
                return;
        }

        if (autoresEncontrados.isEmpty()) {
            System.out.println("Nenhum autor encontrado com os critérios informados.");
        } else {
            System.out.println("\n--- Autores Encontrados ---");
            autoresEncontrados.forEach(autor -> {
                System.out.printf("Nome: %s (Ano de Nascimento: %d, Ano de Morte: %d)\n",
                        autor.getNome(), autor.getAnoNascimento(), autor.getAnoMorte());
                if (autor.getLivro() != null) {
                    System.out.printf("  - Livro: %s\n", autor.getLivro().getTitulo());
                }
            });
            System.out.println("--------------------------");
        }
    }
  // Método para listar livro em 4 idioma (Portuquês, Inglês, Espanhol e Francês).
    private void listarLivrosEmDeterminadoIdioma() {
        System.out.println("Qual idioma você deseja ver os livros?");
        System.out.println("1 - Português (pt)");
        System.out.println("2 - Inglês (en)");
        System.out.println("3 - Espanhol (es)");
        System.out.println("4 - Francês (fr)");
        System.out.print("Escolha uma opção: ");

        int subOpcao;
        try {
            subOpcao = Integer.parseInt(leitura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Digite 1, 2, 3 ou 4.");
            return;
        }

        String idioma = "";
        switch (subOpcao) {
            case 1:
                idioma = "pt";
                break;
            case 2:
                idioma = "en";
                break;
            case 3:
                idioma = "es";
                break;
            case 4:
                idioma = "fr";
                break;
            default:
                System.out.println("Opção inválida. Retornando ao menu principal.");
                return;
        }

        List<Livro> livrosEncontrados = livroRepository.findByIdiomasContainingIgnoreCase(idioma);

        if (!livrosEncontrados.isEmpty()) {
            System.out.println("\n---------- Livros em " + idioma + " ----------");
            livrosEncontrados.stream()
                    .sorted(Comparator.comparing(Livro::getTitulo))
                    .forEach(livro -> System.out.println(" - " + livro.getTitulo()));
            System.out.println("---------------------");
        } else {
            System.out.println("Nenhum livro encontrado para o idioma '" + idioma + "'.");
        }
    }

    // Método auxiliar para obter um inteiro
    private int getIntInput(String mensagem) {
        while (true) {
            System.out.print(mensagem + " ");
            try {
                return Integer.parseInt(leitura.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
            }
        }
    }
}