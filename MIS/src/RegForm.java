import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class RegForm {
    private JPanel startPanel;
    private JButton regButton;
    private JTextField lnameTField;
    private JTextField fnameTField;
    private JTextField mnameTField;
    private JTextField birthdateTField;
    private JTextField addressTField;
    private JRadioButton MRadioButton;
    private JRadioButton FRadioButton;
    private JTextField emailTField;
    private JTextField phoneTField;
    private JTextField medCardTField;
    private JTextField medCardIssueTField;
    private JTextField lastReqTField;
    private JTextField visitDateTField;
    private JTextField ssnTField;
    private JTextField ssnDateEndTField;
    private JTextArea diagnosisTArea;
    private JTextArea medStoryTArea;
    private JButton choicePhotoButton;
    private JButton hospButton;
    private JButton doctorsButton;
    public String Gender="M";
    public File imgPath;

//    метод для проверки полей с датами
    public void checkFields(){
        String reg = "[0-9]{2}.[0-9]{2}.[0-9]{4}";
        try {
            if (birthdateTField.getText().matches(reg) && medCardIssueTField.getText().matches(reg) &&
                    lastReqTField.getText().matches(reg) && visitDateTField.getText().matches(reg) &&
                    ssnDateEndTField.getText().matches(reg)) {
                insertFields();
            } else {
                JOptionPane.showMessageDialog(null,"Дата введена неверно");
            }
        }
        catch (Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
    }

    public void insertFields() {
        Connection con = DBConnection.getConnect();
        if (con != null) {
            try {
                // Вставка данных
                int lastId = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                Statement st = con.createStatement();
                ResultSet rs_lastId = st.executeQuery("SELECT patient_id FROM \"patient1\" ORDER BY patient_id ASC;");
                while (rs_lastId.next()) {
                    lastId = rs_lastId.getInt(1);
                }
                // Обновление изображения
                System.out.println("путь" + imgPath);
                File imageFile = new File(String.valueOf(imgPath));
                FileInputStream fis = new FileInputStream(imageFile);
//                PreparedStatement updateStatement = con.prepareStatement("UPDATE \"patient1\" SET \"photo\"=? WHERE patient_id=?");
//                updateStatement.setBinaryStream(1, fis, (int) imageFile.length());
//                updateStatement.setInt(2, lastId + 1);
//                updateStatement.executeUpdate();
                PreparedStatement insertStatement = con.prepareStatement("INSERT INTO \"patient1\"(patient_id," +
                        " first_name, middle_name, last_name, email, gender, birthday, address, phone_number, med_card," +
                        " med_card_issue, last_request, visit_date, ssn, ssn_date_end, diagnosis, med_story,photo)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                insertStatement.setInt(1, lastId + 1);
                insertStatement.setString(2, fnameTField.getText());
                insertStatement.setString(3, mnameTField.getText());
                insertStatement.setString(4, lnameTField.getText());
                insertStatement.setString(5, emailTField.getText());
                insertStatement.setString(6, Gender);
                insertStatement.setDate(7, Date.valueOf(LocalDate.parse(birthdateTField.getText(),formatter)));
                insertStatement.setString(8, addressTField.getText());
                insertStatement.setString(9, phoneTField.getText());
                insertStatement.setString(10, medCardTField.getText());
                insertStatement.setDate(11, Date.valueOf(LocalDate.parse(medCardIssueTField.getText(),formatter)));
                insertStatement.setDate(12, Date.valueOf(LocalDate.parse(lastReqTField.getText(),formatter)));
                insertStatement.setDate(13, Date.valueOf(LocalDate.parse(visitDateTField.getText(),formatter)));
                insertStatement.setString(14, ssnTField.getText());
                insertStatement.setDate(15, Date.valueOf(LocalDate.parse(ssnDateEndTField.getText(),formatter)));
                insertStatement.setString(16, diagnosisTArea.getText());
                insertStatement.setString(17, medStoryTArea.getText());
                insertStatement.setBinaryStream(18,fis, (int) imageFile.length());

                insertStatement.executeUpdate();
                st.close();
                rs_lastId.close();
                con.close();
            } catch (Exception ex) {
                try {
                    con.rollback(); // Откат транзакции в случае ошибки
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Ошибка при откате транзакции: " + e.getMessage());
                }
            }
        }
    }
//  основная форма с кнопками
    public RegForm() {
        regButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                checkFields();
            }
        });


        MRadioButton.addActionListener(e -> {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
            if (selectedRadioButton.isSelected()) {
                Gender="M";
            }

        });
        FRadioButton.addActionListener(e -> {
            JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
            if (selectedRadioButton.isSelected()) {
                Gender="F";
            }
        });
//        Кнопка выбора фото
        choicePhotoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser jf = new JFileChooser();
                int result=jf.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    if(jf.getSelectedFile().exists()){
                        imgPath = new File(String.valueOf(jf.getSelectedFile()));
                        System.out.println(imgPath);
                    }
                }
            }
        });
        hospButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame frame = new JFrame("HospytForm");
                frame.setContentPane(new HospytForm().hospytPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        doctorsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFrame frame = new JFrame("DoctorsForm");
                frame.setContentPane(new DoctorsForm().doctorsPanel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegForm");
        frame.setContentPane(new RegForm().startPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
