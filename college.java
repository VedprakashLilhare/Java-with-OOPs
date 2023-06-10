class student 
{
    String name;
    String branch;
    String subject;

    public void write()
    {
        System.out.println("Student Details");
    }

    public void print(){
        System.out.println(this.name);
    }
}

public class college
{ 
    /**
     * @param args
     */
    public static void main(String args[])
    {
        student candidate = new student();
        candidate.name = "Vedprakash Lilhare";
        candidate.branch = "CSE";
        candidate.subject = "JAVA";

        student c2 = new student();
        c2.name = "Ved";
        c2.branch = "ICB";
        candidate.print();
        c2.print();
       
    }
}