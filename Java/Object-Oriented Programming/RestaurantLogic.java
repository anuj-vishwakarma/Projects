/*18. Write a Java program to create a class called "Restaurant" with attributes for menu items, prices, and ratings, and methods to add and remove items, and to calculate average rating.

    Extra Added:
        Place order, billing, tip to waiter

*/
package Question18;
import java.util.ArrayList;

class Restaurant{
    static int orderNumber = 1;
    private String restaurantName;
    private String restaurantLocation;
    protected ArrayList<Menu> items = new ArrayList<>();

    private final ArrayList<Rating> ratings = new ArrayList<>();

    public void giveRating(int stars, String customer, Customer c){
        new Rating(stars, customer, c).giveRating(stars, customer, c);
    }
    public void getRating(){
        if(!restaurantName.toLowerCase().contains("the"))
            System.out.println("Ratings of the \"" + restaurantName + "\" restaurant\n");
        else
            System.out.println("Ratings of \"" + restaurantName + "\" restaurant\n");
        for(Rating rating : ratings){
            System.out.println("Customer: " + rating.getCustomerName() + "\n" +
                               "Rating: " + rating.getRatingByCustomer()
                    );
        }
        int avgRating = 0;
        for(Rating rating : ratings){
            avgRating+= rating.getRatingByCustomer();
        }
        System.out.println("Average Rating : " +  ((float)avgRating/ratings.size()));
    }
    Restaurant(){}
    Restaurant(String restaurantName, String restaurantLocation){
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
    }
    public void addItems(Menu item){
        items.add(item);
    }
    public void removeItems(Menu item){
        items.remove(item);
    }
    public void getMenu(){
        System.out.println("""

                Menu of our restaurant\s

                Dishes\t\t\t\t\t\tPrice
                """);
        int counter = 1;
        for(Menu item:items){
            System.out.printf("%d.%-15s\t\t\t%s\n\n",counter, item.getDish(), item.getPrice());
            counter++;
        }
    }
    public void restaurantInformation(){
        System.out.println("Name: " + this.restaurantName + "\n\n" +
                            "Location: " + this.restaurantLocation );
                            getMenu();
    }
    class Rating {
        private final int stars;
        private final String customer;
        public Rating(int stars, String customer, Customer c){
            this.stars = stars;
            this.customer = customer;
        }

        public String getCustomerName() {
            return customer;
        }

        public int getRatingByCustomer() {
            return stars;
        }
        public void giveRating(int stars, String customer, Customer c){
            Rating rating = new Rating(stars, customer, c);
            ratings.add(rating);
        }
    }
}
class Menu {
    private final String dish;
    private final String price;
    Menu(String dish, String price){
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
class Customer extends Restaurant{

    private  int total = 0;
    ArrayList<Menu> orderedItems = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    public void order(int noOfPlates, Menu item){
            orderedItems.add(item);
            this.total += noOfPlates * Integer.parseInt(item.getPrice().substring(1));
            Order order = new Order(noOfPlates, item);
            orders.add(order);
    }
    static class Order{
        private final int noOfPlates;
        Menu item;
        public Order(int noOfPlates, Menu item){
            this.noOfPlates = noOfPlates;
            this.item = item;
        }

        public int getNoOfPlates() {
            return noOfPlates;
        }
    }
    public void bill(){

        int total = 0;
        String calculation = "";
        int count= 1;

        for(Menu item:orderedItems){
            total += Integer.parseInt(item.getPrice().substring(1));
        }
        System.out.println("Order Number: " + orderNumber++ );
        System.out.println("Items\t\t\t\t\t\tPrice");
        for(Menu item:orderedItems){
            System.out.printf("%d.%-20s \t\t%s x " +orders.get(count-1).getNoOfPlates()+ "\n",count++,item.getDish(),item.getPrice());
        }
        System.out.println(calculation);
        System.out.println("----------------------------------");
        String x = "Total";
        System.out.printf( "%-29s  $%d\n\n",x, this.total);
    }
}

public class RestaurantLogic {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Grand Vitara","Ground Floor, Near Syndicate Bank, Pochinki, BGMI, 210110");

        Menu item1  = new Menu("Noodle","$5");
        Menu item2  = new Menu("Burger","$4");
        Menu item3  = new Menu("French Fries","$3");
        Menu item4  = new Menu("Barbeque","$8");

        restaurant.addItems(item1);
        restaurant.addItems(item2);
        restaurant.addItems(item3);
        restaurant.addItems(item4);

        restaurant.restaurantInformation();

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        System.out.println("Orders : ");
        customer1.order(1,item1);
        customer1.order(3,item3);
        customer1.bill();

        customer2.order(2, item1);
        customer2.order(2, item2);
        customer2.bill();

        customer3.order(1,item4);
        customer3.order(2,item3);
        customer3.bill();


        restaurant.giveRating(4,"Alex", customer1);
        restaurant.giveRating(5, "Andrea", customer2);

        restaurant.getRating();
    }
}
