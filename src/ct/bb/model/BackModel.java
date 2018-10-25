package ct.bb.model;

public class BackModel extends BaseModel{
	
	private int code=-1;
	private String result;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result==null?"参与完毕":result;
	}
	
	
}
