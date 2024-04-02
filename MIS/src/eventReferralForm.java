import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class eventReferralForm {
    private JPanel panel1;
    private JTextField loginTextField;
    private JButton logginButton;
    private JPanel panel2;
    private JPasswordField passwordField;
    private JLabel docNameLabel;
    private  JTable patientTable;
    private JButton сформироватьButton;
    private JPanel authorizPanel;
    public static JFrame frame;
    public static Connection con = DBConnection.getConnect();

    public  void invokeTable(){
        try {
            Statement st = con.createStatement();
            ResultSet rs  = st.executeQuery("SELECT patient_id,last_name,med_card FROM patient1 order by patient_id;");
            patientTable.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public eventReferralForm() {
        logginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    assert con != null;
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT doctor_id, full_name, login, password FROM \"doctor\";");
                    if (!loginTextField.getText().isEmpty() || passwordField.getPassword().length>6) {
                        while (rs.next()) {
                            String doctorFName = rs.getString("full_name");
                            String login = rs.getString("login");
                            String password = rs.getString("password");
                            if (loginTextField.getText().equals(login) && String.valueOf(passwordField.getPassword()).equals(password)) {
                                JOptionPane.showMessageDialog(null,"Пользователь успешно авторизирован");
                                docNameLabel.setText(doctorFName);
                                invokeTable();
                                authorizPanel.setVisible(false);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"Введите логин и пароль длинной не менее 6 символов");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        frame = new JFrame("eventReferralForm");
        eventReferralForm form = new eventReferralForm();
        frame.setContentPane(form.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}