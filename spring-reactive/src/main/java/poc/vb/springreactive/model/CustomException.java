package poc.vb.springreactive.model;

import java.util.List;

public class CustomException extends Throwable {
	public String message;

	@Override
	public String getMessage() {
		return message;
	}
	
	public CustomException(Throwable e) {
		this.message = e.getMessage();
	}

}