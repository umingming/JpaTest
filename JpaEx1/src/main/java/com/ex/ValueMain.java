package com.ex;

public class ValueMain {
    public static void main(String[] args) {
        Integer c = new Integer(10);
        Integer d = new Integer(10);

        //c의 값을 변경할 수 있는 방법이 없음. c = ? 로 변경되는디?
        int a = 10;
        int b = 10;
        System.out.println(a == b); //true
        System.out.println(c == d); //false
        System.out.println(c.equals(d));
//        System.out.println(a + "," + b); //a만 20으로 바뀜. 항상 값을 복사하기 때문임.
//        System.out.println(c + "," + d); //a만 20으로 바뀜. 항상 값을 복사하기 때문임.
    }
}
