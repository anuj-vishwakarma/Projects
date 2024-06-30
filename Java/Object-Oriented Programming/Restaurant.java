import java.util.ArrayList;
import java.util.Scanner;

class Restaurant {
    static int orderNumber = 1;    private String restaurantName;
    private String restaurantLocation;
    protected ArrayList<Menu> items = new ArrayList<>();
    protected static ArrayList<Customer> customers = new ArrayList<>();
    private final ArrayList<Rating> ratings = new ArrayList<>();

    public Restaurant() {}

    public Restaurant(String restaurantName, String restaurantLocation) {
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void giveRating() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String customerName = input.nextLine();
            System.out.println("Enter the number of stars(1 to 5) you want to give us: ");
            int stars = input.nextInt();
            while (stars < 1 || stars > 5) {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                stars = input.nextInt();
            }
            input.nextLine();
            System.out.println("Please type your thoughts about us: ");
            String feedback = input.nextLine();

            Customer customer = findCustomerByName(customerName);

            if (customer != null && customer.hasOrdered()) {
                ratings.add(new Rating(stars, customerName, feedback));
                System.out.println("\nRating added for customer: " + customerName);
            } else {
                System.out.println("Customer " + customerName + " has not placed any orders and cannot give a rating.");
            }
        } catch (Exception e) {
            System.out.println("Some error occurred, please enter valid input, and if issue persists try restarting the program!");
        }
    }

    public void getRating() {
        if (!restaurantName.toLowerCase().contains("the")) {
            System.out.println("\nRatings of the \"" + restaurantName + "\" restaurant\n");
        } else {
            System.out.println("Ratings of \"" + restaurantName + "\" restaurant\n");
        }

        if (ratings.isEmpty()) {
            System.out.println("No ratings yet.");
        } else {
            for (Rating rating : ratings) {
                System.out.println("Customer: " + rating.getCustomerName() + "\n" +
                        rating.getFeedback() + "\n" +
                        "Rating: " + rating.getRatingByCustomer()
                );
            }
            int avgRating = 0;
            for (Rating rating : ratings) {
                avgRating += rating.getRatingByCustomer();
            }
            System.out.println("\nAverage Rating : " + ((float) avgRating / ratings.size()));
        }
    }

    public void addItems(Menu item) {
        items.add(item);
    }

    public void removeItems(Menu item) {
        items.remove(item);
    }

    public void getMenu() {
        System.out.println("""

                Menu of our restaurant\s

                Dishes\t\t\t\t\t\tPrice
                """);
        int counter = 1;

        for (Menu item : items) {
            System.out.printf("%d.%-15s\t\t\t%s\n\n", counter, item.getDish(), item.getPrice());
            counter++;
        }
    }

    public void getRestaurantInformation() {
        System.out.println("Name: " + this.restaurantName + "\n\n" +
                "Location: " + this.restaurantLocation);
        getMenu();
    }

    private Customer findCustomerByName(String customerName) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        return null;
    }

    public static void goToRestaurant(Customer customer) {
        customers.add(customer);
    }

    static class Menu {
        private final String dish;
        private final String price;

        Menu(String dish, String price) {
            this.dish = dish;
            this.price = price;
        }

        public String getDish() {
            return dish;
        }

        public String getPrice() {
            return price;
        }
    }
}

class Rating {
    private final int stars;
    private final String customer;
    private final String feedback;

    public Rating(int stars, String customer, String feedback) {
        this.stars = stars;
        this.customer = customer;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getCustomerName() {
        return customer;
    }

    public int getRatingByCustomer() {
        return stars;
    }
}

class Customer {
    protected String name;
    protected float total = 0;
    protected boolean hasOrdered = false;
    ArrayList<Restaurant.Menu> orderedItems = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    public Customer(){}
    public Customer(String name) {
        this.name = name;
        Restaurant.customers.add(this);
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }

    public String getName() {
        return name;
    }

    public void order(int noOfPlates, Restaurant.Menu item) {
        this.hasOrdered = true;
        orderedItems.add(item);
        this.total += noOfPlates * Integer.parseInt(item.getPrice().substring(1));
        Order order = new Order(noOfPlates, item);
        orders.add(order);
    }

    static class Order {
        private final int noOfPlates;
        Restaurant.Menu item;

        public Order(int noOfPlates, Restaurant.Menu item) {
            this.noOfPlates = noOfPlates;
            this.item = item;
        }

        public int getNoOfPlates() {
            return noOfPlates;
        }
    }

    public void bill() {
        int total = 0;
        String calculation = "";
        int count = 1;
        char currency = 0;
        try {
            currency = orderedItems.get(0).getPrice().charAt(0);
        } catch (Exception e) {
            System.out.println("Can't generate bill, the customer has not made any order!\n");
            return;
        }

        for (Restaurant.Menu item : orderedItems) {
            total += Integer.parseInt(item.getPrice().substring(1));
        }

        System.out.println("Order Number: " + Restaurant.orderNumber++);
        System.out.println("Items\t\t\t\t\t\tPrice");

        for (Restaurant.Menu item : orderedItems) {
            System.out.printf("%d.%-20s \t\t%s x " + orders.get(count - 1).getNoOfPlates() + "\n", count++, item.getDish(), item.getPrice());
        }
        System.out.println(calculation);
        System.out.println("----------------------------------");
        String x = "Total";
        System.out.printf("%-29s  %c%.2f\n\n", x, currency, this.total);
    }
}

class VIPCustomer extends Customer {
    private static final float DISCOUNT_RATE = 0.15f;

    public VIPCustomer(String name) {
        super(name);
    }

    @Override
    public void order(int noOfPlates, Restaurant.Menu item) {
        this.hasOrdered = true;
        orderedItems.add(item);
        this.total += noOfPlates * Integer.parseInt(item.getPrice().substring(1)) * (1 - DISCOUNT_RATE);
        Order order = new Order(noOfPlates, item);
        orders.add(order);
    }

    @Override
    public void bill() {
        System.out.println("As a VIP customer, you get a special discount of 15%!");

        int originalTotal = 0;
        String calculation = "";
        int count = 1;
        char currency = 0;
        try {
            currency = orderedItems.get(0).getPrice().charAt(0);
        } catch (Exception e) {
            System.out.println("Can't generate bill, the customer has not made any order!\n");
            return;
        }

        for (Restaurant.Menu item : orderedItems) {
            originalTotal += Integer.parseInt(item.getPrice().substring(1)) * orders.get(count - 1).getNoOfPlates();
        }

        System.out.println("Order Number: " + Restaurant.orderNumber++);
        System.out.println("Items\t\t\t\t\t\tPrice");

        for (Restaurant.Menu item : orderedItems) {
            System.out.printf("%d.%-20s \t\t%s x " + orders.get(count - 1).getNoOfPlates() + "\n", count++, item.getDish(), item.getPrice());
        }
        System.out.println(calculation);
        System.out.println("----------------------------------");
        String x = "Total (after 15% discount)";
        System.out.printf("%-29s  %c%.2f\n\n", x, currency, this.total);
    }
}

class RegularCustomer extends Customer{
    public RegularCustomer(String name){
        super(name);
    }
}

public class Restaurant {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Restaurant restaurant = new Restaurant("Grand Vitara", "Ground Floor, Near Syndicate Bank, Pochinki, BGMI, 210110");

        Restaurant.Menu item1 = new Restaurant.Menu("Noodle", "$5");
        Restaurant.Menu item2 = new Restaurant.Menu("Burger", "$4");
        Restaurant.Menu item3 = new Restaurant.Menu("French Fries", "$3");
        Restaurant.Menu item4 = new Restaurant.Menu("Barbeque", "$8");

        restaurant.addItems(item1);
        restaurant.addItems(item2);
        restaurant.addItems(item3);
        restaurant.addItems(item4);

        restaurant.getRestaurantInformation();

        VIPCustomer customer1 = new VIPCustomer("Alex");
        Customer customer2 = new Customer("Andrea");
        Customer customer3 = new Customer("Roy");

        System.out.println("Orders : ");

        customer1.order(1, item1);
        customer1.order(3, item3);
        customer1.bill();

        customer2.order(2, item1);
        customer2.order(2, item2);
        customer2.bill();

        customer3.order(1, item4);
        customer3.order(2, item3);
        customer3.bill();

        System.out.println("Give the rating to the " + restaurant.getRestaurantName() + " restaurant.");
        restaurant.giveRating();
        restaurant.giveRating();

        restaurant.getRating();
    }
}
