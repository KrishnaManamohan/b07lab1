import java.io.File;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,-2,5};
		int [] e1 = {0,1,3};
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {-2,-9};
		int [] e2 = {1,4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);
		Polynomial r = p1.multiply(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		System.out.println("r(0.1) = " + r.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		if(r.hasRoot(0))
			System.out.println("0 is a root of r");
		else
			System.out.println("0 is not a root of r");
		
		File file = new File("polynomial.txt");
		Polynomial p3 = new Polynomial(file);
		System.out.println("p3(0.1) = " + p3.evaluate(0.1));
		
		for (int i =0; i < p3.exponents.length; i++) {
			System.out.println("Exponents " + p3.exponents[i]);
			System.out.println("Coefficents " + p3.coefficients[i]);
		}
		
		p3.saveToFile("output.txt");

	}
}