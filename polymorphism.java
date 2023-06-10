class student{
    String name;
    int age;
    public void print(String name){
        System.out.println(name);
    }

    public void print(int age){
        System.out.println(age);
    }

    public void print(String name,int age){
        System.out.println(name+ "" +age);
    }
}
public class polymorphism {
    /**
     * @param args
     */
    public static void main(String args[]){
    student s1 = new student();
    s1.name = "Ved";
    s1.print(s1.name);
 }
}