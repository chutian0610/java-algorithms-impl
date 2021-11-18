package info.victorchu.utils;

import java.util.function.Predicate;

public interface Predicates {
    Predicate<String> stringNotBlank = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return s != null && s.trim().length() <= 0;
        }
    };
    Predicate<Integer> integerGT0 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer integer) {
            if(integer>0){
                return true;
            }
            return false;
        }
    };
    Predicate<Integer> integerGTE0 = new Predicate<Integer>() {
        @Override
        public boolean test(Integer integer) {
            if(integer>=0){
                return true;
            }
            return false;
        }
    };
}
