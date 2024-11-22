package emsi.ace.service;

import emsi.ace.stubs.Bank;
import emsi.ace.stubs.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase {

    private static final Map<String, Double> currencies = new HashMap<>();
    static {
        currencies.put("USD", 1.0);
        currencies.put("EUR", 0.84);
        currencies.put("MAD", 1.4);
    }

    @Override
    public void convert(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyResponse> responseObserver) {
        String currencyFrom = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();
        double amount = request.getAmount();
        double fromRate = currencies.get(currencyFrom);
        double toRate = currencies.get(currencyTo);
        double result = amount * fromRate / toRate;

        Bank.ConvertCurrencyResponse response = Bank.ConvertCurrencyResponse.newBuilder()
                .setCurrencyFrom(currencyFrom)
                .setCurrencyTo(currencyTo)
                .setAmount(amount)
                .setResult(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
