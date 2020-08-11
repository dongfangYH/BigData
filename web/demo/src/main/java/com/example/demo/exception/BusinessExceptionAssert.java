package com.example.demo.exception;

public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException(Object... args){
        return new BusinessException(this, args, this.getMessage());
    }

    @Override
    default BaseException newException(Throwable t, Object... args){
        return new BusinessException(this, args, this.getMessage(), t);
    }

    @Override
    default BaseException newException(String message, Object... args){
        return new BusinessException(this, args, message);
    }
}
