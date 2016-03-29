/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 1/6/14
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String args[]){

        /*int Start =0;
        int Two =1;

        int Next=Two;
        int tmp,count;
        System.out.println("1--"+Start);
        System.out.println("2--"+Two);

        for(count=3;count<=10;count++){
            tmp=Next;
            Next=Next+Start;
            Start=tmp;
           System.out.println(count+"--"+Next);
        }
        System.out.println(count-1+"nth fibonacci is "+Next);
    */
        int n=4;
        if((n > 0) && ((n & (n - 1)) == 0)){
           System.out.println(n+" is power of two");
        }
        else{
            System.out.println(n+" is not power of two");
        }

        //System.out.println(Math.pow(2,-8));
    }
}
