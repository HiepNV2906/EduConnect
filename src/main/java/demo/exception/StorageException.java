package demo.exception;

public class StorageException extends Exception{

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Exception e) {
		super(message);
	}

}
