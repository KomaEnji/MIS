import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HospytForm {
    public JPanel hospytPanel;
    private JTextField patientSearchTField;
    private JButton searchButton;
    private JTable searchedPatientTable;
    private JButton regHospButton;
    private JButton showRejectRegButton;
    public JTable hospTable;
    private JTextField dateStartHTField;
    private JTextField dateEndHTField;
    private JTextArea textArea1;
    private JLabel choicedPatientLabel;
    private JLabel choicedHospLabel;
    private JTextArea textArea2;
    private JButton rejectButton;
    private JPanel hiddenPanel;
    public static int selectedRow;
    public  static int selectedRow1;
    public int id;
    public String instName;
    public int lastId=0;

    public void checkHFields(){
        String reg = "[0-9]{2}.[0-9]{2}.[0-9]{4}";
        try {
//            if (dateStartHTField.getText().matches(reg) && (dateEndHTField.getText().isEmpty() || dateEndHTField.getText().matches(reg))) {
            if (dateStartHTField.getText().matches(reg) && dateEndHTField.getText().matches(reg) && id!=0 && instName!=null ){
                insertHFields();
            }
            else {
                JOptionPane.showMessageDialog(null,"Дата введена неверно, либо отсутствует выбор нужного значения из таблиц");
            }
        }
        catch (Exception e){JOptionPane.showMessageDialog(null,e.getMessage());}
    }

    public void insertHFields(){
        Connection con = DBConnection.getConnect();
        try {
//            поиск последнего номера в таблице

            assert con != null;
            Statement st = con.createStatement();
            ResultSet rs_lastId = st.executeQuery("SELECT hospitalization_id FROM \"hospitalization1\" ORDER BY hospitalization_id ASC;");
            while (rs_lastId.next()) {
                lastId = rs_lastId.getInt(1);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//            вставка данных в новую строку
            PreparedStatement pst = con.prepareStatement("INSERT INTO \"hospitalization1\" (hospitalization_id,institution_name," +
                    "type,date_start,date_end,address,result,fk_patient_id) VALUES (?,?,?,?,?,?,?,?)");
            pst.setInt(1,lastId+1);
            pst.setString(2,instName);
            pst.setString(3,"type1");
            pst.setDate(4, Date.valueOf(LocalDate.parse(dateStartHTField.getText(),formatter)));
            pst.setDate(5, Date.valueOf(LocalDate.parse(dateEndHTField.getText(),formatter)));
            pst.setString(6,"address1");
            pst.setString(7,textArea1.getText());
            pst.setInt(8,id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Пациент успешно зарегистрировался на госпитализацию");
            st.close();
            rs_lastId.close();
            con.close();
        }
        catch (Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
    }

    public HospytForm() {
        hiddenPanel.setVisible(false);
        /** Вывод таблицы с названиями госпиталей*/
        Connection con = DBConnection.getConnect();
        try{
            assert con != null;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT institution_name FROM \"hospitalization1\" ORDER BY institution_name ");
            hospTable.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        }
        catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                try{
                    PreparedStatement pst = con.prepareStatement("SELECT patient_id AS №, CONCAT(last_name,' ',LEFT(first_name,1),'.',LEFT(middle_name,1),'.') AS ФИО, "+
                            "gender AS Пол, birthday AS ДатаРождения FROM \"patient1\" WHERE med_card = ?;");
//                    ___вставка данных в запрос с помощью обращения к нужному индексу
                    pst.setString(1,patientSearchTField.getText());
//                    ___формирование ResaltSet для вывода в таблицу на экране
                    ResultSet rs = pst.executeQuery();
                    searchedPatientTable.setModel(DbUtils.resultSetToTableModel(rs));
                    rs.close();
                }
                catch (Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}

            }
        });
        regHospButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                checkHFields();
            }
        });
        showRejectRegButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                hiddenPanel.setVisible(true);
            }
        });

        rejectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                try {
                    PreparedStatement updateValue = con.prepareStatement("UPDATE \"hospitalization1\" SET rejection=? WHERE fk_patient_id=?");
                    updateValue.setString(1,textArea2.getText());
                    updateValue.setInt(2,id);
                    updateValue.executeUpdate();
                    con.close();
                    updateValue.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        searchedPatientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /** Получение индекса нажатой строки в row с помощью слушателя мышки*/
                int row = searchedPatientTable.rowAtPoint(e.getPoint());
                /** Конвертирование индекса выбранной строки в модель для записи в переменную selectedRow*/
                if (row>-1) selectedRow = searchedPatientTable.convertRowIndexToModel(row);
                /** Получение содержимого выбранной строки с помощью преобразования модели*///передать в запись внешнего ключа
                id = Integer.parseInt(String.valueOf(searchedPatientTable.getModel().getValueAt(selectedRow,0)));
                //конвертирование данных в String и присваивание полученных данных в переменную FIO
                String FIO = String.valueOf(searchedPatientTable.getModel().getValueAt(selectedRow,1));
                choicedPatientLabel.setText("Выбранный пациент: "+FIO);
            }
        });
        hospTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = hospTable.rowAtPoint(e.getPoint());
                if (row>-1) selectedRow1 = hospTable.convertRowIndexToModel(row);
                instName = String.valueOf(hospTable.getModel().getValueAt(selectedRow1,0));
                choicedHospLabel.setText("Выбранный госпиталь: "+instName);
            }
        });
    }
}
