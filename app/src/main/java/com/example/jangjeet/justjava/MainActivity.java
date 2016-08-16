package com.example.jangjeet.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when increment button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(getApplicationContext(), "DONT DRINK SO MUCH COOFEE!!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        TextView name = (TextView) findViewById(R.id.name);
        String custName = name.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.Whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.Chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, custName);

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, "skorpion63@gmail.com");
        email.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + custName);
        email.putExtra(Intent.EXTRA_TEXT, message);

        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }
    }



    /**
     * Calculates the price of the order.
     */

    private int calculatePrice(boolean whippedCream, boolean chocolate) {

        int basePrice = 5;
        if (whippedCream) {
            basePrice = basePrice + 1;
        }
        if (chocolate) {
            basePrice = basePrice + 2;
        }
        int price = basePrice * quantity;
        return price;
    }


    public String createOrderSummary(int price, boolean whippedCream, boolean chocolate, String custName) {
        return "Name: " + custName + "\nAdd whipped cream? " + whippedCream + "\nAdd chocolate? " + chocolate + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank You!";
    }

    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(getApplicationContext(), "ARE YOU STUPID", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numbers) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numbers);
    }


}