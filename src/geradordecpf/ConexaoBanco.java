
package geradordecpf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoBanco {
    
    private static final String URL = "jdbc:mysql://localhost:3306/GeradorDeCpf";
    private static final String USUARIO = "root";
    private static final String SENHA = "admin";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    
    
}
