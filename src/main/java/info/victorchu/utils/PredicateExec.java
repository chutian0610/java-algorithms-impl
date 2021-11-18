package info.victorchu.utils;

import java.util.function.Predicate;

public class PredicateExec {
    public static <T> T check(Predicate<T> predicate,T target,String message){
        if(predicate.test(target)){
            return target;
        }
        throw new PredicateExecutionFailedException("Predicate exec fail:"+message);
    }

    public static class PredicateExecutionFailedException extends RuntimeException {
        public PredicateExecutionFailedException() {
        }

        public PredicateExecutionFailedException(String message) {
            super(message);
        }

        public PredicateExecutionFailedException(String message, Throwable cause) {
            super(message, cause);
        }

        public PredicateExecutionFailedException(Throwable cause) {
            super(cause);
        }

        public PredicateExecutionFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
