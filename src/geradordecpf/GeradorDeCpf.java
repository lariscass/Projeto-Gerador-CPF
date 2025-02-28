
package geradordecpf;
//bibliotecas
import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class GeradorDeCpf {
    
    //método para salvas os cpfs no database
    public static void salvarCpfNoBanco(String cpf, int estado) {
    String sql = "INSERT INTO cpfs (cpf, estado) VALUES (?, ?)"; //comando do sql para inserir os valores na tabela cpfs

    try (Connection conn = ConexaoBanco.conectar();//abre a conexao com o banco de dados
        PreparedStatement stmt = conn.prepareStatement(sql)) { //prepara a execução do comando sql
        String siglaEstado = obterSiglaEstado(estado);//obtém a sigla correspondente ao estado selecionado
       

        stmt.setString(1, cpf);
        stmt.setString(2, siglaEstado);
        
        stmt.executeUpdate();//executa o comando sql
        System.out.println("\n✅ CPF salvo no banco!");

    } catch (SQLException e) {//executa exceção se houver
        e.printStackTrace();
    }
}
    
    public static String obterSiglaEstado(int numero) {
    String[] siglas = {"RS", "DF", "PA", "CE", "PE", "BA", "MG", "RJ", "SP", "PR"};
    if (numero >= 0 && numero < siglas.length) {// se estiver entre 0 e 9, sera retornado a sigla
        return siglas[numero];
    }
    return "XX"; // Valor padrão caso o número seja inválido
}

    
public static void gerarCpf(int oitavoNumero) {
    Random random = new Random();
    int numeros[] = new int[9]; 

    System.out.print("CPF gerado: ");
    for (int i = 0; i < 7; i++) {
        numeros[i] = random.nextInt(10); // gera os primeiros 7 dígitos aleatórios do CPF
      System.out.print(numeros[i]);
    }

    numeros[7] = oitavoNumero; //  seleciona o 7º índice e adiciona o número do estado
    System.out.print(numeros[7]);

    // Cálculo do primeiro dígito verificador
    int soma = 0;
    int peso = 10;
    for (int i = 0; i < 9; i++) { 
        soma += numeros[i] * peso; //a cada iteração soma vai atualizando de valor (soma = soma + numeros[i] * peso)
        peso--; //a cada iteração peso diminuí 
    }

    int primeiroDigito = (soma * 10) % 11;
    if (primeiroDigito == 10) { //se o primeiro dígito for 10, ele deve ser convertido para 0, conforme a regra do CPF
        primeiroDigito = 0;
    }

    System.out.print(primeiroDigito);// imprime primeiro dígito

    // Cálculo do segundo dígito verificador
    soma = 0;
    peso = 11;
    for (int i = 0; i < 9; i++) {
        soma += numeros[i] * peso; //a cada iteração soma vai atualizando de valor (soma = soma + numeros[i] * peso)
        peso--; //a cada iteração peso diminuí 
    }
    soma += primeiroDigito * 2; // Adiciona o primeiro dígito na soma

    int segundoDigito = (soma * 10) % 11;
    if (segundoDigito == 10) { //se o primeiro dígito for 10, ele deve ser convertido para 0, conforme a regra do CPF
        segundoDigito = 0;
    }

    System.out.print(segundoDigito); // imprime segundo dígito

    // Montando o CPF final como String
    String cpfGerado = "";
    for (int i = 0; i < 9; i++) {
        cpfGerado += numeros[i];
    }
    //concatena o primeiro e segundo dígito no cpfGerado
    cpfGerado += primeiroDigito;
    cpfGerado += segundoDigito;

    System.out.println("\nCPF Final: " + cpfGerado); //imprime cpf final

    salvarCpfNoBanco(cpfGerado, oitavoNumero); // chama o metódo que irá salvar no banco
}

    
    
        
        public static String selecionarEstado(int oitavoNumero){
            switch(oitavoNumero){
            case 0: return "Rio Grande Do Sul";        
            case 1: return "Distrito Federal, Goiás, Mato Grosso, Mato Grosso do Sul, Tocantins";
            case 2: return "Pará, Amazonas, Acre, Amapá, Rondônia, Roraima";
            case 3: return "Ceará, Maranhão, Piauí";
            case 4: return "Pernambuco, Rio Grande do Norte, Paraíba, Alagoas";
            case 5: return "Bahia, Sergipe";
            case 6: return "Minas Gerais";
            case 7: return "Rio de Janeiro, Espírito Santo";
            case 8: return "São Paulo";
            case 9: return "Paraná, Santa Catarina";
            default: return "Estado inválido"; 
            }
    
}
   
    
    public static void main(String[] args)  throws SQLException {
    
          String[] estados = {
            "0 - Rio Grande do Sul", "1 - Distrito Federal, Goiás, Mato Grosso, Mato Grosso do Sul, Tocantins",
            "2 - Pará, Amazonas, Acre, Amapá, Rondônia, Roraima", "3 - Ceará, Maranhão, Piauí",
            "4 - Pernambuco, Rio Grande do Norte, Paraíba, Alagoas", "5 - Bahia, Sergipe",
            "6 - Minas Gerais", "7 - Rio de Janeiro, Espírito Santo", "8 - São Paulo", "9 - Paraná, Santa Catarina"
        };
          //cria um menu para a escolha do estado
          String escolha = (String) JOptionPane.showInputDialog(null, "Escolha seu estado:",
                "Seleção de Estado", JOptionPane.QUESTION_MESSAGE, null, estados, estados[0]);
                
            if (escolha != null) {// verifica se foi selecionado um estado
            // Pegamos o primeiro caractere da escolha, que representa o número do estado
            int oitavoNumero = Character.getNumericValue(escolha.charAt(0)); //Extrai o primeiro caractere da escolha (o número do estado) e o converte para inteiro
            System.out.println("Estado selecionado foi: " + escolha );
           gerarCpf(oitavoNumero); 
          
        } else {
            System.out.println("Nenhum estado selecionado!");
        }  
    }
    }



 
       
        
      
    

        
   
    
    

