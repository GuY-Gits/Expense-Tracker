public class Expense {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() { return date; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "Date: " + date + " | Category: " + category + " | Amount: Rs." + amount;
    }

    public String toFileFormat() {
        return date + "," + category + "," + amount;
    }
}