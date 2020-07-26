package com.paytabs.task.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;
import com.paytabs.task.R;
import com.paytabs.task.dataAccess.CreateOrderViewModel;
import com.paytabs.task.databinding.FragmentCreateOrderBinding;
import com.paytabs.task.utils.AppConstants;

import static android.app.Activity.RESULT_OK;

public class CreateOrderFragment extends Fragment {

    private CreateOrderViewModel mViewModel;
    private FragmentCreateOrderBinding binding;

    public static CreateOrderFragment newInstance() {
        return new CreateOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_create_order, container, false);
        View view = binding.getRoot();

        binding.setLifecycleOwner(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CreateOrderViewModel.class);

        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payNow();
            }
        });
    }

    void payNow() {
        Intent in = new Intent(getActivity(), PayTabActivity.class);
        in.putExtra(PaymentParams.MERCHANT_EMAIL, "nasreldeenmohamed@gmail.com"); //this a demo account for testing the sdk
        in.putExtra(PaymentParams.SECRET_KEY, AppConstants.SECRET_KEY); //Add your Secret Key Here
        in.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH);
        in.putExtra(PaymentParams.TRANSACTION_TITLE, binding.tvTransactionInfo.getText().toString());
        in.putExtra(PaymentParams.AMOUNT, Double.valueOf(binding.etAmount.getText().toString()));

        in.putExtra(PaymentParams.CURRENCY_CODE, "EGP");
        in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733");
        in.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com");
        in.putExtra(PaymentParams.ORDER_ID, "123456");
        in.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2");

//Billing Address
        in.putExtra(PaymentParams.ADDRESS_BILLING, binding.etAddress.getText().toString());
        in.putExtra(PaymentParams.CITY_BILLING, "Manama");
        in.putExtra(PaymentParams.STATE_BILLING, "Manama");
        in.putExtra(PaymentParams.COUNTRY_BILLING, "BHR");
        in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Shipping Address
        in.putExtra(PaymentParams.ADDRESS_SHIPPING, binding.etAddress.getText().toString());
        in.putExtra(PaymentParams.CITY_SHIPPING, "Manama");
        in.putExtra(PaymentParams.STATE_SHIPPING, "Manama");
        in.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR");
        in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
        in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc");

//Tokenization
        in.putExtra(PaymentParams.IS_TOKENIZATION, true);
//PreAuth
        in.putExtra(PaymentParams.IS_PREAUTH, true);

        startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));

            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));
            }

            NavHostFragment.findNavController(this).navigate(R.id.paymentFragment);
        }
    }

}