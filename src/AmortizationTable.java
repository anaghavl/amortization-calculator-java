import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
public class AmortizationTable {

  // Calculate the monthly payment
  double calculateMonthlyPayment(LoanDetails details) {
    double monthlyPayment = details.getLoanAmount() * (Math.pow((1+details.getInterest()),details.getTerm())*details.getInterest())/((Math.pow((1+details.getInterest()),details.getTerm()))-1);
    return monthlyPayment;
  };

  // Calculate the first date of the next month from requested date
  Date calculatePaymentStartDate(Date date) {
    Calendar calendar = Calendar.getInstance();  
    calendar.setTime(date);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.MONTH, 1);
    return calendar.getTime();
  };

  void displayAmortizationSchedule(LoanDetails details) {
    // Calculate content for the first row
    double monthlyPayment = calculateMonthlyPayment(details);

    double interestPaid  = details.getLoanAmount() * details.getInterest();
    double principal = monthlyPayment - interestPaid;

    double startBalance = details.getLoanAmount();
    double endBalance = startBalance - principal;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(calculatePaymentStartDate(details.getRequestDate()));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    // Display header of the table
    System.out.format("\n%10s %12s %10s %10s %10s %12s\n","Date", "Start Balance", "Principal", "Interest", "Total Pmt", "Balance");

    for (int month = 1; month <= details.getTerm(); month++) {
      // Display the content of the table
      System.out.format("%10s %12.2f %10.2f %10.2f %10.2f %12.2f\n", dateFormat.format(calendar.getTime()), startBalance, principal, interestPaid, monthlyPayment, endBalance);

      // Recalculate the content for next row of the iteration
      calendar.add(Calendar.MONTH, 1);
      startBalance = endBalance;
      interestPaid  = startBalance * details.getInterest();
      principal = monthlyPayment - interestPaid;
      endBalance = endBalance - principal;
    }
  };

  void clearScreen() {
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  };

  public static void main(String[] args) throws Exception {

    AmortizationTable amortizationTable = new AmortizationTable();

    // Create object to store and access loan details
    LoanDetails details = new LoanDetails();

    // Create scanner object to take user input
    Scanner input = new Scanner(System.in);

    boolean runProgram = true, userInput = true;

    while(runProgram) {
      System.out.printf("\n Press 1 to display Amortization Schedule");
      System.out.printf("\n Press 2 to exit");
      System.out.printf("\n Enter choice: ");
      int choice = input.nextInt();

      switch(choice) {
        case 1: do {
                  try {
                    amortizationTable.clearScreen();

                    // Read the details from the user
                    System.out.printf("\n Enter the Loan Amount: ");
                    details.setLoanAmount(input.nextDouble());
                
                    System.out.printf("\n Enter the term in months: ");
                    details.setTerm(input.nextInt());
                
                    System.out.printf("\n Enter the Interest Rate: ");
                    details.setInterest(input.nextDouble() * .01 / 12);
                
                    System.out.printf("\n Enter the requested date in (DD-MM-YYYY):");
                    String reqDate = input.next();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = dateFormat.parse(reqDate);
                    details.setRequestDate(date);
            
                    // Call the function to display the Amortization Schedule
                    amortizationTable.displayAmortizationSchedule(details);
            
                    userInput = false;
            
                  } catch (InputMismatchException e) {
                      amortizationTable.clearScreen();
                      System.out.println("Invalid entry! \nPlease enter the correct details.");
                      input.next();
                  } catch (ParseException e) {
                      amortizationTable.clearScreen();
                      System.out.println("Invalid entry! \nPlease enter the date in the format (DD-MM-YYYY)");
                      input.next();
                  }
                } while(userInput);
                break;

        case 2: System.exit(0);

        default: amortizationTable.clearScreen();
                 System.out.printf("\n Wrong choice, please enter a valid number!\n");
      }  
    };
    
    input.close();
  }
}
