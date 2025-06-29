package nz.clem.store.payments;

import nz.clem.store.orders.Order;

import java.util.Optional;

public interface PaymentGateway {

    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);

}
