package library.bean;

public class CategoryCodeBean {

	private int categoryCode;
	private String categoryName;


	public CategoryCodeBean() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}



	public CategoryCodeBean(int categoryCode, String categoryName) {
		super();
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}



	public int getCategoryCode() {
		return categoryCode;
	}



	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}



	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	@Override
	public String toString() {
		return "CategoryCodeBean [categoryCode=" + categoryCode + ", categoryName=" + categoryName + "]\n";
	}

}
