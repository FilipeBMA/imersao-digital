import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        GerenciadorEventos gerenciador = new GerenciadorEventos();

        System.out.println("=== Cadastro de usuário ===");
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine().trim();
        Usuario usuario = new Usuario(nome, email, telefone);
        System.out.println("Usuário cadastrado: " + usuario);

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarEvento(gerenciador);
                case 2 -> listarEventos(gerenciador);
                case 3 -> participarEvento(gerenciador);
                case 4 -> cancelarParticipacao(gerenciador);
                case 5 -> listarConfirmados(gerenciador);
                case 0 -> System.out.println("Encerrando programa...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1 - Cadastrar evento");
        System.out.println("2 - Listar eventos");
        System.out.println("3 - Participar de evento");
        System.out.println("4 - Cancelar participação");
        System.out.println("5 - Meus eventos confirmados");
        System.out.println("0 - Sair");
    }

    private static void cadastrarEvento(GerenciadorEventos gerenciador) {
        System.out.print("Nome do evento: ");
        String enome = sc.nextLine().trim();

        System.out.print("Endereço: ");
        String eendereco = sc.nextLine().trim();

        System.out.println("Categorias disponíveis: " + Categoria.opcoes());
        System.out.print("Categoria: ");
        String ecategoria = sc.nextLine().trim();
        Categoria categoria = Categoria.from(ecategoria);

        LocalDateTime horario = null;
        while (horario == null) {
            System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
            String data = sc.nextLine().trim();
            try {
                horario = LocalDateTime.parse(data, FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Tente novamente.");
            }
        }

        System.out.print("Descrição: ");
        String edesc = sc.nextLine().trim();

        gerenciador.cadastrarEvento(new Evento(enome, eendereco, categoria, horario, edesc));
        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listarEventos(GerenciadorEventos gerenciador) {
        List<Evento> eventos = gerenciador.listarEventos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            for (int i = 0; i < eventos.size(); i++) {
                System.out.println(i + " - " + eventos.get(i));
            }
        }
    }

    private static void participarEvento(GerenciadorEventos gerenciador) {
        listarEventos(gerenciador);
        int indice = lerInt("Digite o índice do evento para participar: ");
        if (gerenciador.participarEvento(indice)) {
            System.out.println("Confirmação realizada!");
        } else {
            System.out.println("Índice inválido ou já confirmado.");
        }
    }

    private static void cancelarParticipacao(GerenciadorEventos gerenciador) {
        List<Evento> confirmados = gerenciador.listarConfirmados();
        if (confirmados.isEmpty()) {
            System.out.println("Nenhum evento confirmado.");
            return;
        }
        for (int i = 0; i < confirmados.size(); i++) {
            System.out.println(i + " - " + confirmados.get(i));
        }
        int indice = lerInt("Digite o índice para cancelar: ");
        if (gerenciador.cancelarEvento(indice)) {
            System.out.println("Participação cancelada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void listarConfirmados(GerenciadorEventos gerenciador) {
        List<Evento> confirmados = gerenciador.listarConfirmados();
        if (confirmados.isEmpty()) {
            System.out.println("Você não confirmou presença em nenhum evento.");
        } else {
            System.out.println("Eventos confirmados:");
            for (Evento e : confirmados) {
                System.out.println(e);
            }
        }
    }

    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // consumir quebra de linha
        return valor;
    }
}
