package japaneseDictionary_version_0_2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WordProc extends JFrame implements ActionListener {
	JPanel p;
	JTextField tfWord, tfHurigana, tfMean;
	JButton btnInsert, btnCancel, btnUpdate, btnDelete;
	
	GridBagLayout gb;
	GridBagConstraints gbc;
	WordList wList;
	
	public WordProc() {//�ܾ� �߰��� ������
		createUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}
	public WordProc(WordList wList) {//�ܾ� �߰��� ������
		createUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.wList = wList;
	}
	public WordProc(String word, WordList wList) {//����/������ ������
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		this.wList = wList;
		
		System.out.println("word="+word);
		
		WordDAO dao = new WordDAO();
		WordDTO vMem = dao.GetWordDTO(word);
		viewData(vMem);
	}//word�� ������ ����
/**WordDTO�� �ܾ� ������ ������ ȭ�鿡 �������ִ� �޼ҵ�*/
	private void viewData(WordDTO vMem) {
		String word = vMem.getWord();
		String hurigana = vMem.getHurigana();
		String mean = vMem.getMean();
		
		//ȭ�鿡 ����
		tfWord.setText(word);
		tfHurigana.setText(hurigana);
		tfMean.setText(mean);
	}//viewData end
	
	private void createUI(){
		this.setTitle("�ܾ�����");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;
		
		//�ܾ�
		JLabel bWord = new JLabel("�ܾ�");
		tfWord = new JTextField(20);
		//�׸���鿡 ���̱�
		gbAdd(bWord,0,0,1,1);
		gbAdd(tfWord, 1,0,1,1);
		
		//�ĸ�����
		JLabel bHuri = new JLabel("�ĸ�����");
		tfHurigana = new JTextField(30);
		gbAdd(bHuri, 0, 1, 1, 1);
		gbAdd(tfHurigana, 1, 1, 1, 1);
	
		//��
		JLabel bMean = new JLabel("��");
		tfMean = new JTextField(30);
		gbAdd(bMean, 0, 2, 1, 1);
		gbAdd(tfMean, 1, 2, 1, 1);
		
		//��ư
		JPanel pButton = new JPanel();
		btnInsert = new JButton("�߰�");
		btnUpdate = new JButton("����");
		btnDelete = new JButton("����");
		btnCancel = new JButton("���");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		
		gbAdd(pButton, 0, 10, 4, 1);
		
		//��ư ���콺���Ǹ����� ���
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}//createUI() end
	
/**�׸���鷹�̾ƿ��� ���̴� �޼ҵ�*/
	private void gbAdd(JComponent c, int x, int y, int w,int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2,2,2,2);
		add(c,gbc);
	}//gbAdd() end
	public static void main(String[] args) {
		new WordProc();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==btnInsert) {
			insertWord();
			System.out.println("insertWord()ȣ�� ����");
		}else if(ae.getSource()==btnCancel) {
			this.dispose();//â�ݱ�(����â�� ����)
		}else if(ae.getSource()==btnUpdate) {
			UpdateWord();
		}else if(ae.getSource()==btnDelete) {
			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_CANCEL_OPTION);
			if(x == JOptionPane.OK_OPTION) {
				deleteWord();
			}else {
				JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�");
			}
		}
		
		//jTable ���� ���� �޼ҵ� ȣ��
		wList.jTableRefresh();
	}//actionPerformed() end
	
	private void deleteWord() {
		String word = tfWord.getText();

		WordDAO dao = new WordDAO();
		boolean ok = dao.deleteWord(word);
		
		if(ok){
            JOptionPane.showMessageDialog(this, "�����Ϸ�");
            dispose();         
           
        }else{
            JOptionPane.showMessageDialog(this, "��������");
           
        }
	}//deleteWord end
	
	private void UpdateWord() {  
        //1. ȭ���� ������ ��´�.
        WordDTO dto = getViewData();     
        //2. �������� DB�� ����
        WordDAO dao = new WordDAO();
        boolean ok = dao.updateWord(dto);
       
        if(ok){
            JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "��������: ���� Ȯ���ϼ���");   
        }
    }//UpdeteWord end
	 
    private void insertWord(){   
        //ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
    	WordDTO dto = getViewData();
    	WordDAO dao = new WordDAO();       
        boolean ok = dao.insertWord(dto);
       
        if(ok){
            JOptionPane.showMessageDialog(this, "�߰��� �Ϸ�Ǿ����ϴ�.");
            dispose();
           
        }else{	           
            JOptionPane.showMessageDialog(this, "�߰��� ���������� ó������ �ʾҽ��ϴ�.");
        }
    }//insertWord end
    
    public WordDTO getViewData() {
    	//ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
    	WordDTO dto = new WordDTO();
    	String word = tfWord.getText();
    	String hurigana = tfHurigana.getText();
    	String mean = tfMean.getText();
    	
    	dto.setWord(word);
    	dto.setHurigana(hurigana);
    	dto.setMean(mean);
    	
    	return dto;
    }//getViewData end
}
