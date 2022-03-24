package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    CheckBox whippedCream, chocolate;
    EditText editText;
    /**
     * by default the quantity has been set to 2.
     */
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the order plus/increment is clicked.
     */
    public void increment(View view) {
        quantity++;
        if (quantity > 10) {
            quantity = 10;
            Toast.makeText(MainActivity.this, "You can't order more than 10 coffee!", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }


    /**
     * This method is called when the order minus/decrement is clicked.
     */
    public void decrement(View view) {
        quantity--;
        if (quantity < 1) {
            quantity = 1;
            Toast.makeText(MainActivity.this, "You can't order less than 1 coffee!", Toast.LENGTH_SHORT).show();
        }
        display(quantity);

    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the user's name
        editText = (EditText) findViewById(R.id.name_field);
        String name = editText.getText().toString();

        // Figure out if the user wants whipped cream topping
        whippedCream = (CheckBox) findViewById(R.id.whipped_check_box);
        boolean whippedCreamCheckedState = whippedCream.isChecked();

        // Figure out if the user wants chocolate topping
        chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean chocolateCheckedState = chocolate.isChecked();

        // Calculates the price using calculatePrice method and store the price in a variable named price.
        int price = calculatePrice(whippedCreamCheckedState, chocolateCheckedState);
        String orderMessage = createOrderSummary(name, price, whippedCreamCheckedState, chocolateCheckedState);

        // Intent to redirect to the email app.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(orderMessage);

    }

    /**
     * Calculates the price of the order based on toppings.
     *
     * @param : returns the price value.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int Price = 5;
        if (addWhippedCream) Price += 1;
        if (addChocolate) Price += 2;
        Price = Price * quantity;

        return Price;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param price           of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {

        String message = "Name: " + name;
        message += "\nAdd whipped cream? " + addWhippedCream;
        message += "\nAdd chocolate? " + addChocolate;
        message += "\nQuantity: " + quantity;
        message += "\nTotal: " + price + " rs";
        message += "\n" + "Thank you!";
        return message;
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}