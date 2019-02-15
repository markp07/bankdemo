package xyz.markpost.bankdemo.model;

/**
 * Enum of transaction types
 */
public enum TransactionType {
  DEPOSIT("deposit"),
  WITHDRAWAL("withdrawal");

  private final String text;

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
