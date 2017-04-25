package com.mubeen.vanesa.activites;

import android.content.Intent;
import java.math.BigDecimal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mubeen.vanesa.R;
import com.mubeen.vanesa.app.AppConfig;
import com.mubeen.vanesa.util.CartSharedPrefferences;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;

public class MyPaymentActivity extends AppCompatActivity {

    BigDecimal finalamount;
    TextView textViewTotalAmount;
    Button paypalButton;
    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(AppConfig.PAYPAL_ENVIRONMENT).clientId(
                    AppConfig.PAYPAL_CLIENT_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent i = getIntent();
        finalamount = BigDecimal.valueOf(new CartSharedPrefferences().getCartAmount(this));
        textViewTotalAmount = (TextView) findViewById(R.id.textView_totalamount);
        textViewTotalAmount.setText("SubTotal: $" + finalamount.toString());
        paypalButton = (Button) findViewById(R.id.paypal_payment);

        paypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkout();
            }
        });
    }

    private boolean checkout() {
        PayPalPayment thingsToBuy = prepareFinalCart();

        Intent intent = new Intent(MyPaymentActivity.this, com.paypal.android.sdk.payments.PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingsToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        return true;
    }

    private PayPalPayment prepareFinalCart() {

        //PayPalItem[] items = new PayPalItem[1];
//        items = (PayPalItem[]) cartArrayList.toArray(items);

        // Total amount
        java.math.BigDecimal subtotal = finalamount;

        // If you have shipping cost, add it here
        java.math.BigDecimal shipping = new java.math.BigDecimal("0.0");

        // If you have tax, add it here
        java.math.BigDecimal tax = new java.math.BigDecimal("0.0");

        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(
                shipping, subtotal, tax);

        java.math.BigDecimal amount = subtotal.add(shipping).add(tax);

        PayPalPayment payment = new PayPalPayment(
                amount,
                AppConfig.DEFAULT_CURRENCY,
                "Description about transaction. This will be displayed to the user.",
                AppConfig.PAYMENT_INTENT);

        //payment.items(items).paymentDetails(paymentDetails);

        // Custom field like invoice_number etc.,
        payment.custom("This is text that will be associated with the payment that the app can use.");

        return payment;
    }
}
