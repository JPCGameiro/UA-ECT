
class Order {

  final String clientName;
  final String prodName;
  final int quantity;

  Order(String client, String prod, int quant) {
    prodName = prod;
    clientName = client;
    quantity = quant;
  }

  public String toString() {
    return String.format("Order(%s, %s, %d)", clientName, prodName, quantity);
  }

}
