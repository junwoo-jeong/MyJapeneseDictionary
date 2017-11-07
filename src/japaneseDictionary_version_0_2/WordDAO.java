package japaneseDictionary_version_0_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class WordDAO {
	private static final String DRIVER
    = "oracle.jdbc.driver.OracleDriver";//DB ����̹�
	private static final String URL
    = "jdbc:oracle:thin:@localhost:1521:xe";//DB �ּ�

	private static final String USER = "s1401266"; //DB ID
	private static final String PASS = "p1401266"; //DB �н�����

	public WordDAO() {

	}
	
/**DB���� �޼ҵ�*/
	public Connection getConn(){
		Connection con = null;
   
		try {
			Class.forName(DRIVER); //1. ����̹� �ε�
			con = DriverManager.getConnection(URL,USER,PASS); //2. ����̹� ����
			System.out.println("Database�� ���� �Ǿ����ϴ�.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return con;
	}
	
/**�� �ܾ��� ������ ��� �޼ҵ�*/
	public WordDTO GetWordDTO(String word) {
		WordDTO dto = new WordDTO();
		
		Connection con = null;//����
		PreparedStatement ps = null;//���
		ResultSet rs = null;//���
		
		try {
			con = getConn();
			String sql = "SELECT * from dictionary where word=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, word);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				dto.setWord(rs.getString("WORD"));
				dto.setHurigana(rs.getString("HURIGANA"));
				dto.setMean(rs.getString("MEAN"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
/**��� �ܾ� ��� �޼ҵ�*/
	public Vector getMemberList() {
		Vector data = new Vector();
				
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			String sql = "select * from dictionary order by word asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String word = rs.getString("word");
				String hurigana = rs.getString("hurigana");
				String mean = rs.getString("mean");
				
				Vector row = new Vector();
				row.add(word);
				row.add(hurigana);
				row.add(mean);
				
				data.add(row);
			}//while
		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}
/**�ܾ� ���� �޼ҵ�*/
	public boolean insertWord(WordDTO dto) {
		boolean ok = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = getConn();
			String sql = "insert into dictionary ("+
						"word,hurigana,mean)"+
						"values(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getWord());
			ps.setString(2, dto.getHurigana());
			ps.setString(3, dto.getMean());
			int r = ps.executeUpdate();//���� -> ����
			
			if(r>0) {
				System.out.println("���� ����");
				ok=true;
			}else {
				System.out.println("���� ����");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ok;
	}//insertWord end;
	
/**�ܾ� ���� �޼ҵ�*/
	public boolean deleteWord(String word) {
		boolean ok = false;
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = getConn();
			String sql = "delete from dictionary where word=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, word);
			int r = ps.executeUpdate();
			
			if(r>0) ok = true;
		}catch(Exception e) {
			System.out.println(e + "-> �����߻�");
		}
		return ok;
	}//deleteWord end
	
/**�ܾ� ���� ���� �޼ҵ�*/
	public boolean updateWord(WordDTO vMem) {
		System.out.println("dto="+vMem.toString());
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			
			con = getConn();
			String sql = "update dictionary set word=?, hurigana=?, mean=?"+"where word=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vMem.getWord());
			ps.setString(2, vMem.getHurigana());
			ps.setString(3, vMem.getMean());
			
			int r = ps.executeUpdate();//���� -> ����
			
			if(r>0) ok = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ok;
	}//updateWord end
	
    public void userSelectAll(DefaultTableModel model) {
        
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       
        try {
            con = getConn();
            String sql = "select * from dictionary order by word asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
           
            // DefaultTableModel�� �ִ� ������ �����
            for (int i = 0; i < model.getRowCount();) {
                model.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getString(3)};
 
                model.addRow(data);                
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally{
           
            if(rs!=null)
                try {
                    rs.close();
                } catch (SQLException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            if(ps!=null)
                try {
                    ps.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            if(con!=null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
