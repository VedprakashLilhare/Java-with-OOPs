class shape{
    public void area(){
        System.out.println("Displays area");
    }
}

class Circle extends shape {

    public void area(int l){
    System.out.print(3.14*l*l);
    }
}

class Triangle extends shape{
    public void area(int l,int h){
    System.out.println(1/2*l*h);
    }
}

public class Inheritence {
    public static void main(String arg[]){
        Circle a = new Circle();
        a.area();
        a.area(5);

    }
    
}
