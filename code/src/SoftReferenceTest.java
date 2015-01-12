import java.lang.ref.SoftReference;

public class SoftReferenceTest {

    /**
     * @param args
     */
	public static class A{
		public String str;
		public A() {
			// TODO Auto-generated constructor stub
		}
	}
    public static void main(String[] args) {
        A a = new A();
        a.str = "Hello, reference";
        SoftReference<A> sr = new SoftReference<A>(a);
        a = null;
        int i = 0;
        while (sr.get() != null) {
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", sr.get().str, ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("object a was cleared by JVM!");
    }

}