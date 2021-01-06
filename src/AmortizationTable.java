import java.util.Scanner;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
public class AmortizationTable {

  double calculateMonthlyPayment(LoanDetails details) {

    double monthlyPayment = details.getLoanAmount() * (Math.pow((1+details.getInterest()),details.getTerm())*details.getInterest())/((Math.pow((1+details.getInterest()),details.getTerm()))-1);
    return monthlyPayment;
  };

  void displayAmortizationSchedule(LoanDetails details) {
    double monthlyPayment = calculateMonthlyPayment(details);
    double interestPaid  = details.getLoanAmount() * details.getInterest();
    double principal = monthlyPayment - interestPaid;
    double startBalance = details.getLoanAmount();
    double endBalance = startBalance - principal;
    System.out.format("\n%10s %12s %10s %10s %10s %12s\n","Date", "Start Balance", "Principal", "Interest", "Total Pmt", "Balance");

    for (int month = 1; month <= details.getTerm(); month++) {
      System.out.format("%8d %12.2f %10.2f %10.2f %10.2f %12.2f\n", month, startBalance, principal, interestPaid, monthlyPayment, endBalance);
      startBalance = endBalance;
      interestPaid  = startBalance * details.getInterest();
      principal = monthlyPayment - interestPaid;
      endBalance = endBalance - principal;
    }
  };

  public static void main(String[] args) throws Exception {

    AmortizationTable amortizationTable = new AmortizationTable();
    LoanDetails details = new LoanDetails();
    Scanner input = new Scanner(System.in);
    boolean userInput = true;
    do {
      try {
        // Read the details from the user
        System.out.println("Enter the Loan Amount: ");
        details.setLoanAmount(input.nextDouble());
    
        System.out.println("Enter the term in months: ");
        details.setTerm(input.nextInt());
    
        System.out.println("Enter the Interest Rate: ");
        details.setInterest(input.nextDouble() * .01 / 12);
    
        // System.out.println("Enter the requested date in (DD-MM-YYYY):");
        // String date = input.next();
        // SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        // details.requestDate = dateFormat.parse(date);

        // Call the function to display the Amortization Schedule
        amortizationTable.displayAmortizationSchedule(details);

        userInput = false;

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
    } while(userInput);
    input.close();
  }
}
