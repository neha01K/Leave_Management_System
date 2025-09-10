import com.sun.security.jgss.GSSUtil;

import java.sql.*;
import java.util.Scanner;

//----------comments added in this program are for my own understanding-----------

public class Main{

    //to build connection, we need 3 things - url, username, password(these are attributes of MySQL Database)
    private static final String url = "jdbc:mysql://localhost:3306/transactions";
    private static final String username = "root";
    private static final String password = "Hitmanbau1*@sql";

    public static void main(String[] args) {

        //loading driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }

        //creating connection and Statement(help in sql query)
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
           // Statement statement = connection.createStatement();

            /*String query = "select * from students";
            //to hold the table and to retrieve the data(executeQuery)
            //to insert, update, delete (executeUpdate)
            ResultSet resultSet = statement.executeQuery(query);

            //looping on the table rows
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name  = resultSet.getString("name");
                int age  = resultSet.getInt("age");
                int marks = resultSet.getInt("marks");

                System.out.println("ID: "+ id);
                System.out.println("Name: "+ name);
                System.out.println("Age: "+ age);
                System.out.println("Marks: "+ marks);
            }*/

            //----------------INSERTING VALUE-------------------

            /*String query = String.format("INSERT INTO STUDENTS(id, name, age, marks) VALUES(%d,'%s', %d, %d)", 2, "Kunal", 24, 97);
            int rowsAffected = statement.executeUpdate(query);

            if(rowsAffected>0){
                System.out.println("Data inserted successfully!");
            }
            else{
                System.out.println("Data not inserted!");
            }*/

            //-----------------UPDATING VALUE------------------

            /*String query = String.format("UPDATE students SET marks = %d WHERE id = %d", 91, 1);
            int rowsAffected = statement.executeUpdate(query);

            if(rowsAffected>0){
                System.out.println("Data updated successfully!");
            }
            else{
                System.out.println("Data not updated!");
            }*/

            //-----------------DELETING VALUE------------------
           /* String query = String.format("DELETE FROM students WHERE id = 2");
            int rowsAffected = statement.executeUpdate(query);

            if(rowsAffected>0){
                System.out.println("Data deleted successfully!");
            }
            else{
                System.out.println("Data not deleted!");
            }*/


            //------------------PREPARED STATEMENT--------------------

            /*String query ="INSERT INTO STUDENTS(name, age, marks) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "Kunal");
            preparedStatement.setInt(2, 24);
            preparedStatement.setInt(3, 98);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected>0){
                System.out.println("Data inserted successfully!");
            }
            else{
                System.out.println("Data not inserted!");
            }*/


            //------------------PREPARED STATEMENT(Retrieving Data)--------------------

            /*String query ="SELECT marks FROM students WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,1);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int marks = resultSet.getInt("marks");
                System.out.println("Marks: "+ marks);
            }
            else{
                System.out.println("Marks not found");
            }*/


            //------------------PREPARED STATEMENT(updating Data)--------------------

            /*String query ="UPDATE students SET marks = ? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,89);
            preparedStatement.setInt(2, 3);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected>0){
                System.out.println("Data updated successfully!");
            }
            else{
                System.out.println("Data not updated!");
            }*/



            //------------------PREPARED STATEMENT(deleting Data)--------------------

            /*String query ="DELETE FROM students WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, 3);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected>0){
                System.out.println("Data deleted successfully!");
            }
            else{
                System.out.println("Data not deleted!");
            }*/

            //-----------------------BATCH PROCESSING  (using Statement)----------------------

            /*Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.print("Enter name: ");
                String name = scanner.next();
                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                System.out.print("Enter marks: ");
                int marks = scanner.nextInt();

                System.out.println("Enter more data(Y/N): ");
                String choice = scanner.next();

                String query = String.format("INSERT INTO STUDENTS(name, age, marks) VALUES('%s', %d, %d)", name,age,marks);
                statement.addBatch(query);

                if(choice.toUpperCase().equals("N")){
                    break;
                }
            }

            int[] arrayOfAffectedRows = statement.executeBatch();

            for(int i=0; i<arrayOfAffectedRows.length; i++){
                if(arrayOfAffectedRows[i]==0){
                    System.out.println("Query "+ (i+1) + " NOT executed");
                }
            }
*/


            //-----------------------BATCH PROCESSING (using PreparedStatement)----------------------

            /*String query = "INSERT INTO STUDENTS(name, age, marks) VALUES(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.print("Enter name: ");
                String name = scanner.next();
                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                System.out.print("Enter marks: ");
                int marks = scanner.nextInt();

                System.out.print("Enter more data(Y/N): ");
                String choice = scanner.next();

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setInt(3, marks);


                preparedStatement.addBatch();

                if(choice.toUpperCase().equals("N")){
                    break;
                }
            }

            int[] arrayOfAffectedRows = preparedStatement.executeBatch();

            for(int i=0; i<arrayOfAffectedRows.length; i++){
                if(arrayOfAffectedRows[i]==0){
                    System.out.println("Query "+ (i+1) + " NOT executed");
                }
            }*/


            //---------------------TRANSACTION HANDLING----------------------

            connection.setAutoCommit(false);

            String debitQuery = "UPDATE kahta SET balance = balance - ? WHERE account_number = ?";
            String creditQuery = "UPDATE kahta SET balance = balance + ? WHERE account_number = ?";

            PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
            PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();

            System.out.print("Enter sender Account Number: ");
            int debitAccountNUmber = scanner.nextInt();

            System.out.print("Enter receiver Account Number: ");
            int creditAccountNumber = scanner.nextInt();

            debitPreparedStatement.setDouble(1, amount);
            debitPreparedStatement.setInt(2, debitAccountNUmber);
            creditPreparedStatement.setDouble(1, amount);
            creditPreparedStatement.setInt(2, creditAccountNumber);

            debitPreparedStatement.executeUpdate();
            creditPreparedStatement.executeUpdate();

            if(isSufficient(connection, debitAccountNUmber, amount)){
                connection.commit();
                System.out.println("Transaction Successful!");
            }
            else{
                connection.rollback();
                System.out.println("Transaction Failed: Insufficient Balance");
            }

            debitPreparedStatement.close();
            scanner.close();
            creditPreparedStatement.close();
            connection.close();
        }


        catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }

    static boolean isSufficient(Connection connection, int accountNumber, double amount){
        try{
            String query = "SELECT BALANCE FROM kahta WHERE ACCOUNT_NUMBER = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                double currentBalance = resultSet.getDouble("balance");
                if(amount>currentBalance) {
                    return false;
                }
                else{
                    return true;
                }

            }

            resultSet.close();
            preparedStatement.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}