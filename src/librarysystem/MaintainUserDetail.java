package librarysystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class MaintainUserDetail extends JFrame implements ActionListener{
    
    Choice userid;
    JTable table;
    JButton search, print, update, add, cancel, delete;
    JLabel heading;
    JTableHeader header;
    
    MaintainUserDetail() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setSize(600, 400);
        setLocation(400, 150);
        
        setTitle("USER RECORD");
        
        heading = new JLabel("Search by User_Id ");
        heading.setBounds(20, 20, 150, 20);
        add(heading);
        
        userid = new Choice();
        userid.setBounds(180, 20, 150, 20);
        add(userid);
                
        try {
            ConnectionDatabase c = new ConnectionDatabase();
            ResultSet rs = c.s.executeQuery("select * from users_customer");
            while(rs.next()) {
                userid.add(rs.getString("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        table = new JTable();
        table.setBackground(new Color(225, 245, 254));
        table.setForeground(Color.BLACK);
        
        header = table.getTableHeader();
        header.setBackground(new Color(31, 97, 141));
        header.setForeground(Color.BLACK);
        
        try {
            ConnectionDatabase c = new ConnectionDatabase();
            ResultSet rs = c.s.executeQuery("select * from users_customer");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 100, 550, 250);
        add(jsp);
        
        search = new JButton("Search");
        search.setBounds(340, 20, 80, 20);
        search.setBackground(new Color(31, 97, 141));
        search.setForeground(Color.WHITE);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(20, 70, 80, 20);
        print.setBackground(new Color(31, 97, 141));
        print.setForeground(Color.WHITE);
        print.addActionListener(this);
        add(print);
        
        update = new JButton("Update");
        update.setBounds(110, 70, 80, 20);
        update.setBackground(new Color(31, 97, 141));
        update.setForeground(Color.WHITE);
        update.addActionListener(this);
        add(update);
        
        delete = new JButton("Delete");
        delete.setBounds(200, 70, 80, 20);
        delete.setBackground(new Color(31, 97, 141));
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(290, 70, 80, 20);
        cancel.setBackground(new Color(31, 97, 141));
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        add(cancel);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from users_customer where id = '"+userid.getSelectedItem()+"'";
            try {
                ConnectionDatabase c = new ConnectionDatabase();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else if (ae.getSource() == update) {
             new UserUpdateDelete();
        } else if (ae.getSource() == delete) {
            new UserUpdateDelete();
        } else if (ae.getSource() == cancel) {
             setVisible(false);
//             new AdminDashboard();
        }
        else {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new MaintainUserDetail().setVisible(true);
    }
}
