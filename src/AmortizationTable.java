import java.util.Scanner;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
public class AmortizationTable {

  double calculateMonthlyPayment(LoanDetails details) {
    double interest = (details.interest *.01)/12;
    double monthlyPayment = details.loanAmount * (Math.pow((1+interest),details.term)*interest)/((Math.pow((1+interest),details.term))-1);
    return monthlyPayment;
  };

  void displayAmortizationSchedule(LoanDetails details) {
    double monthlyPayment = calculateMonthlyPayment(details);
    double monthlyInterestRate = details.interest/12;
    double interestPaid  = details.loanAmount * (monthlyInterestRate / 100);
    double principal = monthlyPayment - interestPaid;
    double startBalance = details.loanAmount;
    double endBalance = startBalance - principal;
    System.out.format("\n%8s %12s %10s %10s %10s %12s\n","Date", "Start Balance", "Principal", "Interest", "Total Pmt", "Balance");

    for (int month = 1; month <= details.term; month++) {
      System.out.format("%8d %12.2f %10.2f %10.2f %10.2f %12.2f\n", month, startBalance, principal, interestPaid, monthlyPayment, endBalance);
      startBalance = endBalance;
      endBalance = endBalance - principal;
      interestPaid  = startBalance * (monthlyInterestRate/100);
      principal = monthlyPayment - interestPaid;
    }
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
        details.interest = input.nextDouble();
    
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
