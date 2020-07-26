package com.paytabs.task.views;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paytabs.task.R;
import com.paytabs.task.dataAccess.PaymentViewModel;
import com.paytabs.task.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {

    private PaymentViewModel mViewModel;
    private FragmentPaymentBinding binding;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_payment, container, false);
        View view = binding.getRoot();

        binding.setLifecycleOwner(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
    }

}