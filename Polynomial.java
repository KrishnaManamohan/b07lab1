public class Polynomial {
	
	/************************************************
	 * Fields:
	 * double [] coefficients: array of coefficients
	 ***********************************************/
    double [] coefficients = null;
    
    /************************************************
	 * Constructor
	 * base case constructor
	 ***********************************************/
    public Polynomial (){
    	this.coefficients = new double [0];
    }
    
    /************************************************
   	 * Constructor
   	 * initialize coefficient array 
   	 ***********************************************/
    public Polynomial (double coefficents[]){
        this.coefficients = coefficents;
    }
    
    /************************************************
   	 * method
   	 * adds coefficients of reference variable and input 
   	 ***********************************************/
    public Polynomial add(Polynomial p){
    	double [] larger = this.coefficients;
    	double [] smaller = p.coefficients;
    	
    	if (p.coefficients.length >= this.coefficients.length) {
    		larger = p.coefficients;
    		smaller = this.coefficients;
    	}
    	
    	for (int i = 0; i < smaller.length; i++) {
    		larger[i] = larger[i] + smaller[i];
    	}
    	
    	Polynomial result = new Polynomial(larger);
    	return result;
    }
    
    /************************************************
   	 * method
   	 * evaluates polynomial at x=d
   	 ***********************************************/
    public double evaluate(double d){
    	double sum = 0;
    	
    	for(int i = 0; i < this.coefficients.length; i++){
    		sum += this.coefficients[i]*(Math.pow(d, i));
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
}