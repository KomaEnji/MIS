import net.proteanit.sql.DbUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class DoctorsForm {
    public JPanel doctorsPanel;
    private JTable scheduleTable;
    private JButton weekButton;
    private JButton monButton;
    private JButton tueButton;
    private JButton wednButton;
    private JButton fhuButton;
    private JButton friButton;
    private JTable choicePatientTable;
    private JTextField searchPatientTFied;
    private JButton referralButton;
    private JLabel tip2Label;
    private JButton searchPatientButton;
    private JLabel tip3Label;
    public int selectedRow;
    public int cabNumber=0;
    public  String fullNameDoctor = "";
    public String fullNamePatient = "";
    public String medCardNumber = "";
    public int scheduleId;
    public Time referralTime;
    public int fkDoctorId;
    public int patientId;
    /**
     *
      */

    public DoctorsForm() {


        weekButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.monday AS Пн, S.tuesday AS Вт, S.wednsday AS Ср, " +
                            "S.thursday AS Чт, S.friday AS Пт, S.fk_doctor_id " +
                            " FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}

            }
        });
        monButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.monday AS Пн, S.fk_doctor_id  " +
                            "FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });
        tueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.tuesday AS Вт , S.fk_doctor_id " +
                            "FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });
        wednButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.wednsday AS Ср, S.fk_doctor_id  " +
                            "FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });
        fhuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.thursday AS Чт, S.fk_doctor_id  " +
                            "FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });
        friButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                assert con != null;
                try{
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT S.id AS №, D.cabinet AS Кабинет, D.specialisation AS " +
                            "Специализация, D.full_name AS ФИО, S.friday AS Пт, S.fk_doctor_id  " +
                            "FROM \"doctor\" D " +
                            "JOIN \"schedule\" S ON D.doctor_id = S.fk_doctor_id ORDER BY full_name;");
                    scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
                    con.close();
                    st.close();
                    rs.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });

//        Слушатель для таблицы пациентов для выбора строки
        choicePatientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = choicePatientTable.rowAtPoint(e.getPoint());
                if (row>-1) selectedRow = choicePatientTable.convertRowIndexToModel(row);
                patientId = Integer.parseInt(String.valueOf(choicePatientTable.getModel().getValueAt(selectedRow,0)));
                fullNamePatient = String.valueOf(choicePatientTable.getModel().getValueAt(selectedRow,1));
                medCardNumber = String.valueOf(choicePatientTable.getModel().getValueAt(selectedRow,2));
                tip2Label.setText(fullNamePatient+" "+medCardNumber);

            }
        });
// кнопка поиска пациентов по фамилии и вывод результата в таблицу
        searchPatientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Connection con = DBConnection.getConnect();
                try{
                    String search = searchPatientTFied.getText();
                    PreparedStatement pst = con.prepareStatement("SELECT patient_id, CONCAT(last_name,' ',LEFT(first_name,1),'. '," +
                            "LEFT(middle_name,1),'.' ) AS ФИО, med_card AS №Карты, last_request AS ДатаПоследнегоОбращения" +
                            " FROM \"patient1\" WHERE last_name = ?");
                    pst.setString(1,search);
                    ResultSet rs = pst.executeQuery();
                    choicePatientTable.setModel(DbUtils.resultSetToTableModel(rs));
                    rs.close();
                    pst.close();
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
            }
        });
//        слушатель мыши на таблицу с расписанием, для получения данных из таблицы
        scheduleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = scheduleTable.rowAtPoint(e.getPoint());
                if (row>-1) selectedRow = scheduleTable.convertRowIndexToModel(row);
                scheduleId = Integer.parseInt(String.valueOf(scheduleTable.getModel().getValueAt(selectedRow,0)));
                cabNumber = Integer.parseInt(String.valueOf(scheduleTable.getModel().getValueAt(selectedRow,1)));
                fullNameDoctor = String.valueOf(scheduleTable.getModel().getValueAt(selectedRow,3));
                referralTime = Time.valueOf(String.valueOf(scheduleTable.getModel().getValueAt(selectedRow,4)));
                fkDoctorId = Integer.parseInt(String.valueOf(scheduleTable.getModel().getValueAt(selectedRow,5)));
                tip3Label.setText(scheduleId+" "+cabNumber+" "+fullNameDoctor+" "+referralTime+" "+fkDoctorId);


            }
        });
        referralButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Connection con = DBConnection.getConnect();
                try{
                    int lastId = 0;
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT referral_id FROM referral ORDER BY referral_id ASC");
                    while(rs.next()){
                        lastId=rs.getInt(1);
                    }

                    assert con != null;
                    PreparedStatement pst = con.prepareStatement("INSERT INTO \"referral\"(referral_id,fk_patient_id,fk_schedule_id)" +
                            " VALUES(?,?,?)");
                    pst.setInt(1,lastId+1);
                    pst.setInt(2,patientId);
                    pst.setInt(3,scheduleId);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Талон сформирован");
                }
                catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}

//                try (InputStream is = getClass().getClassLoader().getResourceAsStream("C:\\Users\\Home\\Downloads\\shablon.docx");
//
//                     XWPFDocument doc = new XWPFDocument(is)) {
//
//        /*try (XWPFDocument doc = new XWPFDocument(
//                Files.newInputStream(Paths.get(input)))
//        ) {*/
//
//                    List<XWPFParagraph> xwpfParagraphList = doc.getParagraphs();
//                    //Iterate over paragraph list and check for the replaceable text in each paragraph
//                    for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
//                        for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
//                            String docText = xwpfRun.getText(0);
//                            //replacement and setting position
//                            docText = docText.replace("${ИМЯ}", fullNamePatient);
//                            xwpfRun.setText(docText, 0);
//                        }
//                    }
//
//                    // save the docs
//                    try (FileOutputStream out = new FileOutputStream("C:\\Users\\Home\\Downloads\\output.docx")) {
//                        doc.write(out);
//                    }
//
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
            }
        });
    }
}
