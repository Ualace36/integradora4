import org.apache.log4j.Logger;

import java.sql.*;

public class Main {
    static Logger log = Logger.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        Connection con = null;

        String createTable = "DROP TABLE IF EXISTS funcionarios; CREATE TABLE IF NOT EXISTS funcionarios(ID INT NOT NULL PRIMARY KEY, nome VARCHAR(300) NOT NULL, sobrenome VARCHAR(300) NOT NULL, cpf VARCHAR(20) NOT NULL UNIQUE, telefone VARCHAR(20));";
        String addFuncionarioSql = "INSERT INTO funcionarios VALUES(?,?,?,?,?)";
        String updateFuncionarioSql = "UPDATE funcionarios SET telefone='+55 (11) 90012-5480' WHERE ID='2';";
        String selectFuncionarioSql = "SELECT * FROM funcionarios WHERE ID='2';";
        String deleteFuncionarioSql = "DELETE FROM funcionarios WHERE ID='1';";
        String selectFuncionarioSql2 = "SELECT * FROM funcionarios WHERE ID='1';";
        String deleteFuncionarioSql2 = "DELETE FROM funcionarios WHERE cpf='987.658.412-49';";
        String selectFuncionarioSql3 = "SELECT * FROM funcionarios WHERE cpf='987.658.412-49';";
        String selectFuncionarioAllAql = "SELECT * FROM funcionarios";

        String url = "jdbc:h2:~/test";
        String user = "ualace";
        String pass = "";

        try{
            log.debug("Iniciando a conexão...");
            con = DriverManager.getConnection(url,user,pass);
            Statement statement = con.createStatement();
            statement.execute(createTable);
            statement.close();

            PreparedStatement addFuncionario = con.prepareStatement(addFuncionarioSql);
            addFuncionario.setInt(1,1);
            addFuncionario.setString(2,"Ualace");
            addFuncionario.setString(3,"Santos");
            addFuncionario.setString(4,"000.222.111-25");
            addFuncionario.setString(5,"+55 (73) 98888-7777");
            addFuncionario.executeUpdate();
            addFuncionario.setInt(1,2);
            addFuncionario.setString(2,"Arthur");
            addFuncionario.setString(3,"Ito");
            addFuncionario.setString(4,"735.857.854-45");
            addFuncionario.setString(5,"+55 (75) 97777-5484");
            addFuncionario.executeUpdate();
            addFuncionario.setInt(1,3);
            addFuncionario.setString(2,"O");
            addFuncionario.setString(3,"Maskara");
            addFuncionario.setString(4,"784.987.789-49");
            addFuncionario.setString(5,"+55 (61) 4574-6548");
            addFuncionario.executeUpdate();
            addFuncionario.setInt(1,4);

            try {
                addFuncionario.setString(2,"Uàlace");
                addFuncionario.setString(3,"Arthur");
                addFuncionario.setString(4,"784.987.789-78");
                addFuncionario.setString(5,"+55 (73) 95555-2788");
                addFuncionario.executeUpdate();
            }
            catch (SQLException e){
                log.error(e.getMessage());
                e.printStackTrace();
            }
            addFuncionario.close();

            Statement statement1 = con.createStatement();
            statement1.executeUpdate(updateFuncionarioSql);
            ResultSet resultSet = statement1.executeQuery(selectFuncionarioSql);
            while(resultSet.next()){
                log.debug("Funcionario: \n" +
                        "ID:       " + resultSet.getInt(1) + "\n" +
                        "Nome:     " + resultSet.getString(2) + " " + resultSet.getString(3) + "\n" +
                        "CPF:      " + resultSet.getString(4) + "\n" +
                        "Telefone: " + resultSet.getString(5));
            }
            resultSet.close();

            ResultSet resultSet1 = statement1.executeQuery(selectFuncionarioSql2);
            while (resultSet1.next()){
                log.debug("Funcionario: \n" +
                        "ID:       " + resultSet1.getInt(1) + "\n" +
                        "Nome:     " + resultSet1.getString(2) + " " + resultSet1.getString(3) + "\n" +
                        "CPF:      " + resultSet1.getString(4) + "\n" +
                        "Telefone: " + resultSet1.getString(5));
            }
            resultSet1.close();
            statement1.executeUpdate(deleteFuncionarioSql);

            ResultSet resultSet2 = statement1.executeQuery(selectFuncionarioSql3);
            while (resultSet2.next()){
                log.debug("Funcionario: \n" +
                        "ID:       " + resultSet2.getInt(1) + "\n" +
                        "Nome:     " + resultSet2.getString(2) + " " + resultSet2.getString(3) + "\n" +
                        "CPF:      " + resultSet2.getString(4) + "\n" +
                        "Telefone: " + resultSet2.getString(5));
            }
            resultSet2.close();
            statement1.executeUpdate(deleteFuncionarioSql2);

            ResultSet resultSet3 = statement1.executeQuery(selectFuncionarioAllAql);
            while (resultSet3.next()){
                log.debug("Funcionario: \n" +
                        "ID:       " + resultSet3.getInt(1) + "\n" +
                        "Nome:     " + resultSet3.getString(2) + " " + resultSet3.getString(3) + "\n" +
                        "CPF:      " + resultSet3.getString(4) + "\n" +
                        "Telefone: " + resultSet3.getString(5));
            }
            resultSet3.close();
            statement1.close();
        }
        catch(SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            log.debug("Fechando a conexão...");
            if(con != null){
                con.close();
            }
        }
    }
}
