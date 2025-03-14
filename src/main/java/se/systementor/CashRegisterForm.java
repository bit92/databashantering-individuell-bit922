package se.systementor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashRegisterForm {
    private JPanel panel1;
    private JPanel panelRight;
    private JPanel panelLeft;
    private JTextArea receiptArea;
    private JPanel buttonsPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JButton payButton;
    private Database database = new Database();
    private Product lastClickedProduct = null;
    private double totalAmount = 0.0;

    public CashRegisterForm() {
        receiptArea.setText("");
        for (Product product : database.activeProducts()) {
            JButton button = new JButton(product.getProductName());
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    lastClickedProduct = product;
                    textField1.setText(product.getProductName());
                    payButton.setEnabled(true);
                }
            });
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastClickedProduct == null || textField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a product and enter quantity.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(textField2.getText());
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Quantity must be greater than zero.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    totalAmount = totalAmount + (lastClickedProduct.getPrice()*quantity);
                    receiptArea.append(lastClickedProduct.getProductName() + "  " + quantity + " x " +
                            lastClickedProduct.getPrice() + " = " + String.format("%.2f",(lastClickedProduct.getPrice()*quantity) ) + "\n");


                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity. Please enter a number.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalAmount == 0) {
                    JOptionPane.showMessageDialog(null, "No items added to the cart.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                receiptArea.append("-------------------------------------------\n");
                receiptArea.append("Total Amount: " + String.format("%.2f", totalAmount) + "\n");
                receiptArea.append("TACK FÖR DITT KÖP\n");

                receiptArea.append("\n-----------------------------------------\n");
                textField1.setText("");
                textField2.setText("");
                totalAmount = 0;
                payButton.setEnabled(false);
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("Cash Register");
        frame.setContentPane(new CashRegisterForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }

}