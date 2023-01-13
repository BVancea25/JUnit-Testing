package com.test;


import com.company.DataBase;
import com.company.Movie;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * In aceasta clasa sunt prezente metode de testare  care verifica functionalitatile metodelor din clasa DataBase
 */
public class DataBaseTest {
    private final static String username="admin";

    /**
     * Test pentru a verifica daca metoda login va returna mesajul corect in cazul in care se logheaza un user admin
     */
    @Test
    void loginAdminTest() {
        DataBase connect= new DataBase();
        connect.connectToDB();
        assertEquals("Logat cu succes ca administrator!",connect.login(username,"admin"));
    }

    /**
     * Test pentru a verifica daca metoda login daca returneaza mesajul corect in cazul in care un user normal se va loga
     */
    @Test
     void loginUserTest() {
        DataBase connect= new DataBase();
        connect.connectToDB();
        assertEquals("Logat cu succes!",connect.login("user","user"));
    }

    /**
     * Test pentru a verifica daca metoda login daca returneaza mesajul corect in cazul in care un user nu introduce datele corecte
     */

    @Test
    void loginInvalidTest() {
        DataBase connect= new DataBase();
        connect.connectToDB();
        assertEquals("Date de logare incorecte!",connect.login("user","khgjsgjsfg"));
    }


    /**
     * Test pentru a verifica metoda register daca se comporta in modul asteptat in cazul in care un utilizator va incerca sa creeze un cont cu un email deja existent
     */
    @Test
    void registerEmailExistentTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertEquals("Exista deja un cont cu acest email",connect.register(username,"admin"));
    }

    /**
     * Test pentru a verifica daca metoda getListOfMovies returneza filme si nu este goala
     */
    @Test
    void listOfMoviesEmptyTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertFalse(connect.getListOfMoviesDB().isEmpty());
    }

    /**
     * Test pentru a vedea daca metoda insertMovie returneaza 0 in cazul in care filmul exista deja in baza de date
     */
    @Test
    void insertMovieExistentTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        Movie film=new Movie(
            "Capra cu trei iezi",
                "blablabla",
                "90",
                "Horror,Thriller",
                "PG13",
                ""
        );

        assertEquals(0,connect.insertMovieDB(film));
    }

    /**
     * Test pentru a verifica daca metoda getIDFilm returneaza o valoare valida
     */
    @Test
    void getIDMovieTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertFalse(connect.getIDFilm("Capra cu trei iezi").isEmpty());

    }

    /**
     * Test pentru a verifica metoda getIDFilm in cazul in care titlul filmului cautat nu exista in baza de date
     */
    @Test
    void getIDMovieInvalidTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertTrue(connect.getIDFilm("fakjgadfjng").isEmpty());
    }


    /**
     * Test pentru a verifica daca metoda delete returneaza valoarea corecta cand este apelata pentru a sterge un film
     * Daca metoda getIDFilm nu returneaza nici un id  metoda delete a sters filmul din baza de date
     */
    @Test
    void deleteMovieTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        Movie film=new Movie(
                "Capra cu trei iezi",
                "blablabla",
                "90",
                "Horror,Thriller",
                "PG13",
                ""
        );
        assertEquals(1,connect.deleteMovieBD(film));
        assertEquals("",connect.getIDFilm("Capra cu trei iezi"));
    }

    /**
     * Test pentru a verifica daca metoda countAvailableFilms returneaza o valoare valida si nu -1 in cazul unei erori
     */
    @Test
    void countMovieTest(){
        DataBase connect = new DataBase();
        connect.connectToDB();
        assertNotEquals(-1,connect.countAvailableFilmsDB());
    }


    /**
     * Test pentru a verifica daca metoda getSala returneaza numarul corect de locuri pentru sala respectiva
     */

    @Test
    void getSala(){
        DataBase connect = new DataBase();
        connect.connectToDB();
        assertEquals("30",connect.getSala(4));
    }

    /**
     * Test pentru a verifica daca metoda getSala va returna valoarea -1 in cazul in care id-ul salii pentru care a fost apelata nu exista in baza de date
     */
    @Test
    void getSalaEroare(){
        DataBase connect = new DataBase();
        connect.connectToDB();
        assertEquals("-1",connect.getSala(8));
    }

    /**
     * Test pentru a verifica daca metoda insertLoc va returna valoarea 1 in cazul in cazre inserarea a fost facuta cu succes
     */

    @Test
    void inserareLocTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertEquals(1,connect.insertLoc(30,"5"));
    }

    /**
     * Test pentru a verifica daca metoda insertLoc va returna valoarea -1 in cazul in care se va incerca inserarea unor locuri pentru o sala cu locuri existente
     */
    @Test
    void inserareLocErroareTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertEquals(-1,connect.insertLoc(20,"1"));
    }

    /**
     * Test pentru a verifica daca metoda insertEcranizare va returna valoarea -1 in cazul in care se va incerca inserarea unei ecranizari deja existente
     */

    @Test
    void inserareEcranizareExistentaTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertEquals(-1,connect.InsertEcranizare("2022-12-08 17:00:00","8",4));
    }

    /**
     * Test pentru a verifica daca metoda insertEcranizare va returna valoarea 1 in cazul in care ecranizarea a fost introdusa cu succes
     */
    @Test
    void inserareEcranizareTest(){
        DataBase connect=new DataBase();
        connect.connectToDB();
        assertEquals(1,connect.InsertEcranizare("2022-12-08 17:00:00","10",4));
    }




}