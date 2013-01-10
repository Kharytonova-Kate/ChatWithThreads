
public class MyOutput {
public static void show (Object o) {
	if (o.getClass()==String.class) {
		System.out.println (o);
	}
	else {
		System.out.println("Received object is not a String");
	}
}
}
