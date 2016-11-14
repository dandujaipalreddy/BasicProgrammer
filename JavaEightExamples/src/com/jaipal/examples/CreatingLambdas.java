package com.jaipal.examples;

public class CreatingLambdas {

    interface Greeting{
        public String sayHello(String s);
    }

    public void testHello(String s,Greeting g){
        System.out.println(g.sayHello(s));
    }

    public static void main(String[] args) {

        new CreatingLambdas().testHello("Jaipal",(String s)->"Hello "+s);
        new CreatingLambdas().testHello("Jaipal",(String s)-> !s.isEmpty()?"Hii "+s:"Something Went Wrong");
        new CreatingLambdas().testHello("",(String s)-> !s.isEmpty()?"Hello "+s:"Something Went Wrong!!");

        Runnable r=()->{
          Person a=new Person();

        };



    }
}
