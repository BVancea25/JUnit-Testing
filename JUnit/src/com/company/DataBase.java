package com.company;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

     static final String DBURL = "jdbc:mysql://localhost/proiect_cinema?serverTimezone=UTC";
     static final String USERNAME = "root";
     static final String PASSWORD = "";
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private CallableStatement cstmt;

    MyLogger logger=new MyLogger(Logger.getLogger(Logger.GLOBAL_LOGGER_NAME));



    public void connectToDB() {
        try {

            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiect_cinema?" + "user=root&serverTimezone=UTC");
        } catch (Exception e) {
            logger.LogException(e);
        }
    }

    public String login(String email, String password)  {
        try {
            String sql = "CALL login(?,?)";
            cstmt = connect.prepareCall(sql);

            cstmt.setString(1, email);
            cstmt.setString(2, password);

            cstmt.execute();

            result = cstmt.getResultSet();
            String outputMessage = "";
            while (result.next()) {
                outputMessage = result.getString(1);
            }
            cstmt.close();

            return outputMessage;


        } catch (SQLException e) {
            logger.LogException(e);


        }
        return "Eroare logare";
    }

    public String register(String email, String password) {
        try {
            String sql = "{CALL inregistrare(?,?)}";
            cstmt = connect.prepareCall(sql);

            cstmt.setString(1, email);
            cstmt.setString(2, password);
            cstmt.execute();
            result = cstmt.getResultSet();
            String outputMessage = "";
            while (result.next()) {
                outputMessage = result.getString(1);
            }
            cstmt.close();
            return outputMessage;

        } catch (SQLException e) {
            logger.LogException(e);
        }
        return "Eroare Inregistrare";

    }

    public ArrayList<Movie> getListOfMoviesDB() {
        ArrayList<Movie> list =new ArrayList<>();
        try {
            String sql = "SELECT * FROM film";

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Movie movie;
            while (result.next()) {
                movie = new Movie(result.getString("titlu"),
                        result.getString("descriere"),
                        result.getString("durata"),
                        result.getString("gen"),
                        result.getString("clasificare"),
                        result.getString("limba_dublare")
                );
                list.add(movie);
            }

        } catch (SQLException e) {
            logger.LogException(e);
        }
        return list;
    }

    public int insertMovieDB(Movie movie) {
        int code = -1;

        /*
            code = 0  => filmul exista in baza de date
            code = 1  => filmul a fost introdus in BD cu succes
        */

        try(PreparedStatement prepare2=connect.prepareStatement("INSERT INTO film (titlu, descriere, durata, gen, clasificare, limba_dublare) VALUES (?,?,?,?,?,?)")) {
            String sql1 = "SELECT titlu FROM film WHERE titlu = (?)";

            prepare = connect.prepareStatement(sql1);
            prepare.setString(1, movie.getTitle());
            result = prepare.executeQuery();
            if (result.next()) {

                code = 0;
            } else {

                prepare2.setString(1, movie.getTitle());
                prepare2.setString(2, movie.getDescription());
                prepare2.setString(3, movie.getRuntime());
                prepare2.setString(4, movie.getGenre());
                prepare2.setString(5, movie.getAgeRestrictions());
                prepare2.setString(6, movie.getLanguage());
                int nrRowAffected = prepare2.executeUpdate();
                if (nrRowAffected != 0) {
                    code = 1;   // filmul a fost introdus in BD cu succes
                }
            }

        } catch (SQLException e) {
            logger.LogException(e);
        }
        return code;
    }

    public int deleteMovieBD(Movie movie) {
        int code = -1;
        /*
            code = -1 => erori
            code = 0  => cod de eroare liber
            code = 1  => filmul a fost sters din BD cu succes
        */
        try {
            String sql = "DELETE FROM film WHERE titlu = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, movie.getTitle());
            int nrRowAffected = prepare.executeUpdate();
            if (nrRowAffected != 0) {
                code = 1;
            }
            prepare.close();
        } catch (SQLException e) {
            logger.LogException(e);
        }
        return code;
    }

    public int countAvailableFilmsDB(){
        int code = -1;

        /*
            code = -1 => erori
            code = count = nr de filme
        */
        try(Statement statement=connect.createStatement()){
            String sql = "SELECT COUNT(titlu) FROM film;";
            result = statement.executeQuery(sql);
            int count;
            while(result.next()){
                count = result.getInt(1);
                code = count;
            }

        } catch (SQLException e) {
            logger.LogException(e);

        }

        return code;
    }

    public int insertLoc(int nrLocuri, String idSala)  {

            int i=1;
            int nrRand=1;
            int code=-1; //-1 erroare
                        //0 inserare cu succes
        try(PreparedStatement cstmt=connect.prepareCall("INSERT INTO loc (idsala,rand,numar_loc) VALUES (?,?,?);")){
            while(i<=nrLocuri) {

                if (i % 11 == 0) nrRand++;
                cstmt.setString(1, idSala);
                cstmt.setString(2, String.valueOf(nrRand));
                cstmt.setString(3, String.valueOf(i));
                cstmt.execute();
                i++;
                code = 1;
            }
        }catch (SQLException e){
            logger.LogException(e);
            code=-1;
        }
        return code;
    }

    public String getIDFilm(String titlu) {

        String id="" ;
        try(CallableStatement cstmt2=connect.prepareCall("SELECT id FROM film WHERE titlu LIKE ? ")) {

            cstmt2.setString(1, titlu);
            cstmt2.execute();
            ResultSet resultSet = cstmt2.getResultSet();
            while(resultSet.next()) {
                id = resultSet.getString("id");
            }

        } catch (SQLException e) {
            logger.LogException(e);

        }
        return id;
    }

    public int InsertEcranizare(String data_rulare,String idFilm,int idSala) {
        int code=-1;

        try(PreparedStatement cstmt = connect.prepareCall("INSERT INTO ecranizare (data_rulare,idfilm,idsala) VALUES(?,?,?)")){
            cstmt.setString(1,data_rulare);
            cstmt.setString(2,idFilm);
            cstmt.setString(3,String.valueOf(idSala));
            cstmt.execute();
            code=1;
        } catch (SQLException e) {
            logger.LogException(e);
        }
        return code;
    }

    public String getSala(int idSala){
        String valoare="-1";
        try(PreparedStatement statement=connect.prepareCall("SELECT numar_locuri FROM sala WHERE id=?")){
            statement.setString(1,String.valueOf(idSala));
            statement.execute();
            ResultSet resultSet=statement.getResultSet();
            while(resultSet.next()){
                valoare=resultSet.getString("numar_locuri");
            }
        }catch (Exception e){

            logger.LogException(e);
        }
        return valoare;
    }

}
