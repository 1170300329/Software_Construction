package src.exception;

public class InvalidCmdException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidCmdException(String s) {
		super(s);
	}
}
