package org.example;

import spark.Spark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {



        JFrame frame = new JFrame("Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        JTextField inputField1 = new JTextField();
        JTextField inputField2 = new JTextField();

        inputField1.setFont((new Font("Arial", Font.PLAIN, 30)));
        inputField2.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Nome:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label2 = new JLabel("Acesso:");
        label2.setFont((new Font("Arial", Font.BOLD, 30)));


        panel.add(label1);
        panel.add(inputField1);
        panel.add(label2);
        panel.add(inputField2);


        String[] buttonLabels ={
                "ENVIAR", "SAIR"
        };

        for (String label: buttonLabels){
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    try {
                        String path = "http://localhost:8080/api/" + inputField1.getText();
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        int responseCode = connection.getResponseCode();
                        System.out.println("Code:" + responseCode);
                        if (responseCode != HttpURLConnection.HTTP_OK) {
                            System.out.println("Got an unexpected response code");
                        }

                        BufferedReader in = new BufferedReader((new InputStreamReader(connection.getInputStream())));
                        String line;
                        StringBuilder response = new StringBuilder();

                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        in.close();
                        connection.disconnect();

                        inputField2.setText(response.toString());
                        if (label.equals("SAIR")) {
                            System.exit(0);
                        }


                    }
                    catch (Exception er){
                        System.out.println(er);
                    }


                }

            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}

