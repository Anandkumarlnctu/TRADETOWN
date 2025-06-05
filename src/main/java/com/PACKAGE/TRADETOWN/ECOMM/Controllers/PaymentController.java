package com.PACKAGE.TRADETOWN.ECOMM.Controllers;



import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import com.razorpay.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PaymentController {

    private static final String KEY_ID = "rzp_test_HX8no9EuxOuyNG"; 
    private static final String KEY_SECRET = "hienLRPfqg9lPk68edwzRLY2"; 

    @PostMapping("/create-order")
    public String createOrder(@RequestParam("amount") int amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        Order order = razorpay.orders.create(orderRequest);
        return order.toString();
    }

    @PostMapping("/payment-callback")
    public RedirectView paymentCallback( 
            @RequestParam("razorpay_order_id") String razorpayOrderId,
            @RequestParam("razorpay_payment_id") String razorpayPaymentId,
            @RequestParam("razorpay_signature") String razorpaySignature) throws RazorpayException {
        try {
            // Verify the payment signature here
            String signature = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValid = Utils.verifySignature(signature, razorpaySignature, KEY_SECRET);

            if (isValid) {
                // Payment successful
                RedirectView redirectView = new RedirectView("/ordersuccesshtml.html?orderId=" + razorpayOrderId);
                return redirectView;
            } else {
                // Payment failed
                return new RedirectView("/orderfail.html"); // Create failure.html if needed
            }
        } catch (RazorpayException e) {
            System.err.println("Razorpay Exception during callback: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General Exception during callback: " + e.getMessage());
            throw new RazorpayException("General exception during callback");
        }
    }

    @PostMapping("/get-key")
    public String getKey() {
        return KEY_ID;
    }
}
