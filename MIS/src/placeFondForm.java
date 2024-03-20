import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class placeFondForm {
    private JPanel placeFondPanel;
    public   JTable placeTable;
    public   JTable patientTable;
    private JButton moveToBedButton;
    private JButton clearButton;
    private JLabel selectedPatientLabel;
    private JLabel selectedBedLabel;
    public int selectedRowInPatient;
    public int selectedRowInBed;
    public String selectedPatient;
    public int patientId;
    public int bedId;
    public int fkPatientId;
    public int hospitId;

    public void showTables(){
        Connection con = DBConnection.getConnect();
        //ТАБЛИЦА С ПАЦИЕНТАМИ
        try{
            assert con != null;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT H.fk_patient_id AS №1, " +
                    "H.hospitalization_id AS №2, " +
                    " CONCAT(P.last_name,' ',LEFT(P.first_name,1),'. '," +
                    " LEFT(P.middle_name,1),'.' ) AS ФИО " +
                    "FROM \"hospitalization1\" H" +
                    " JOIN (SELECT MAX(hospitalization_id) hospitalization_id, fk_patient_id FROM hospitalization1 GROUP BY fk_patient_id) A " +
                    "USING (hospitalization_id, fk_patient_id)" +
                    " JOIN \"patient1\" P ON  fk_patient_id=patient_id" +
                    " ORDER BY patient_id;");
            patientTable.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        }
        catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
//        ТАБЛИЦА С МЕСТАМИ
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT bed_id AS №," +
                    "room AS Комната, bed AS Кровать, patient AS ПрикрепленныйПациент" +
                    " FROM \"bed\" ORDER BY bed_id ASC;");
            placeTable.setModel(DbUtils.resultSetToTableModel(rs));
            rs.close();
        }
        catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
    }

    public placeFondForm() {
        showTables();
        Connection con = DBConnection.getConnect();
//        ВЫБОР КРОВАТИ
        placeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = placeTable.rowAtPoint(e.getPoint());
                if (row>-1) selectedRowInBed = placeTable.convertRowIndexToModel(row);
                bedId = Integer.parseInt(String.valueOf(placeTable.getModel().getValueAt(selectedRowInBed,0)));
                selectedBedLabel.setText("Выбран: "+bedId);
            }
        });
//        ВЫБОР ПАЦИЕНТА ИЗ ТАБЛИЦЫ
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = patientTable.rowAtPoint(e.getPoint());
                if (row>-1) selectedRowInPatient = patientTable.convertRowIndexToModel(row);
                patientId = Integer.parseInt(String.valueOf(patientTable.getModel().getValueAt(selectedRowInPatient,0)));
                hospitId = Integer.parseInt(String.valueOf(patientTable.getModel().getValueAt(selectedRowInPatient,1)));
                selectedPatient = String.valueOf(patientTable.getModel().getValueAt(selectedRowInPatient,2));
                selectedPatientLabel.setText("Выбран: "+patientId+" "+hospitId+" "+selectedPatient);
            }
        });
        moveToBedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (patientId!=0 && hospitId!=0 && bedId!=0){
                    try {
                        assert con != null;
                        PreparedStatement pst = con.prepareStatement("UPDATE \"bed\" " +
                                " SET fk_patient_id=?,fk_hospitalization_id=?,patient=?" +
                                " WHERE bed_id=?");
                        pst.setInt(1,patientId);
                        pst.setInt(2,hospitId);
                        pst.setString(3,selectedPatient);
                        pst.setInt(4,bedId);
                        pst.executeUpdate();
                        pst.close();
                        placeTable.setValueAt(selectedPatient,bedId-1,3);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Не выбраны поля");
                }

            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (bedId != 0){
                    try {
                        PreparedStatement pst = con.prepareStatement("UPDATE \"bed\" " +
                                "SET fk_patient_id=null, fk_hospitalization_id=null, patient=null " +
                                "WHERE bed_id=?; ");
                        pst.setInt(1,bedId);
                        pst.executeUpdate();
                        pst.close();
                        placeTable.setValueAt(null,bedId-1,3);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else JOptionPane.showMessageDialog(null,"Не выбрано койко-место");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("placeFondForm");
        frame.setContentPane(new placeFondForm().placeFondPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
