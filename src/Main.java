/** Atividade da unidade "Pratique" -
 * Atributos necessários:
 * -> Eventos: nome, endereço, categoria (festas, eventos esportivos, shows, entre outros exemplos), horário e descrição;
 * -> Usuários: nome, documento, cidade.
 * Funcionalidades necessárias:
 *  -> Prover um espaço para cadastro do usuário;
 * -> Consultar os eventos cadastrados e decidir participar de qualquer um que esteja listado;
 * -> Cadastrar eventos, definindo os atributos;
 * -> Através do horário, o programa deve ordenar os eventos mais próximos e informar se um evento está ocorrendo no momento (é desejável utilizar a estrutura DateTime para o controle de horários);
 * -> Informar os eventos que já ocorreram;
 * -> informações dos eventos devem ser salvas em um arquivo de texto chamado events.data;
 * -> Toda vez que o programa for aberto, deve carregar os eventos a partir da leitura deste arquivo.
 * */

import java.util.*;


public class Main {
    public static void main(String[] args) {
        int opcaoMenu;
        List<Evento> eventos = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.printf("O que você deseja fazer?%n" +
                    "\t1- Cadastrar novo usuário:%n" +
                    "\t2- Listar usuários %n"+
                    "\t3- Cadastrar novo evento%n" +
                    "\t4- Abrir arquivo com os dados dos eventos%n" +
                    "\t5- Listar eventos em ordem cronológica%n"+
                    "\t0 - Sair do programa%n");

            try {
                opcaoMenu = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida! Por favor, insira um número inteiro.");
                continue; // Continue loop to prompt for input again
            }

            switch(opcaoMenu) {
                case 0:
                    System.out.printf("Saindo do programa!%n%n");
                    return;
                case 1:
                    //implementar o cadastro de alunos;
                    break;
                case 2:
                    //implementar a listagem de todos os alunos para consulta;
                    break;
                case 3:
                    Evento novoEvento = Evento.cadastro();
                    break;
                case 4:
                    eventos = Evento.listarEventos();
                    break;
                case 5:
                    if(eventos != null) {
                        Collections.sort(eventos, new Comparator<Evento>() {
                            @Override
                            public int compare(Evento o1, Evento o2) {
                                return o1.getDataEvento().compareTo(o2.getDataEvento());
                            }
                        });
                    }
                    System.out.print("Lista de eventos em ordem: " + eventos);
                    break;
                default:
                    System.out.printf("Opção não encontrada! %n");
                    break;
            }
        }
    }
}