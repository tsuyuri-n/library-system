package library.bean;

import java.sql.Date;

public class MemberBean {

	//会員ID
	//会員を一意に特定する
	private int memberId;

	//氏名
	private String name;

	//住所
	private String address;

	//電話番号
	//会員を一意に特定する︖
	private String tel;

	//メールアドレス
	private String email;

	//生年月日
	private Date birth;

	//入会年月日
	private Date joined;

	//退会年月日
	//nullなら現在も会員
	private Date out;

	//コンストラクタ
	//全フィールドを初期化するコンストラクタを作成する
	public MemberBean(int memberId, String name, String address, String tel, String email, Date birth, Date joined,
			Date out) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birth = birth;
		this.joined = joined;
		this.out = out;
	}

	//メソッド
	//全フィールドのゲッターとセッターを作成する
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Date getJoined() {
		return joined;
	}

	public void setJoined(Date joined) {
		this.joined = joined;
	}

	public Date getOut() {
		return out;
	}

	public void setOut(Date out) {
		this.out = out;
	}

	//toString生成
	@Override
	public String toString() {
		return "MemberBean [memberId=" + memberId + ", name=" + name + ", address=" + address + ", tel=" + tel
				+ ", email=" + email + ", birth=" + birth + ", joined=" + joined + ", out=" + out + "]";
	}
}
