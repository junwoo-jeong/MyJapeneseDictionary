package japaneseDictionary_version_0_2;

public class WordDTO {
	private String word;
	private String hurigana;
	private String mean;
	
	public WordDTO() {}
/**»ý¼ºÀÚ*/
	public WordDTO(String word,String hurigana,String mean) {
		this.word = word;
		this.hurigana = hurigana;
		this.mean = mean;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getHurigana() {
		return hurigana;
	}
	public void setHurigana(String hurigana) {
		this.hurigana = hurigana;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[word="+word+", hurigana="+hurigana+", hurigana="+mean+"]";
	}
	
}
