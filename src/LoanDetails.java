import java.util.Date;
public class LoanDetails {
  private Double interest;
  private Double loanAmount;
  private Integer term;
  private Date requestDate;

  public Double getInterest() {
    return interest;
  }

  public Double getLoanAmount() {
    return loanAmount;
  }

  public Integer getTerm() {
    return term;
  }

  public Date getRequestDate() {
    return requestDate;
  }

  public void setInterest(Double interestInput) {
    this.interest = interestInput;
  }

  public void setLoanAmount(Double loanAmountInput) {
    this.loanAmount = loanAmountInput;
  }

  public void setTerm(Integer termInput) {
    this.term = termInput;
  }

  public void setRequestDate(Date requestDateInput) {
    this.requestDate = requestDateInput;
  }
}