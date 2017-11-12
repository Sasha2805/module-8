package library;

public class Library {
    private String name;
    private int maxAmount;

    public Library(String name, int maxAmount) {
        this.name = name;
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name = '" + name + '\'' +
                ", maxAmount = " + maxAmount +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

}
