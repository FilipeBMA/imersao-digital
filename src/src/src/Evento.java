import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String nome;
    private String endereco;
    private Categoria categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, Categoria categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public Categoria getCategoria() { return categoria; }
    public LocalDateTime getHorario() { return horario; }
    public String getDescricao() { return descricao; }

    // Considerando duração "padrão" de 2 horas para status
    public String statusEvento() {
        LocalDateTime agora = LocalDateTime.now();
        if (horario.isBefore(agora.minusHours(2))) return "Já ocorreu";
        if (horario.isBefore(agora) && horario.plusHours(2).isAfter(agora)) return "Em andamento";
        return "Próximo";
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return nome + " | " + categoria + " | " + horario.format(fmt) + " | " + endereco + " | " + descricao
                + " [" + statusEvento() + "]";
    }
}
