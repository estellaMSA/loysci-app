package br.com.loysciapp.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.ElementList;

import java.util.List;

import br.com.loysciapp.loysci_android.model.Items;

public class CouponItems {

        @ElementList(inline = true, name = "Items")
        private List<Items> Items;

        CouponItems(List<Items> items) {
            Items = items;
        }

        public List<Items> getItems() {
            return Items;
        }

        public void setItems(List<Items> items) {
            Items = items;
        }
    }