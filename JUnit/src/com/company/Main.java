package com.company;

public class Main {

    public static void main(String[] args) {
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
        connect.insertMovieDB(film);

    }

}
