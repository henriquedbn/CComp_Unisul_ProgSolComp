import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Usuarios {
    private static final String dadosUsuarios = "users.data";
    private static Formatter output;
    private String nome;
    private int idade;
    private String documento;
    private String cidade;
    private List<Evento> eventosVaiParticipar;

    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Idade: " + idade + "\n" +
                "Documento: " + documento + "\n" +
                "Cidade: " + cidade + "\n\n";
    }

    // Construtor
    public Usuarios(String nome, int idade, String documento, String cidade) {
        this.nome = nome;
        this.idade = idade;
        this.documento = documento;
        this.cidade = cidade;
        this.eventosVaiParticipar = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Evento> getEventosVaiParticipar() {
        return eventosVaiParticipar;
    }

    // Methods to manage events user will participate
    public void adicionarEvento(Evento evento) {
        eventosVaiParticipar.add(evento);
    }

    public void removerEvento(Evento evento) {
        eventosVaiParticipar.remove(evento);
    }

    public void listarEventosParticipacao() {
        for (Evento evento : eventosVaiParticipar) {
            System.out.println(evento);
        }
    }

    public static Usuarios cadastroUsuario() {
        //Método para cadastro de novos usuários,
        Scanner input = new Scanner(System.in);
        System.out.println("Cadastro de novo usuário:");
        System.out.println("Informe o nome do usuário:");
        String nome = input.nextLine();
        int idadeObtida = 99;
        boolean idadeFormatoValido = false;
        while (!idadeFormatoValido) {
            System.out.println("Informe a idade do usuário:");
            try {
                idadeObtida = Integer.parseInt(input.nextLine());
                idadeFormatoValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida! Por favor, insira um número inteiro.");
                String tentarNovamente = input.nextLine();
                if (tentarNovamente.equalsIgnoreCase("N")) {
                    return null;
                }
            }
        }

        System.out.println("Informe o documento do usuário:");
        String documento = input.nextLine();
        System.out.println("Informe a cidade do usuário:");
        String cidade = input.nextLine();

        abrirLista();
        output.format("%s;%d;%s;%s\n", nome, idadeObtida, documento, cidade);
        output.close();
        System.out.println("Usuário cadastrado com sucesso! \n");
        return new Usuarios(nome, idadeObtida, documento, cidade);
    }

    public static void abrirLista(){
        try{
            output = new Formatter(new FileOutputStream(dadosUsuarios, true));
        }
        catch(SecurityException securityException){
            System.err.println("Permissão de escrita negada.S");
        }
        catch(FileNotFoundException fileNotFoundException){
            System.err.println("Arquivo não encontrado.");
        }
    }

    public static Scanner abrirUsuarios(){
        try{
            return new Scanner(Paths.get("users.data"));
        }
        catch(IOException ioException){
            System.err.println("Erro ao abrir o arquivo.");
            return null;
        }
    }

    public static List<Usuarios> obterUsuarios() {
        List<Usuarios> todosUsuarios = new ArrayList<>();
        Scanner lista = abrirUsuarios();
        if(lista==null){
            System.err.println("Erro ao acessar o arquivo com dados dos usuários.");
            return null;
        }
        while(lista.hasNext()) {
            String linhaDoArquivo = lista.nextLine();
            String[] parts = linhaDoArquivo.split(";");
            if (parts.length == 4) { // temos 4 atributos para cada usuário
                String nome = parts[0];
                int idade = Integer.parseInt(parts[1]);
                String documento = parts[2];
                String cidade = parts[3];

                // Cria um objeto Evento e adiciona à lista
                Usuarios usuario = new Usuarios(nome, idade, documento, cidade);
                todosUsuarios.add(usuario);
            }
            else{
                System.err.println("Erro ao segmentar os dados de usuários.");
                return null;
            }
        }
        lista.close();
        return todosUsuarios;
    }
}