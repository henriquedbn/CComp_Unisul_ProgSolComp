/** Classe que implementa os eventos, definindo seus atributos e metódos.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

class Evento {
    private static final String arquivoTxt = "events.data";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static boolean dataFormatoValido;
    private static Formatter output;
    private String nome;
    private String endereco;
    private String categoria;
    public LocalDateTime dataEvento;
    private String descricao;

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Endereço: " + endereco + "\n" +
                "Categoria: " + categoria + "\n" +
                "Data e Hora: " + dataEvento.format(DATE_TIME_FORMATTER) + "\n" +
                "Descrição: " + descricao + "\n";
    }

    public Evento(String nome, String endereco, String categoria, LocalDateTime dataHoraEvento, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.dataEvento = dataHoraEvento;
        this.descricao = descricao;

    }


    public static Evento cadastro() {
        //método para cadastro de novos eventos,
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o nome do evento:");
        String nome = input.nextLine();

        System.out.println("Informe o endereço do evento:");
        String endereco = input.nextLine();

        System.out.println("Informe a categoria do evento:");
        String categoria = input.nextLine();

        LocalDateTime horario = null;
        while (!dataFormatoValido) {
            System.out.println("Informe o horário do evento (dd-MM-yyyy HH:mm):");
            try {
                String inputHorario = input.nextLine();
                horario = LocalDateTime.parse(inputHorario, DATE_TIME_FORMATTER);
                dataFormatoValido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de horário inválido. Deseja tentar novamente? (S/N)");
                String tentarNovamente = input.nextLine();
                if (tentarNovamente.equalsIgnoreCase("N")) {
                    return null;
                }
            }

        }

        System.out.println("Informe a descrição do evento:");
        String descricao = input.nextLine();
        abrirLista();
        assert horario != null;
        output.format("%s;%s;%s;%s;%s\n", nome, endereco, categoria, horario.format(DATE_TIME_FORMATTER), descricao);
        output.close();
        System.out.println("Evento cadastrado com sucesso! \n");
        return new Evento(nome,endereco,categoria,horario,descricao);
    }
    public static void abrirLista(){
        try{
            output = new Formatter(new FileOutputStream(arquivoTxt, true));
        }
        catch(SecurityException securityException){
            System.err.println("Permissão de escrita negada.S");
        }
        catch(FileNotFoundException fileNotFoundException){
            System.err.println("Arquivo não encontrado.");
        }
    }

    public static Scanner abrirEventos(){
        try{
            return new Scanner(Paths.get("events.data"));
        }
        catch(IOException ioException){
            System.err.println("Erro ao abrir o arquivo.");
            return null;
        }
    }

    public static List<Evento> listarEventos(){
        List<Evento> todosEventos = new ArrayList<>();
        Scanner lista = abrirEventos();
        if(lista==null){
            System.err.println("Erro ao acessar o arquivo com dados dos eventos.");
            return null;
        }
        while(lista.hasNext()) {
            String linhaDoArquivo = lista.nextLine();
            String[] parts = linhaDoArquivo.split(";");
            if (parts.length == 5) { // temos 5 categorias para cada evento
                String nome = parts[0];
                String endereco = parts[1];
                String categoria = parts[2];
                LocalDateTime eventDateTime = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                String descricao = parts[4];

                // Create an Evento object and add it to the list
                Evento evento = new Evento(nome, endereco, categoria, eventDateTime, descricao);
                todosEventos.add(evento);

                // Print details about the event
                System.out.println("Nome: " + nome);
                System.out.println("Local: " + endereco);
                System.out.println("Categoria: " + categoria);
                System.out.println("Data e Hora: " + eventDateTime.format(DATE_TIME_FORMATTER));
                if (eventDateTime.isBefore(LocalDateTime.now())) System.out.println("O evento já ocorreu");
                else {
                    long intervaloTemporal = DAYS.between(LocalDateTime.now(), eventDateTime);
                    if (intervaloTemporal > 0)
                        System.out.println("O evento ocorrerá em " + intervaloTemporal + " dias.");
                    else
                        System.out.println("O evento começará em " + HOURS.between(LocalDateTime.now(), eventDateTime) + " horas. ");
                }
                System.out.println("Descrição: " + descricao);
                System.out.println(); // For spacing between events
            }
        }
        lista.close();
        return todosEventos;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }
}


