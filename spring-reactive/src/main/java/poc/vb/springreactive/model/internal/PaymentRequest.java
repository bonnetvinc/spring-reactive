package poc.vb.springreactive.model.internal;


public class PaymentRequest {

	private String id;
	
	private String amount;
	
	private String status;
	
	private String responseMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaymentRequest [id=" + id + ", amount=" + amount + "]";
	}

	public String getStatus() {
		return status;
	}

	
    public PaymentRequest updateStatus(String status) {
        this.status = status;
        return this;
    }

	public String getResponseMessage() {
		return responseMessage;
	}

	public PaymentRequest setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
		return this;
	}
	
    
	
	
}
