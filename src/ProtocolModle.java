
/**
 * socket 传输字符串内容协议
 * androidId||message
 * @author chenhewen
 *
 */
public class ProtocolModle {
	
	public static final String DIVIDER = ":";
	
	private String mFromAndroidId;
	
	private String mToAndroidId;
	
	private String mMessage;
	
	public ProtocolModle(String protocolString) {
		String[] protocolStr = protocolString.split(DIVIDER);
		
		//TODO 需要进行数组越界保护
		mFromAndroidId = protocolStr[0];
		mToAndroidId = protocolStr[1];
		mMessage = protocolStr[2];
	}
	
	@Override
	public String toString() {
		return mFromAndroidId + DIVIDER + mToAndroidId + DIVIDER + mMessage;
	}
	
	@Override
	public int hashCode() {
		return (mFromAndroidId + mMessage + System.currentTimeMillis()).hashCode();
	}

	
	
	
	
	
	public String getFromAndroidId() {
		return mFromAndroidId;
	}

	public void setFromAndroidId(String mFromAndroidId) {
		this.mFromAndroidId = mFromAndroidId;
	}

	public String getToAndroidId() {
		return mToAndroidId;
	}

	public void setToAndroidId(String mToAndroidId) {
		this.mToAndroidId = mToAndroidId;
	}

	public String getessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public static String getDivider() {
		return DIVIDER;
	}
	
	
}
