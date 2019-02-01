package xyz.markpost.bankdemo.model;

public enum TransactionType {
  CREDIT("credit"),
  DEBIT("debit");

  private final String text;

  /**
   * @param text
   */
  TransactionType(final String text) {
    this.text = text;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return text;
  }
}
