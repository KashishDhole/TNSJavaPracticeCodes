
# Online Food Delivery System (Core Java Case Study)

A console-based food delivery application that demonstrates OOP concepts (inheritance, polymorphism, collections) with Admin and Customer modules.

## Project Structure
```
OnlineFoodDeliverySystem/
├── src/com/fooddelivery/
│   ├── AdminService.java
│   ├── Cart.java
│   ├── Customer.java
│   ├── CustomerService.java
│   ├── DataStore.java
│   ├── DeliveryPerson.java
│   ├── FoodItem.java
│   ├── Main.java
│   ├── Order.java
│   └── Restaurant.java
└── README.md
```

## How to Compile & Run

### Using `javac` and `java`
```bash
cd OnlineFoodDeliverySystem
javac -d out $(find src -name "*.java")
java -cp out com.fooddelivery.application.Main
```

### On Windows (PowerShell)
```powershell
cd OnlineFoodDeliverySystem
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } | javac -d out @-
java -cp out com.fooddelivery.application.Main
```

## Notes
- Prompts and print statements are crafted to match the sample output in the case study.
- Data is stored in-memory for simplicity.
- When "Place Order" is used, the cart is cleared (common UX).

## Sample Flow
Follow the interaction sequence from your case study to reproduce the exact sample output.
