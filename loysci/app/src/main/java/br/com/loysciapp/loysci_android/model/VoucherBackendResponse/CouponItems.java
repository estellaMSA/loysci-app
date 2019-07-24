package br.com.loysciapp.loysci_android.model.VoucherBackendResponse;

public class CouponItems
{
    private Item Item;

    public Item getItem ()
    {
        return Item;
    }

    public void setItem (Item Item)
    {
        this.Item = Item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Item = "+Item+"]";
    }
}
			
	