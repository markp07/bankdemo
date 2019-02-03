package xyz.markpost.bankdemo.model;

public enum TransactionType {
  DEPOSIT("deposit"),
  WITHDRAWAL("withdrawal");

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
