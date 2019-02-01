package xyz.markpost.bankdemo.model;

public enum AccountType {
  CHECKING("checking"),
  SAVING("saving");

  private final String text;

  /**
   * @param text
   */
  AccountType(final String text) {
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