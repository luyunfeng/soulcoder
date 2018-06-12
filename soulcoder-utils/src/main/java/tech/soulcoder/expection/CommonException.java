package tech.soulcoder.expection;

public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 5719639814985692478L;
    private ReturnCodeModel exception_type;

    public CommonException(ReturnCodeModel type) {
        super(type.getMsg());
        this.exception_type = type;
    }

    public String getErrorCode() {
        return this.exception_type.getCode();
    }

    public String getErrorDeclare() {
        return this.exception_type.getMsg();
    }

    public ReturnCodeModel getException_type() {
        return this.exception_type;
    }
}
