package com.mycompany.myproject.Interview_Questions;

import java.sql.*;

public class JDBCConnector {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String MYSQL_URL = "jdbc:mysql://localhost:3306/user_db";
    static final String USER_NAME = "root";
    static final String PASSWORD = "Password@123";

    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;


        try{

            Class.forName(JDBC_DRIVER);

            String query = "select * from user_db.user";

            conn = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);

             st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);

            while(rs.next()){

                String username = rs.getString("username");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String password = rs.getString("password");

                System.out.printf("Username: %s \tEmail: %s  \tName: %s \tPassword: %s\n", username, email, name, password);
            }

            rs.close();
            st.close();
            conn.close();

        }catch(SQLException e){

            System.out.println("SQL Exception occured:" + e.getMessage());
            e.printStackTrace();

        }catch (Exception e){

            System.out.println("Exception occured:" + e.getMessage());
            e.printStackTrace();
        }finally {

            try{
                if ( st!= null){
                    st.close();
                }
            }catch(SQLException e){
                // Nothing can be done...
                e.printStackTrace();
            }

            try{
                if ( conn!= null){
                    conn.close();
                }
            }catch(SQLException e){
                // Nothing can be done...
                e.printStackTrace();
            }
            System.out.println("\nGood Bye...");
        }

    }
}
