import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Polynomial {
	
	/************************************************
	 * Fields:
	 * double [] coefficients: array of coefficients (non-zero)
	 * int [] exponents: array of exponents
	 ***********************************************/
    double [] coefficients = null;
    int [] exponents = null;
    
    /************************************************
	 * Constructor
	 * base case constructor
	 ***********************************************/
    public Polynomial (){
    }
    
    /************************************************
   	 * Constructor
   	 * initialize coefficient array 
   	 ***********************************************/
    public Polynomial (double coefficents[], int exponents[]){
        this.coefficients = coefficents;
        this.exponents = exponents;
    }
    
    /************************************************
   	 * Constructor
   	 * initialize coefficient array based on file
   	 ***********************************************/
    public Polynomial (File p) {
		BufferedReader input = null;
		String line = null;
		
		
		if (p.exists() && p.isFile()) {
			try {
				input = new BufferedReader(new FileReader(p));
			} 
			catch (FileNotFoundException e) {
			}
			try {
				line = input.readLine();
				input.close();
			} 
			catch (IOException e) {
			}
		}
		
		 
		String[] terms = line.split("(?=[+-])");
		this.coefficients = new double [terms.length];
		this.exponents = new int [terms.length];
		String[]singleTerm;
		
		for (int i = 0; i < terms.length; i++) {
			if (!terms[i].contains("x")) { //"a" from a+bxc+dxe...
				terms[i] = terms[i] + "x0";
			}
			if (terms[i].endsWith("x")) { //"ax" from b+ax...
				terms[i] = terms[i] + "1";
			}
			if (terms[i].startsWith("x")) { //"ax" from b+ax...
				terms[i] = "1" + terms[i];
			}
			if (terms[i].startsWith("-x")) { //"-xa" from b-xa...
				terms[i] = "-1" + terms[i].substring(1);
			}
			if (terms[i].startsWith("+x") ) { //"xa" or "+xa" from xa+b or b+xa...
				terms[i] = "1" + terms[i].substring(1);
			}
			singleTerm = terms[i].split("[x]");
			this.coefficients[i] = Double.parseDouble(singleTerm[0]);
			this.exponents[i] = Integer.parseInt(singleTerm[1]);
		}
    }
    
    /************************************************
   	 * method
   	 * adds coefficients of reference variable and input 
   	 ***********************************************/
    public Polynomial add(Polynomial p){
    	Polynomial result = new Polynomial();
    	if (zeroPolynomial() && p.zeroPolynomial()) {
    		return result;
    	}
    	if (zeroPolynomial()) {
    		result.coefficients = p.coefficients;
    		result.exponents = p.exponents;
    	}
    	if (p.zeroPolynomial()) {
    		result.coefficients = this.coefficients;
    		result.exponents = this.exponents;
    	}
    	
    	ArrayList<Double> coefficients = new ArrayList<>();
    	ArrayList<Integer> exponents = new ArrayList<>();
    	for (int i = 0; i < this.exponents.length; i++) {
    		coefficients.add(this.coefficients[i]);
    		exponents.add(this.exponents[i]);
    	}
  
    	for (int i = 0; i < p.exponents.length; i++) {
    		int index = exponents.indexOf(p.exponents[i]);
    		if (index == -1) {
    			exponents.add(p.exponents[i]);
    			coefficients.add(p.coefficients[i]);
    		}
    		else {
    			coefficients.set(index, coefficients.get(index) + p.coefficients[i]);
    		}
    	}
    	
    	result.coefficients = new double[coefficients.size()];
    	result.exponents = new int[exponents.size()];
    	for (int i = 0; i < exponents.size(); i++) {
    		result.exponents[i] = exponents.get(i);
    		result.coefficients[i] = coefficients.get(i);
    	}
    	return result;
    }
    
    /************************************************
   	 * method
   	 * multiplies polynomials of reference variable and input 
   	 ***********************************************/
    public Polynomial multiply(Polynomial p) {
    	Polynomial result = new Polynomial();
    	if (zeroPolynomial() || p.zeroPolynomial()) {
    		return result;
    	}
    	
    	ArrayList<Double> coefficients = new ArrayList<>();
    	ArrayList<Integer> exponents = new ArrayList<>();
    	
    	for (int i = 0; i < p.exponents.length; i++) {
    		for (int j = 0; j < this.exponents.length; j++) {
    			int index = exponents.indexOf(p.exponents[i] + this.exponents[j]);
    			if (index == -1) {
        			exponents.add(p.exponents[i] + this.exponents[j]);
        			coefficients.add(p.coefficients[i] * this.coefficients[j]);
        		}
        		else {
        			coefficients.set(index, coefficients.get(index) + (p.coefficients[i] * this.coefficients[j]));
        		}
    		}
    	}
    	
    	result.coefficients = new double[coefficients.size()];
    	result.exponents = new int[exponents.size()];
    	for (int i = 0; i < exponents.size(); i++) {
    		result.exponents[i] = exponents.get(i);
    		result.coefficients[i] = coefficients.get(i);
    	}
    	return result;
    }
    
    /************************************************
   	 * method
   	 * evaluates polynomial at x=d
   	 ***********************************************/
    public double evaluate(double d){
    	if (zeroPolynomial()) {
    		return 0;
    	}
    	
    	double sum = 0;
    	for(int i = 0; i < this.exponents.length; i++){
    		sum += this.coefficients[i]*(Math.pow(d, this.exponents[i]));
    	}
    	return sum;
    }
    
    /************************************************
   	 * method
   	 * checks if polynomial at x=d == 0
   	 ***********************************************/
    public boolean hasRoot (double d){
    	return (this.evaluate(d) == 0);
    }
    
    /************************************************
   	 * method
   	 * checks if the calling object is the zero Polynomial (when either field is null)
   	 ***********************************************/
    public boolean zeroPolynomial() {
    	return this.coefficients == null || this.exponents == null;
    }
    
    public void saveToFile (String filename) {
    	FileWriter output = null;
    	try {
			output = new FileWriter(filename, false);
		} 
    	catch (IOException e) {
		}
    	
    	String line = "";
    	for(int i=0; i < this.exponents.length; i++) {
    		if (this.exponents[i] == 0) {
    			line = line + this.coefficients[i];
    		}
    		else if (this.coefficients[i] < 0) {
    			line = line + this.coefficients[i] + "x" + this.exponents[i];
    		}
    		else {
    			line = line + "+" + this.coefficients[i] + "x" + this.exponents[i];
    		}
    		
    	}
    	try {
			output.write(line);
			output.close();
		} 
    	catch (IOException e) {
		}	
    }
}