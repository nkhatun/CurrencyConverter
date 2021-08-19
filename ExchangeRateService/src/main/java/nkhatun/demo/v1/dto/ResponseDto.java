package nkhatun.demo.v1.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable{
	private static final long serialVersionUID = 1L;
		String status ;
	    DataDto data ;
	    String message;
	    public ResponseDto() {super();}
	    public ResponseDto(String status, DataDto data, String message ) {
			this.status = status;
			this.data = data;
			this.message = message;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public DataDto getData() {
			return data;
		}
		public void setData(DataDto data) {
			this.data = data;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	    
	    
}
