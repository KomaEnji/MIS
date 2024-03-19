import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class placeFondForm {
    private JPanel placeFondPanel;
    private JTable placeTable;
    private JTable patientTable;
    private JButton moveToBedButton;
    private JButton moveToPatientButton;
    public static void showTables(){
        Connection con = DBConnection.getConnect();
        //ТАБЛИЦА С ПАЦИЕНТАМИ
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ");

        }
        catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}

    }

    public placeFondForm() {
        showTables();

        placeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        moveToBedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
    }
}
