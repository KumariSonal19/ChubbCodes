// Reusable business-rule exception hierarchy
public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
    public BusinessException(String message, Throwable cause) { super(message, cause); }
}

class DuplicateBookException extends BusinessException {
    public DuplicateBookException(String msg) { super(msg); }
}

class BookNotAvailableException extends BusinessException {
    public BookNotAvailableException(String msg) { super(msg); }
}

class MemberLimitExceededException extends BusinessException {
    public MemberLimitExceededException(String msg) { super(msg); }
}

class StudentNotFoundException extends BusinessException {
    public StudentNotFoundException(String msg) { super(msg); }
}

class InvalidGradeException extends BusinessException {
    public InvalidGradeException(String msg) { super(msg); }
}
