package apporio.com.vehicleapp.Setter_Getter_Files;

import java.util.List;

/**
 * Created by admin on 5/11/2016.
 */
public class MyridesResultCore {


    /**
     * result : 1
     * Message : [{"ride_id":"66","coupons_code":"","pickup_lat":"28.4125708","pickup_long":"77.0432908","pickup_address":"IRIS Tech Park, Sector 49, Gurgaon, Haryana 122018, India","drop_lat":"28.4592693","drop_long":"77.0724192","drop_address":"Netaji Subhash Marg, Sector 44, Gurgaon, Haryana 122003, India","r_time":"17:50","ride_type":"0","ride_place_date":"2016-09-24 12:20:36","pickup_date":"","pickup_time":"","driver_id":"59","city_id":"0","driver_name":"tessss","car_number":"1234","phone_number":"16389","current_lat":"28.411992","current_long":"77.0432852","current_address":"68, Plaza St, Block S, Uppal Southend, Sector 49, Gurgaon, Haryana 122018, India","driver_image":"uploads/driver/59.png","car_type_id":"10","car_type_name":"Extended","car_type_image":"uploads/car/car_10.png","ride_status":"2","driver_status":"3","receipent":{"receipent_name":"tedt","receipent_address":"Huda City Centre Metro Station, Gurgaon, Haryana, India","receipent_emailid":"test@gmail.com","receipent_phonenumber":"123466"},"shipment":[{"shipment_weight":"0-100","shipment_image":"uploads/shipment/ride_264.jpg","shipment_quantity":"1","shipment_spcl_instruction":""}],"done_ride":"8"}]
     */

    private int result;
    /**
     * ride_id : 66
     * coupons_code :
     * pickup_lat : 28.4125708
     * pickup_long : 77.0432908
     * pickup_address : IRIS Tech Park, Sector 49, Gurgaon, Haryana 122018, India
     * drop_lat : 28.4592693
     * drop_long : 77.0724192
     * drop_address : Netaji Subhash Marg, Sector 44, Gurgaon, Haryana 122003, India
     * r_time : 17:50
     * ride_type : 0
     * ride_place_date : 2016-09-24 12:20:36
     * pickup_date :
     * pickup_time :
     * driver_id : 59
     * city_id : 0
     * driver_name : tessss
     * car_number : 1234
     * phone_number : 16389
     * current_lat : 28.411992
     * current_long : 77.0432852
     * current_address : 68, Plaza St, Block S, Uppal Southend, Sector 49, Gurgaon, Haryana 122018, India
     * driver_image : uploads/driver/59.png
     * car_type_id : 10
     * car_type_name : Extended
     * car_type_image : uploads/car/car_10.png
     * ride_status : 2
     * driver_status : 3
     * receipent : {"receipent_name":"tedt","receipent_address":"Huda City Centre Metro Station, Gurgaon, Haryana, India","receipent_emailid":"test@gmail.com","receipent_phonenumber":"123466"}
     * shipment : [{"shipment_weight":"0-100","shipment_image":"uploads/shipment/ride_264.jpg","shipment_quantity":"1","shipment_spcl_instruction":""}]
     * done_ride : 8
     */

    private List<MessageBean> Message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<MessageBean> getMessage() {
        return Message;
    }

    public void setMessage(List<MessageBean> Message) {
        this.Message = Message;
    }

    public static class MessageBean {
        private String ride_id;
        private String coupons_code;
        private String pickup_lat;
        private String pickup_long;
        private String pickup_address;
        private String drop_lat;
        private String drop_long;
        private String drop_address;
        private String r_time;
        private String ride_type;
        private String ride_place_date;
        private String pickup_date;
        private String pickup_time;
        private String driver_id;
        private String city_id;
        private String driver_name;
        private String car_number;
        private String phone_number;
        private String current_lat;
        private String current_long;
        private String current_address;
        private String driver_image;
        private String car_type_id;
        private String car_type_name;
        private String car_type_image;
        private String ride_status;
        private String driver_status;
        /**
         * receipent_name : tedt
         * receipent_address : Huda City Centre Metro Station, Gurgaon, Haryana, India
         * receipent_emailid : test@gmail.com
         * receipent_phonenumber : 123466
         */

        private ReceipentBean receipent;
        private String done_ride;
        /**
         * shipment_weight : 0-100
         * shipment_image : uploads/shipment/ride_264.jpg
         * shipment_quantity : 1
         * shipment_spcl_instruction :
         */

        private List<ShipmentBean> shipment;

        public String getRide_id() {
            return ride_id;
        }

        public void setRide_id(String ride_id) {
            this.ride_id = ride_id;
        }

        public String getCoupons_code() {
            return coupons_code;
        }

        public void setCoupons_code(String coupons_code) {
            this.coupons_code = coupons_code;
        }

        public String getPickup_lat() {
            return pickup_lat;
        }

        public void setPickup_lat(String pickup_lat) {
            this.pickup_lat = pickup_lat;
        }

        public String getPickup_long() {
            return pickup_long;
        }

        public void setPickup_long(String pickup_long) {
            this.pickup_long = pickup_long;
        }

        public String getPickup_address() {
            return pickup_address;
        }

        public void setPickup_address(String pickup_address) {
            this.pickup_address = pickup_address;
        }

        public String getDrop_lat() {
            return drop_lat;
        }

        public void setDrop_lat(String drop_lat) {
            this.drop_lat = drop_lat;
        }

        public String getDrop_long() {
            return drop_long;
        }

        public void setDrop_long(String drop_long) {
            this.drop_long = drop_long;
        }

        public String getDrop_address() {
            return drop_address;
        }

        public void setDrop_address(String drop_address) {
            this.drop_address = drop_address;
        }

        public String getR_time() {
            return r_time;
        }

        public void setR_time(String r_time) {
            this.r_time = r_time;
        }

        public String getRide_type() {
            return ride_type;
        }

        public void setRide_type(String ride_type) {
            this.ride_type = ride_type;
        }

        public String getRide_place_date() {
            return ride_place_date;
        }

        public void setRide_place_date(String ride_place_date) {
            this.ride_place_date = ride_place_date;
        }

        public String getPickup_date() {
            return pickup_date;
        }

        public void setPickup_date(String pickup_date) {
            this.pickup_date = pickup_date;
        }

        public String getPickup_time() {
            return pickup_time;
        }

        public void setPickup_time(String pickup_time) {
            this.pickup_time = pickup_time;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getCurrent_lat() {
            return current_lat;
        }

        public void setCurrent_lat(String current_lat) {
            this.current_lat = current_lat;
        }

        public String getCurrent_long() {
            return current_long;
        }

        public void setCurrent_long(String current_long) {
            this.current_long = current_long;
        }

        public String getCurrent_address() {
            return current_address;
        }

        public void setCurrent_address(String current_address) {
            this.current_address = current_address;
        }

        public String getDriver_image() {
            return driver_image;
        }

        public void setDriver_image(String driver_image) {
            this.driver_image = driver_image;
        }

        public String getCar_type_id() {
            return car_type_id;
        }

        public void setCar_type_id(String car_type_id) {
            this.car_type_id = car_type_id;
        }

        public String getCar_type_name() {
            return car_type_name;
        }

        public void setCar_type_name(String car_type_name) {
            this.car_type_name = car_type_name;
        }

        public String getCar_type_image() {
            return car_type_image;
        }

        public void setCar_type_image(String car_type_image) {
            this.car_type_image = car_type_image;
        }

        public String getRide_status() {
            return ride_status;
        }

        public void setRide_status(String ride_status) {
            this.ride_status = ride_status;
        }

        public String getDriver_status() {
            return driver_status;
        }

        public void setDriver_status(String driver_status) {
            this.driver_status = driver_status;
        }

        public ReceipentBean getReceipent() {
            return receipent;
        }

        public void setReceipent(ReceipentBean receipent) {
            this.receipent = receipent;
        }

        public String getDone_ride() {
            return done_ride;
        }

        public void setDone_ride(String done_ride) {
            this.done_ride = done_ride;
        }

        public List<ShipmentBean> getShipment() {
            return shipment;
        }

        public void setShipment(List<ShipmentBean> shipment) {
            this.shipment = shipment;
        }

        public static class ReceipentBean {
            private String receipent_name;
            private String receipent_address;
            private String receipent_emailid;
            private String receipent_phonenumber;

            public String getReceipent_name() {
                return receipent_name;
            }

            public void setReceipent_name(String receipent_name) {
                this.receipent_name = receipent_name;
            }

            public String getReceipent_address() {
                return receipent_address;
            }

            public void setReceipent_address(String receipent_address) {
                this.receipent_address = receipent_address;
            }

            public String getReceipent_emailid() {
                return receipent_emailid;
            }

            public void setReceipent_emailid(String receipent_emailid) {
                this.receipent_emailid = receipent_emailid;
            }

            public String getReceipent_phonenumber() {
                return receipent_phonenumber;
            }

            public void setReceipent_phonenumber(String receipent_phonenumber) {
                this.receipent_phonenumber = receipent_phonenumber;
            }
        }

        public static class ShipmentBean {
            private String shipment_weight;
            private String shipment_image;
            private String shipment_quantity;
            private String shipment_spcl_instruction;

            public String getShipment_weight() {
                return shipment_weight;
            }

            public void setShipment_weight(String shipment_weight) {
                this.shipment_weight = shipment_weight;
            }

            public String getShipment_image() {
                return shipment_image;
            }

            public void setShipment_image(String shipment_image) {
                this.shipment_image = shipment_image;
            }

            public String getShipment_quantity() {
                return shipment_quantity;
            }

            public void setShipment_quantity(String shipment_quantity) {
                this.shipment_quantity = shipment_quantity;
            }

            public String getShipment_spcl_instruction() {
                return shipment_spcl_instruction;
            }

            public void setShipment_spcl_instruction(String shipment_spcl_instruction) {
                this.shipment_spcl_instruction = shipment_spcl_instruction;
            }
        }
    }
}




