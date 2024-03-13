import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.String.valueOf;

public class test {
    public static void main(String[] args){
        Connection con=DBConnection.getConnect();
                    if (con!=null) {
                        try {
                            int lastId = 0;
//                              обращение к столбцу patient_id
                            Statement st = con.createStatement();
                            ResultSet rs_lastId = st.executeQuery("SELECT patient_id FROM \"Patient\"");
                            while (rs_lastId.next()){       //нахождение последней строки
                                lastId = rs_lastId.getInt(1);
                                System.out.println(lastId);
                            }
                            String photo="yea";
                            ResultSet rs_insert =st.executeQuery("INSERT INTO \"Patient\"(patient_id," +
                                    "photo, first_name, last_name, passport, birthdate, gender, address, phone_number," +
                                    "email, med_card_ issue_date_med_card, req_date, ssn, ssn_end, diagnosis, medical_story)" +
                                    "VALUES ("+(lastId+1)+","+photo+")");

                            st.close();
                            rs_lastId.close();
                            con.close();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                    }
    }
}
