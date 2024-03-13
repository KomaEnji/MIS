import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorsForm {
    public JPanel doctorsPanel;
    private JTable scheduleTable;

    public DoctorsForm() {
        Connection con = DBConnection.getConnect();
        assert con != null;
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT D.cabinet AS Кабинет, D.specialisation AS " +
                    "Специализация, D.full_name AS ФИО, S.monday AS Пн, S.tuesday AS Вт, S.wednsday AS Ср, " +
                    "S.thursday AS Чт, S.friday AS Пт, S.saturday AS Сб " +
                    "FROM \"doctor\" D " +
                    "JOIN \"schedule\" S ON D.doctor_id = S.schedule_id");
            scheduleTable.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();
            st.close();
            rs.close();
        }
        catch (SQLException ex){JOptionPane.showMessageDialog(null,ex.getMessage());}

        scheduleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
    }
}
