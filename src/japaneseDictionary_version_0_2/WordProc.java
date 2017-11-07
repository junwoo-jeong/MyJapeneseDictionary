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
	
	public WordProc() {//단어 추가용 생성자
		createUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
	}
	public WordProc(WordList wList) {//단어 추가용 생성자
		createUI();
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.wList = wList;
	}
	public WordProc(String word, WordList wList) {//수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnInsert.setVisible(false);
		this.wList = wList;
		
		System.out.println("word="+word);
		
		WordDAO dao = new WordDAO();
		WordDTO vMem = dao.GetWordDTO(word);
		viewData(vMem);
	}//word를 가지고 생성
/**WordDTO의 단어 정보를 가지고 화면에 셋팅해주는 메소드*/
	private void viewData(WordDTO vMem) {
		String word = vMem.getWord();
		String hurigana = vMem.getHurigana();
		String mean = vMem.getMean();
		
		//화면에 세팅
		tfWord.setText(word);
		tfHurigana.setText(hurigana);
		tfMean.setText(mean);
	}//viewData end
	
	private void createUI(){
		this.setTitle("단어정보");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2.0;
		gbc.weighty = 2.0;
		
		//단어
		JLabel bWord = new JLabel("단어");
		tfWord = new JTextField(20);
		//그리드백에 붙이기
		gbAdd(bWord,0,0,1,1);
		gbAdd(tfWord, 1,0,1,1);
		
		//후리가나
		JLabel bHuri = new JLabel("후리가나");
		tfHurigana = new JTextField(30);
		gbAdd(bHuri, 0, 1, 1, 1);
		gbAdd(tfHurigana, 1, 1, 1, 1);
	
		//뜻
		JLabel bMean = new JLabel("뜻");
		tfMean = new JTextField(30);
		gbAdd(bMean, 0, 2, 1, 1);
		gbAdd(tfMean, 1, 2, 1, 1);
		
		//버튼
		JPanel pButton = new JPanel();
		btnInsert = new JButton("추가");
		btnUpdate = new JButton("수정");
		btnDelete = new JButton("삭제");
		btnCancel = new JButton("취소");
		pButton.add(btnInsert);
		pButton.add(btnUpdate);
		pButton.add(btnDelete);
		pButton.add(btnCancel);
		
		gbAdd(pButton, 0, 10, 4, 1);
		
		//버튼 마우스엑션리스너 등록
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCancel.addActionListener(this);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}//createUI() end
	
/**그리드백레이아웃에 붙이는 메소드*/
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
			System.out.println("insertWord()호출 종료");
		}else if(ae.getSource()==btnCancel) {
			this.dispose();//창닫기(현재창만 닫힘)
		}else if(ae.getSource()==btnUpdate) {
			UpdateWord();
		}else if(ae.getSource()==btnDelete) {
			int x = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_CANCEL_OPTION);
			if(x == JOptionPane.OK_OPTION) {
				deleteWord();
			}else {
				JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다");
			}
		}
		
		//jTable 내용 갱신 메소드 호출
		wList.jTableRefresh();
	}//actionPerformed() end
	
	private void deleteWord() {
		String word = tfWord.getText();

		WordDAO dao = new WordDAO();
		boolean ok = dao.deleteWord(word);
		
		if(ok){
            JOptionPane.showMessageDialog(this, "삭제완료");
            dispose();         
           
        }else{
            JOptionPane.showMessageDialog(this, "삭제실패");
           
        }
	}//deleteWord end
	
	private void UpdateWord() {  
        //1. 화면의 정보를 얻는다.
        WordDTO dto = getViewData();     
        //2. 그정보로 DB를 수정
        WordDAO dao = new WordDAO();
        boolean ok = dao.updateWord(dto);
       
        if(ok){
            JOptionPane.showMessageDialog(this, "수정되었습니다.");
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "수정실패: 값을 확인하세요");   
        }
    }//UpdeteWord end
	 
    private void insertWord(){   
        //화면에서 사용자가 입력한 내용을 얻는다.
    	WordDTO dto = getViewData();
    	WordDAO dao = new WordDAO();       
        boolean ok = dao.insertWord(dto);
       
        if(ok){
            JOptionPane.showMessageDialog(this, "추가가 완료되었습니다.");
            dispose();
           
        }else{	           
            JOptionPane.showMessageDialog(this, "추가가 정상적으로 처리되지 않았습니다.");
        }
    }//insertWord end
    
    public WordDTO getViewData() {
    	//화면에서 사용자가 입력한 내용을 얻는다.
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
