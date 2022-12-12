package barros.maria.app_manual_do_calouro.model;

/**
 * Classe para reprensetar um horário
 */
public class Horario {
    public Integer id_dia_semana;   // id do dia da semana
    public Integer id_horario_aula; // id do horario da aula
    public Integer id_turma;        // id da turma
    public String grupo;            // dsc do grupo
    public String hora_aula_inicio; // hora de inicio de uma aula
    public String hora_aula_fim;    // hora de término de uma aula
    public String sala;             // dsc da sala de uma aula
    public String materia;          // dsc da disciplina
    public String professor;        // dsc do professor que dá a aula
}