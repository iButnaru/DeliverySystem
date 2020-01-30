package Entities;

public class Package {

    private int id;
    private boolean fragile;
    private int delivery_type;
    private String status;
    private int customer_id;

    public Package() {
    }

    public Package(boolean fragile, int delivery_type, String status, int customer_id) {
        this.fragile = fragile;
        this.delivery_type = delivery_type;
        this.status = status;
        this.customer_id = customer_id;
    }

    public Package(int id, boolean fragile, int delivery_type, String status, int customer_id) {
        this.id = id;
        this.fragile = fragile;
        this.delivery_type = delivery_type;
        this.status = status;
        this.customer_id = customer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", fragile=" + fragile +
                ", delivery_type=" + delivery_type +
                ", status='" + status + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
