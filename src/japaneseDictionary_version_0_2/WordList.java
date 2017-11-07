package japaneseDictionary_version_0_2;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WordList extends JFrame implements MouseListener,ActionListener{
	Vector v;
	Vector cols;
	DefaultTableModel model;
	JTable jTable;
	JScrollPane pane;
	JPanel pbtn;
	JButton btnInsert;
	
	public WordList() {//생성자 start
		super("나만의 단어장 v0.1.1");
		WordDAO dao = new WordDAO();
		v = dao.getMemberList();
		System.out.println("v="+v);
		cols = getColumn();
		
		model = new DefaultTableModel(v,cols);
		
		jTable = new JTable(model);
		pane = new JScrollPane(jTable);
		add(pane);
		
		pbtn = new JPanel();
		btnInsert = new JButton("단어 등록");
		pbtn.add(btnInsert);
		add(pbtn,BorderLayout.NORTH);
		
		jTable.addMouseListener(this);
		btnInsert.addActionListener(this);
		
		setSize(600,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//생성자 end
	public Vector getColumn() {
		Vector col = new Vector();
		col.add("단어");
		col.add("후리가나");
		col.add("뜻");
		return col;
	}
	public void jTableRefresh() {
		WordDAO dao = new WordDAO();
		DefaultTableModel model = new DefaultTableModel(dao.getMemberList(), getColumn());
		jTable.setModel(model);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int r = jTable.getSelectedRow();
		String word = (String)jTable.getValueAt(r, 0);
		WordProc mem = new WordProc(word,this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource() == btnInsert ){
	            new WordProc(this);
		 }
	}
	public static void main(String[] args) {
		new WordList();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
