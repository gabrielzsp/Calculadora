import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> criarEExibirGUI());
    }

    private static void criarEExibirGUI() {
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 4));

        JTextField visor = new JTextField();
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setFont(new Font("Arial", Font.PLAIN, 24));
        frame.add(visor, BorderLayout.NORTH);

        String[] rotulosBotoes = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "C", "0", "=", "/"
        };

        for (String rotulo : rotulosBotoes) {
            JButton botao = new JButton(rotulo);
            painel.add(botao);

            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String textoAtual = visor.getText();

                    if (rotulo.matches("[0-9]")) {
                        visor.setText(textoAtual + rotulo);
                    } else if (rotulo.equals("C")) {
                        visor.setText("");
                    } else if (rotulo.equals("=")) {
                        try {
                            double resultado = avaliarExpressao(textoAtual);
                            visor.setText(Double.toString(resultado));
                        } catch (ArithmeticException ex) {
                            visor.setText("Erro");
                        }
                    } else {
                        visor.setText(textoAtual + " " + rotulo + " ");
                    }
                }
            });
        }
        frame.add(painel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static double avaliarExpressao(String expressao) {
        String[] tokens = expressao.split(" ");
        double num1 = Double.parseDouble(tokens[0]);
        double num2 = Double.parseDouble(tokens[2]);
        String operador = tokens[1];

        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Divisão por zero");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operador inválido");
        }
    }
}