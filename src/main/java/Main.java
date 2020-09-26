import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        statement = connection.createStatement();
    }
    private static void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }
    private static void delete() throws SQLException {
        statement.executeUpdate("DELETE FROM testTable WHERE id>100;");
    }

//    НЕ МОГУ ПОНЯТЬ, ПОЧЕМУ НЕ РАБОТАЮТ МЕТОДЫ и как они должны быть связаны? allStatement() И  updateNewValue()

    private static void allStatement() throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE testTable SET id =? name=?");
    }
    private static void updateNewValue(int idNew, String string) throws SQLException {   // изменяет имя на string, если idNew > id
        ResultSet r = statement.executeQuery("SELECT id FROM testTable ;");
        while (r.next()) {
            if (idNew > r.getInt(1)) {
                preparedStatement.setString(2, string);
//                preparedStatement.executeUpdate();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        try {
            connect();
            delete();
            allStatement();
//            statement.executeUpdate("UPDATE testTable SET id=14 WHERE name == 'Morgan';");
            updateNewValue(5, "Bro");

           ResultSet rs =  statement.executeQuery("SELECT *\n" + "FROM testTable ;");
           while (rs.next()){
               System.out.println(rs.getString(1) + " " + rs.getString(2));
           }
           rs.close();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
          disconnect();
        }
    }
}
