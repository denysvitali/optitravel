package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.results;

public class Meta {
    private String sort_key;
    private String sort_order;
    private int per_page;
    private int offset;

    public int getOffset() {
        return offset;
    }

    public int getPer_page() {
        return per_page;
    }

    public String getSort_key() {
        return sort_key;
    }

    public String getSort_order() {
        return sort_order;
    }
}
