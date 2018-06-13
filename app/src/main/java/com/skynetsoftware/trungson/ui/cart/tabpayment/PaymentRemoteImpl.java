package com.skynetsoftware.trungson.ui.cart.tabpayment;

import com.google.gson.Gson;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.ProductRequest;
import com.skynetsoftware.trungson.network.api.ApiResponse;
import com.skynetsoftware.trungson.network.api.ApiUtil;
import com.skynetsoftware.trungson.network.api.CallBackBase;
import com.skynetsoftware.trungson.network.api.TrungSonAPI;
import com.skynetsoftware.trungson.ui.base.Interactor;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaymentRemoteImpl extends Interactor implements PaymentContract.Interactor {
    PaymentContract.PaymentListener listener;

    public PaymentRemoteImpl(PaymentContract.PaymentListener listener) {
        this.listener = listener;
    }

    @Override
    public TrungSonAPI createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getInforCard() {
        String num = AppController.getInstance().getmSetting().getString("card_num");
        String name = AppController.getInstance().getmSetting().getString("card_name");
        String date = AppController.getInstance().getmSetting().getString("card_date");
        String cvv = AppController.getInstance().getmSetting().getString("card_cvv");
        listener.onSuccessGetInforCard(num, name, date, cvv);
    }

    @Override
    public void saveInfoCard(String date, String number, String name, String cvv) {
        AppController.getInstance().getmSetting().put("card_num", number);
        AppController.getInstance().getmSetting().put("card_name", name);
        AppController.getInstance().getmSetting().put("card_date", date);
        AppController.getInstance().getmSetting().put("card_cvv", cvv);

    }

    @Override
    public void paidCart(int typePayment, double price) {
        if (AppController.getInstance().getListProducts().size() == 0) {
            listener.onError("Không có sản phẩm nào trong giỏ");

            return;
        }
        if (AppController.getInstance().getmProfileUser() == null) {
            listener.onErrorAuthorization();
            return;
        }

        String name = AppController.getInstance().getmSetting().getString("name");
        String address = AppController.getInstance().getmSetting().getString("address");
        String city = AppController.getInstance().getmSetting().getString("city");
        String phone = AppController.getInstance().getmSetting().getString("phone");
        String note = AppController.getInstance().getmSetting().getString("note");
        String promotion = AppController.getInstance().getmSetting().getString("promo");

        if (name == null || address == null || city == null || phone == null) {
            listener.onError("Thiếu thông tin nhận hàng");
            return;
        }
        final List<ProductRequest> listProduct = new ArrayList<>();
        for (Product p : AppController.getInstance().getListProducts()) {
            listProduct.add(new ProductRequest(p.getId(), p.getNumberOfProduct()));
        }
        getmService().paidCart(AppController.getInstance().getmProfileUser().getId(), new Gson().toJson(listProduct),
                note, promotion, price, name, address, city, phone, typePayment).enqueue(new CallBackBase<ApiResponse<String>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {

                        listener.onSuccessPaid(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<String>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
