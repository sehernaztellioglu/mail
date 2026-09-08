package org.example;



import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mail Gönder");
        JPanel panel = new JPanel();

        JTextField eposta = new JTextField(20);
        JTextField konu = new JTextField(20);
        JTextArea icerik = new JTextArea();

        JButton buton = new JButton("Gönder");
        JPanel butonPaneli =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        eposta.setMaximumSize(new Dimension(300, 30));
        konu.setMaximumSize(new Dimension(300, 30));
        icerik.setMaximumSize(new Dimension(300, 200));
        icerik.setPreferredSize(new Dimension(300, 200));

        placeHolder(eposta, "Alıcının e-posta adresi");
        placeHolder(konu, "Konu");
        placeHolder(icerik, "Konu içeriği");

        butonPaneli.add(buton);

        panel.add(eposta);
        panel.add(konu);
        panel.add(icerik);
        panel.add(Box.createVerticalStrut(60));
        panel.add(butonPaneli);

        buton.addActionListener(e -> {
            String alici = eposta.getText().trim();
            String baslik = konu.getText().trim();
            String mesaj = icerik.getText().trim();

            boolean alanEksik =
                    alici.isEmpty()
                            || baslik.isEmpty()
                            || mesaj.isEmpty()
                            || alici.equals("Alıcının e-posta adresi")
                            || baslik.equals("Konu")
                            || mesaj.equals("Konu içeriği");

            if (alanEksik) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Bütün alanları doldurun."
                );
                return;
            }

            try {
                MailGonderici.mailGonder(
                        alici,
                        baslik,
                        mesaj
                );

                JOptionPane.showMessageDialog(
                        frame,
                        "E-posta başarıyla gönderildi."
                );
            } catch (Exception hata) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Gönderilemedi: " + hata.getMessage()
                );

                hata.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void placeHolder(
            JTextComponent alan,
            String yazi
    ) {
        alan.setText(yazi);
        alan.setForeground(Color.GRAY);

        alan.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (alan.getText().equals(yazi)) {
                    alan.setText("");
                    alan.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (alan.getText().trim().isEmpty()) {
                    alan.setText(yazi);
                    alan.setForeground(Color.GRAY);
                }
            }
        });
    }
}