package com.skynetsoftware.trungson.ui.tabhome.listagency;

import com.google.android.gms.maps.model.LatLng;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ListAgencyContract {
    interface View extends BaseView{
        void onSuccessGetListAgency(List<Shop> list);

    }
    interface Presenter extends BasePresenter,ListProductionListener{
       void getListAgency(LatLng latLng);
    }

    interface Interactor {
        void getListAgency(LatLng latLng);

    }
    interface ListProductionListener extends OnFinishListener{
        void onSuccessGetListAgency(List<Shop> list);
    }
}
