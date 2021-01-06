import java.util.Scanner;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
public class AmortizationTable {

  double calculateMonthlyPayment(LoanDetails details) {
    double a = details.loanAmount;
    int n = details.term;
    double i = calculateInterest(details);
    double monthlyPayment = a*(Math.pow((1+i),n)*i)/((Math.pow((1+i),n))-1);
    System.out.println(monthlyPayment);
    return monthlyPayment;
  };

  void displayAmortizationSchedule(LoanDetails details) {
    double monthlyPayment = calculateMonthlyPayment(details);
    double monthlyInterestRate = details.interest/12;
    double interestPaid  = details.loanAmount * (monthlyInterestRate / 100);
    double principalPaid = monthlyPayment - interestPaid;
    double newBalance    = details.loanAmount;

    for (int month = 1; month <= details.term; month++) {
      System.out.format("%8d %10.2f %10.2f %10.2f %12.2f\n", month, interestPaid, principalPaid, monthlyPayment, newBalance);
      newBalance    = newBalance - principalPaid;
      interestPaid  = newBalance * (monthlyInterestRate/100);
      principalPaid = monthlyPayment - interestPaid;
    }
  };

  double calculateInterest( LoanDetails details ) {
    double interest = (details.interest *.01)/12;
    return interest;
  };

  public static void main(String[] args) throws Exception {

    LoanDetails details = new LoanDetails();
    AmortizationTable aTable = new AmortizationTable();

    Scanner input = new Scanner(System.in);
    boolean bError = true;
    do {
      try {
        System.out.println("Enter the Loan Amount: ");
        details.loanAmount = input.nextDouble();
    
        System.out.println("Enter the term in months: ");
        details.term = input.nextInt();
    
        System.out.println("Enter the Interest Rate: ");
        details.interest = input.nextFloat();
    
        // System.out.println("Enter the requested date in (DD-MM-YYYY):");
        // String date = input.next();
        // SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        // details.requestDate = dateFormat.parse(date);

        aTable.displayAmortizationSchedule(details);

        bError = false;

      } catch (InputMismatchException e) {
          System.out.print("\033[H\033[2J");  
          System.out.flush();  
          System.out.println( e + "Invalid entry! \nPlease enter the correct details.");
          input.next();
      } 
      // catch ( ParseException e ) {
      //     System.out.print("\033[H\033[2J");  
      //     System.out.flush();  
      //     System.out.println( e + "Invalid entry! \nPlease enter the date in the format (DD-MM-YYYY)");
      //     input.next();
      // }
    } while(bError);
    input.close();
  }
}
