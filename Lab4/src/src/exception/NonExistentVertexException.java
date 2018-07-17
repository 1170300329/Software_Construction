package src.exception;

public class NonExistentVertexException extends Exception{
	private static final long serialVersionUID = 1L;
	public  NonExistentVertexException (String s) {
		super(s);
	}
}
