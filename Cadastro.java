import javax.swing.*;
import java.awt.*;

public class Cadastro {

  
    static boolean emailValido(String email) {
    email = email.trim();
    return email.contains("@") && email.endsWith(".com");
}

    // CPF válido = 11 dígitos, não todos iguais, e com os 2 dígitos verificadores corretos
    static boolean cpfValido(String cpf) {
        String digitos = cpf.replaceAll("\\D", ""); // remove pontos, traço e espaços

        if (digitos.length() != 11) return false;

        // Rejeita CPFs com todos os dígitos iguais (111.111.111-11, etc.)
        if (digitos.matches("(\\d)\\1{10}")) return false;

        
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (digitos.charAt(i) - '0') * (10 - i);
        }
        int dv1 = 11 - (soma % 11);
        if (dv1 >= 10) dv1 = 0;

        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (digitos.charAt(i) - '0') * (11 - i);
        }
        int dv2 = 11 - (soma % 11);
        if (dv2 >= 10) dv2 = 0;

        return dv1 == (digitos.charAt(9) - '0') && dv2 == (digitos.charAt(10) - '0');
    }

   
    static String fazerCadastro() {
        final String[] resultado = {null};

        JDialog janela = new JDialog((Frame) null, "Cadastro", true);
        janela.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JTextField campoNome = new JTextField(20);
        JTextField campoEmail = new JTextField(20);
        JTextField campoCpf = new JTextField(20);

        JLabel erro = new JLabel(" ");
        erro.setForeground(Color.RED);

        JButton botao = new JButton("Cadastrar");

        
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        form.add(new JLabel("Nome completo:"));
        form.add(campoNome);
        form.add(new JLabel("Email:"));
        form.add(campoEmail);
        form.add(new JLabel("CPF:"));
        form.add(campoCpf);

      
        JPanel rodape = new JPanel(new BorderLayout(0, 8));
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        rodape.add(erro, BorderLayout.CENTER);
        rodape.add(botao, BorderLayout.SOUTH);

        janela.add(form, BorderLayout.CENTER);
        janela.add(rodape, BorderLayout.SOUTH);

        botao.addActionListener(e -> {
            StringBuilder erros = new StringBuilder();

            if (campoNome.getText().trim().isEmpty()) {
                erros.append("Nome não pode ficar vazio<br>");
            }
            if (!emailValido(campoEmail.getText())) {
                erros.append("Email incorreto<br>");
            }
            if (!cpfValido(campoCpf.getText())) {
                erros.append("CPF incorreto<br>");
            }

            if (erros.length() > 0) {
                erro.setText("<html>" + erros + "</html>");
                janela.pack();
                return;
            }

            JOptionPane.showMessageDialog(janela, "Cadastro feito com sucesso", "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
            resultado[0] = campoNome.getText().trim();
            janela.dispose();
        });

        janela.getRootPane().setDefaultButton(botao); 
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true); 

        return resultado[0];
    }

    public static void main(String[] args) {
        String nomeCompleto = fazerCadastro();

        if (nomeCompleto != null) {
            System.out.println("Cadastrado: " + nomeCompleto);
        }
    }
}